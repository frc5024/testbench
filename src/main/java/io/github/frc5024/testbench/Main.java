package io.github.frc5024.testbench;

import java.io.IOException;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import io.github.frc5024.lib5k.autonomous.RobotProgram;
import io.github.frc5024.lib5k.logging.RobotLogger.Level;
import io.github.frc5024.testbench.config.Parser;
import io.github.frc5024.testbench.config.hardwareconfigs.AnalogInputConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.DigitalInputConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.GyroConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.MotorConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.PCMConfig;
import io.github.frc5024.testbench.dashboard.AnalogInputDashboard;
import io.github.frc5024.testbench.dashboard.DigitalInputDashboard;
import io.github.frc5024.testbench.dashboard.GyroDashboard;
import io.github.frc5024.testbench.dashboard.MotorDashboard;
import io.github.frc5024.testbench.dashboard.PCMDashboard;

public class Main extends RobotProgram {

    public static void main(String[] args) {
        RobotBase.startRobot(Main::new);
    }

    // All dashboards
    private MotorDashboard motorDash;
    private DigitalInputDashboard dioDash;
    private AnalogInputDashboard aioDash;
    private GyroDashboard gyroDash;
    private PCMDashboard pcmDash;

    public Main() {
        super(false, true, Shuffleboard.getTab("Robot Status"));

        // Create all dashboards
        motorDash = MotorDashboard.getInstance();
        dioDash = DigitalInputDashboard.getInstance();
        aioDash = AnalogInputDashboard.getInstance();
        gyroDash = GyroDashboard.getInstance();
        pcmDash = PCMDashboard.getInstance();

        // Set config load status
        NetworkTableEntry configStatus = Shuffleboard.getTab("Robot Status").add("Config Ready", false)
                .withWidget(BuiltInWidgets.kBooleanBox).getEntry();

        // Load json
        Parser parser = Parser.getInstance();
        try {

            // Load motor configs
            MotorConfig[] motors = parser.getAllMotors();
            if (motors != null) {
                motorDash.addMotors(motors);
            }

            // Load DIO configs
            DigitalInputConfig[] dis = parser.getAllDigitalInputs();
            if (dis != null) {
                dioDash.addDigitalInputs(dis);
            }
            
            // Load AIO configs
            AnalogInputConfig[] ais = parser.getAllAnalogInputs();
            if (ais != null) {
                aioDash.addAnalogInputs(ais);
            }

            // Load all gyros
            GyroConfig[] gyros = parser.getAllGyros();
            if (gyros != null) {
                gyroDash.addGyros(gyros);
            }

            // Load PCM
            PCMConfig pcm = parser.getPCMConfig();
            if (pcm != null) {
                pcmDash.configPCM(pcm);
            }

            // Update config status
            configStatus.setBoolean(true);
        } catch (IOException e) {
            logger.log("Robot", "Failed to parse all json settings", Level.kWarning);
            DriverStation.reportError(e.toString(), e.getStackTrace());
        }

    }

    @Override
    public void autonomous(boolean init) {
        if (init) {
            logger.log("Robot", "TestBench program must be run in \"Test\" mode!", Level.kWarning);
        }
    }

    @Override
    public void disabled(boolean init) {
        if (init) {
            logger.log("Robot", "Disabled");
        }

    }

    @Override
    public void teleop(boolean init) {
        if (init) {
            logger.log("Robot", "TestBench program must be run in \"Test\" mode!", Level.kWarning);
        }
    }

    @Override
    public void test(boolean init) {

        // Update all dashboards
        motorDash.update();
        dioDash.update();
        aioDash.update();
        gyroDash.update();
        pcmDash.update();


    }

}