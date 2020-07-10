package io.github.frc5024.testbench;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import io.github.frc5024.lib5k.autonomous.RobotProgram;
import io.github.frc5024.lib5k.logging.RobotLogger.Level;

public class Main extends RobotProgram {

    public static void main(String[] args) {
        RobotBase.startRobot(Main::new);
    }

    public Main() {
        super(false, true, Shuffleboard.getTab("Robot Status"));
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

    }

}