package io.github.frc5024.testbench.dashboard;

import io.github.frc5024.lib5k.hardware.common.sensors.interfaces.CommonEncoder;
import io.github.frc5024.lib5k.hardware.ctre.motors.ExtendedTalonFX;
import io.github.frc5024.lib5k.hardware.ctre.motors.ExtendedTalonSRX;
import io.github.frc5024.lib5k.hardware.ctre.motors.ExtendedVictorSPX;
import io.github.frc5024.lib5k.hardware.revrobotics.motors.ExtendedSparkMax;
import io.github.frc5024.lib5k.logging.RobotLogger;
import io.github.frc5024.testbench.config.hardwareconfigs.MotorConfig;
import java.util.ArrayList;

import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import ca.retrylife.ewmath.MathUtils;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.shuffleboard.*;

/**
 * Dashboard panel for motors
 */
public class MotorDashboard {
    public static MotorDashboard m_instance = null;

    // Dashboard tab
    ShuffleboardTab tab;

    /**
     * Data structure for motor data and devices
     */
    private class MotorData {

        // Speed controller
        public SpeedController output;

        // Encoder
        public CommonEncoder encoder;

        // Min and max output
        public double[] minMax = new double[2];

        // Default inversion
        public boolean defaultInverted;

        // Networktables entries for settings
        public NetworkTableEntry speed;
        public NetworkTableEntry position;
        public NetworkTableEntry inverted;

        // Handle setting and getting data
        public void update() {
            System.out.println(speed.getDouble(0.0));
            output.setVoltage(MathUtils.clamp(speed.getDouble(0.0), minMax[0], minMax[1]));
            output.setInverted(inverted.getBoolean(defaultInverted));

            // Only do this if there is an encoder
            if (encoder != null) {
                position.setDouble(encoder.getPosition());
            }
        }
    }

    // All motors
    private ArrayList<MotorData> motors = new ArrayList<>();

    private MotorDashboard() {

        // Load the shuffleboard tab for motors
        tab = Shuffleboard.getTab("Motors");
    }

    /**
     * Add a motor (or motors) to the panel
     * @param motors Motor configuration(s)
     */
    public void addMotors(MotorConfig... motors) {
        for (MotorConfig config : motors) {

            // Create motor data
            MotorData motor = new MotorData();

            // Handle motor type
            // We just have to read the config and create the correct object type
            switch (config.controller) {
                case BrushedSparkMax:
                    motor.output = new ExtendedSparkMax(config.id, MotorType.kBrushed);

                    // Since this isn't a Neo, we will have to manually set the sensor type. 5024
                    // only uses Quadrature sensors. So it is probably safe to guess (watch this
                    // backfire in a year)
                    motor.encoder = ((ExtendedSparkMax) motor.output).getCommonEncoder(EncoderType.kQuadrature,
                            config.encoderCPR);
                    break;
                case BrushlessSparkMax:

                    // ExtendedSparkMax automatically determines the CPR of brushless motors, so we
                    // can use the default getters.
                    motor.output = new ExtendedSparkMax(config.id, MotorType.kBrushless);
                    motor.encoder = ((ExtendedSparkMax) motor.output).getCommonEncoder();
                    break;
                case Spark:
                    motor.output = new Spark(config.id);
                    break;
                case TalonFX:
                    motor.output = new ExtendedTalonFX(config.id);
                    motor.encoder = ((ExtendedTalonFX) motor.output).getCommonEncoder(config.encoderCPR);
                    break;
                case TalonSRX:
                    motor.output = new ExtendedTalonSRX(config.id);
                    motor.encoder = ((ExtendedTalonSRX) motor.output).getCommonEncoder(config.encoderCPR);
                    break;
                case VictorSPX:
                    motor.output = new ExtendedVictorSPX(config.id);
                    break;
                default:
                    break;

            }

            // If the motor is null (this shouldn't ever happen) we will just skip it
            if (motor.output == null) {
                continue;
            }

            // Set min and max
            motor.minMax[0] = config.min_output;
            motor.minMax[1] = config.max_output;

            // Set inversion
            motor.output.setInverted(config.inverted);
            motor.defaultInverted = config.inverted;

            // Set up a new list for shuffleboard
            ShuffleboardLayout layout = tab.getLayout(String.format("%s: %d", config.controller.toString(), config.id),
                    BuiltInLayouts.kList).withSize(2, 4);

            // Add every list component
            motor.speed = layout.add("Voltage Output", 0.0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
            motor.inverted = layout.add("Motor Invert", config.inverted).withWidget(BuiltInWidgets.kToggleSwitch)
                    .getEntry();
            motor.position = layout.add("Rotations", 0.0).withWidget(BuiltInWidgets.kGraph).getEntry();

            // Add the motor to the motors list
            this.motors.add(motor);
        }
    }

    /**
     * Update all components
     */
    public void update() {
        // Update all motors
        for (MotorData motor : motors) {
            motor.update();
        }
    }

    /**
     * @return the motor dashboard instance
     */
    public static MotorDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new MotorDashboard();
        }
        return m_instance;

    }

}