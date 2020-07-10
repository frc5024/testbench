package io.github.frc5024.testbench.config.datatypes;

/**
 * All allowed motor controller types
 */
public enum MotorControllerType {
    TalonSRX(BusType.CAN), TalonFX(BusType.CAN), VictorSPX(BusType.CAN), Spark(BusType.PWM),
    BrushlessSparkMax(BusType.CAN), BrushedSparkMax(BusType.CAN);

    // All allowed data bus types for the controller
    public BusType[] allowedTypes;

    private MotorControllerType(BusType... allowedTypes) {
        this.allowedTypes = allowedTypes;
    }
}