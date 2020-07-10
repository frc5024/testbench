package io.github.frc5024.testbench.config;

import edu.wpi.first.wpilibj.Filesystem;
import io.github.frc5024.testbench.config.hardwareconfigs.AnalogInputConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.DigitalInputConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.GyroConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.MotorConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.PCMConfig;

/**
 * JSON Parser
 */
public class Parser {

    private static Parser instance;

    // Json config filepath
    private static final String CONFIG_FILEPATH = Filesystem.getDeployDirectory().getPath() + "/hardwareConfig.json";

    // File parser data
    private boolean fileParsed = false;

    private Parser() {

    }

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    private void parse() {
        
    }

    public MotorConfig[] getAllMotors() {
        if (!fileParsed) {
            parse();
        }
        return null;
    }

    public GyroConfig[] getAllGyros() {
        if (!fileParsed) {
            parse();
        }
        return null;
    }

    public AnalogInputConfig[] getAllAnalogInputs() {
        if (!fileParsed) {
            parse();
        }
        return null;
    }

    public DigitalInputConfig[] getAllDigitalInputs() {
        if (!fileParsed) {
            parse();
        }
        return null;
    }

    public PCMConfig getPCMConfig() {
        if (!fileParsed) {
            parse();
        }
        return null;
    }

}