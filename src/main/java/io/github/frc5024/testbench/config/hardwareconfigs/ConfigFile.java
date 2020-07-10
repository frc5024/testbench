package io.github.frc5024.testbench.config.hardwareconfigs;

/**
 * A data structure that defines the json hardware config file
 */
public class ConfigFile {
    public MotorConfig[] motors = null;
    public PCMConfig pcm = null;

    // Sensors struct
    public static class Sensors {
        public DigitalInputConfig[] digital = null;
        public AnalogInputConfig[] analog = null;
        public GyroConfig[] gyros = null;
    }

    public Sensors sensors = null;
}