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

    private class GyroDashboardData {

        public ISimGyro input;

        public NetworkTableEntry value;

        public void update() {
            value.setDouble(input.getAngle());
        }

    }

    private GyroDashboard() {

        tab = Shuffleboard.getTab("Gyro");

        gyroDashboardDatas = new ArrayList<>();

    }

    public void addGyros(GyroConfig... gyros) {
        for (GyroConfig gyro : gyros) {

            GyroDashboardData gyroDashboardData = new GyroDashboardData();

            if (!Arrays.asList(gyro.device_type.allowedTypes).contains(gyro.bus_type)) {
                RobotLogger.getInstance().log("GyroDashboard",
                        String.format("Gyro %d's bus type is not allowed for it's model", gyro.id),
                        RobotLogger.Level.kWarning);

                continue;
            }



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

            gyroDashboardData.input.reset();

            gyroDashboardData.value = tab.add(String.format("Gyro %s", gyro.device_type.toString()), 0).withWidget(BuiltInWidgets.kGyro).getEntry();

            gyroDashboardDatas.add(gyroDashboardData);

        }
    }


    public void update(){
        for (GyroDashboardData gyroDashboardData : gyroDashboardDatas) {
            gyroDashboardData.update();
        }
    }


    public static GyroDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new GyroDashboard();
        }

        return m_instance;
    }

}