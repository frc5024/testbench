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
import io.github.frc5024.testbench.config.hardwareconfigs.MotorConfig;
import io.github.frc5024.testbench.dashboard.MotorDashboard;

public class Main extends RobotProgram {

    public static void main(String[] args) {
        RobotBase.startRobot(Main::new);
    }

    // All dashboards
    private MotorDashboard motorDash;

    public Main() {
        super(false, true, Shuffleboard.getTab("Robot Status"));

        // Create all dashboards
        motorDash = MotorDashboard.getInstance();

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


    }

}