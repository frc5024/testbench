package io.github.frc5024.testbench.config;

/**
 * All configuration datatypes
 */
public class Datatypes {

    /**
     * Digital I/O datatype
     */
    public static class DIOType {
        public int id;
        public boolean inverted = false;
    }

    /**
     * Analog I/O datatype
     */
    public static class AnalogType {
        public int id;
    }

    /**
     * Gyroscope datatype
     */
    public static class GyroType {
        public static enum DeviceType {
            NavX, ADXR, PigeonIMU;
        }

        public static enum DevicePort {
            MXP, SPI, CAN;
        }

        public DeviceType type;
        public DevicePort port;
        public int portID = 0;
    }

    /**
     * Motor datatype
     */
    public static class MotorType {

        public static enum BusType {
            CAN, PWM;
        }

        public static enum ControllerType {
            TalonSRX, Spark, BrushlessCANSparkMax, BrushedCANSparkMax, VictorSPX, TalonFX;
        }

        public BusType bus;
        public ControllerType controller;
        public int id;
        public double maxOut = 12.0;
        public double minOut = -12.0;
        public boolean inverted = false;
        public double slewSeconds = 0.0;

    }

}