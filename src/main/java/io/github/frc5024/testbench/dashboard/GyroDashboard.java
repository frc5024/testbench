package io.github.frc5024.testbench.dashboard;

import java.util.ArrayList;
import java.util.Arrays;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.lib5k.hardware.common.sensors.interfaces.ISimGyro;
import io.github.frc5024.lib5k.hardware.ctre.sensors.ExtendedPigeonIMU;
import io.github.frc5024.lib5k.hardware.generic.gyroscopes.ADGyro;
import io.github.frc5024.lib5k.hardware.kauai.gyroscopes.NavX;
import io.github.frc5024.lib5k.logging.RobotLogger;
import io.github.frc5024.testbench.config.datatypes.BusType;
import io.github.frc5024.testbench.config.hardwareconfigs.GyroConfig;

public class GyroDashboard {
    public static GyroDashboard m_instance = null;

    public ArrayList<GyroDashboardData> gyroDashboardDatas;

    public ShuffleboardTab tab;

    /**
     * Class for storing Gyro Data
     */
    private class GyroDashboardData {

        public ISimGyro input;

        public NetworkTableEntry value;

        /**
         * Updates the value
         */
        public void update() {
            value.setDouble(input.getAngle());
        }

    }

    private GyroDashboard() {

        // Creates the tab
        tab = Shuffleboard.getTab("Gyro");

        // Creates the list
        gyroDashboardDatas = new ArrayList<>();

    }

    /**
     * Adds gyros to the gyro list
     * @param gyros gyros to add to the list
     */
    public void addGyros(GyroConfig... gyros) {
        for (GyroConfig gyro : gyros) {

            // Creates Gyro Dashboard Data
            GyroDashboardData gyroDashboardData = new GyroDashboardData();

            // Checks if the bus type is allowed for it's model
            if (!Arrays.asList(gyro.device_type.allowedTypes).contains(gyro.bus_type)) {
                RobotLogger.getInstance().log("GyroDashboard",
                        String.format("Gyro %d's bus type is not allowed for it's model", gyro.id),
                        RobotLogger.Level.kWarning);

                continue;
            }


            // Adds the correct gyro to the data
            switch (gyro.device_type) {
                case ADXR:
                    gyroDashboardData.input = new ADGyro();
                    break;
                case NavX:
                    gyroDashboardData.input = new NavX();
                    break;
                case PigeonIMU:
                    gyroDashboardData.input = new ExtendedPigeonIMU(gyro.id);
                    break;
                default:
                    break;
            }

            // Resets the gyro
            gyroDashboardData.input.reset();

            // Adds the value to shuffleboard
            gyroDashboardData.value = tab.add(String.format("Gyro %s", gyro.device_type.toString()), 0).withWidget(BuiltInWidgets.kGyro).getEntry();

            // Adds the gyro to the gyro list
            gyroDashboardDatas.add(gyroDashboardData);

        }
    }


    /**
     * Updates all gyros
     */
    public void update(){
        for (GyroDashboardData gyroDashboardData : gyroDashboardDatas) {
            gyroDashboardData.update();
        }
    }


    /**
     * Gets the GyroDashboard instance
     * @return the GyroDashboard instance
     */
    public static GyroDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new GyroDashboard();
        }

        return m_instance;
    }

}