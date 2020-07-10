package io.github.frc5024.testbench.config.hardwareconfigs;

import io.github.frc5024.testbench.config.datatypes.BusType;
import io.github.frc5024.testbench.config.datatypes.MotorControllerType;

/**
 * Common configuration for all motors
 */
public class MotorConfig {
    public BusType bus_type;
    public MotorControllerType controller;
    public int id;
    public double max_output = 12.0;
    public double min_output = -12.0;
    public boolean inverted = false;
    public double slew_seconds = 0.0;
}