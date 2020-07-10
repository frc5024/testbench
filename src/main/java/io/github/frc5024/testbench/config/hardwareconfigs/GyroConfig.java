package io.github.frc5024.testbench.config.hardwareconfigs;

import io.github.frc5024.testbench.config.datatypes.BusType;
import io.github.frc5024.testbench.config.datatypes.GyroType;

/**
 * Common configuration for all gyros
 */
public class GyroConfig {
    public BusType bus_type;
    public GyroType device_type;
    public int id = 0;
}