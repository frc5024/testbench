package io.github.frc5024.testbench.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.Nullable;

import com.google.gson.Gson;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import io.github.frc5024.lib5k.logging.RobotLogger;
import io.github.frc5024.lib5k.logging.RobotLogger.Level;
import io.github.frc5024.testbench.config.hardwareconfigs.AnalogInputConfig;
import io.github.frc5024.testbench.config.hardwareconfigs.ConfigFile;
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
    private MotorConfig[] motors = null;
    private GyroConfig[] gyros = null;
    private AnalogInputConfig[] analogInputs = null;
    private DigitalInputConfig[] digitalInputs = null;
    private PCMConfig pcm = null;

    private Parser() {

    }

    public static Parser getInstance() {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    private void parse() throws IOException {

        // Read file
        FileReader reader = null;
        try {
            reader = new FileReader(CONFIG_FILEPATH);
        } catch (FileNotFoundException e) {
            RobotLogger.getInstance().log("JSONParser",
                    "Invalid filepath. This robot might be missing a hardwareConfig.json file!", Level.kWarning);
            DriverStation.reportError(e.toString(), e.getStackTrace());
            return;
        }

        // Parse out with gson
        Gson gson = new Gson();
        ConfigFile config = gson.fromJson(reader, ConfigFile.class);

        // Set configs
        motors = config.motors;
        pcm = config.pcm;

        // Add sensors if needed
        if (config.sensors != null) {
            digitalInputs = config.sensors.digital;
            analogInputs = config.sensors.analog;
            gyros = config.sensors.gyros;
        }

        // Close the file
        reader.close();
        fileParsed = true;

    }

    /**
     * Get all defined motors in the config. This will return null if no motors are
     * defined
     * 
     * @return List of motors or null
     * @throws IOException
     */
    public @Nullable MotorConfig[] getAllMotors() throws IOException {
        if (!fileParsed) {
            parse();
        }
        return motors;
    }

    /**
     * Get all defined gyroscopes in the config. This will return null if no
     * gyroscopes are defined
     * 
     * @return List of gyroscopes or null
     * @throws IOException
     */
    public @Nullable GyroConfig[] getAllGyros() throws IOException {
        if (!fileParsed) {
            parse();
        }
        return gyros;
    }

    /**
     * Get all defined analog inputs in the config. This will return null if no
     * analog inputs are defined
     * 
     * @return List of analog inputs or null
     * @throws IOException
     */
    public @Nullable AnalogInputConfig[] getAllAnalogInputs() throws IOException {
        if (!fileParsed) {
            parse();
        }
        return analogInputs;
    }

    /**
     * Get all defined digital inputs in the config. This will return null if no
     * digital inputs are defined
     * 
     * @return List of digital inputs or null
     * @throws IOException
     */
    public @Nullable DigitalInputConfig[] getAllDigitalInputs() throws IOException {
        if (!fileParsed) {
            parse();
        }
        return digitalInputs;
    }

    /**
     * Get the Pneumatic control module defined in the config. If none defined, this
     * will return null.
     * 
     * @return PCM config
     * @throws IOException
     */
    public @Nullable PCMConfig getPCMConfig() throws IOException {
        if (!fileParsed) {
            parse();
        }
        return pcm;
    }

}