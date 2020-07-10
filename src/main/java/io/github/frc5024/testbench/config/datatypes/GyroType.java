package io.github.frc5024.testbench.config.datatypes;

public enum GyroType {
    ADXR(BusType.SPI), NavX(BusType.MXP, BusType.SPI, BusType.I2C), PigeonIMU(BusType.CAN);
    
    // All allowed data bus types for the gyro
    public BusType[] allowedTypes;

    private GyroType(BusType... allowedTypes) {
        this.allowedTypes = allowedTypes;
    }
}