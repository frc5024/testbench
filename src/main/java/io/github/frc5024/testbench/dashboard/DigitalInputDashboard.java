package io.github.frc5024.testbench.dashboard;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.lib5k.logging.RobotLogger;
import io.github.frc5024.testbench.config.hardwareconfigs.DigitalInputConfig;

/**
 * Dashboard panel for Digital inputs
 */
public class DigitalInputDashboard {
    public static DigitalInputDashboard m_instance = null;

    // Dashboard tab
    private ShuffleboardTab tab;

    /**
     * Data structure for Digital Input devices
     */
    private class DigitalInputData {

        // Input channel
        public DigitalInput input;

        // Networktables entry for current value
        public NetworkTableEntry value;

        // Update the NT value
        public void update() {
            value.setBoolean(input.get());
        }

    }

    // List of all added
    private ArrayList<DigitalInputData> digitalInputs = new ArrayList<>();

    private DigitalInputDashboard() {

        // Load the shuffleboard tab for digital inputs
        tab = Shuffleboard.getTab("Digital Inputs");
    }

    /**
     * Add a digital input channel (or channels)
     * 
     * @param configs Digital input channel configuration(s)
     */
    public void addDigitalInput(DigitalInputConfig... configs) {
        for (DigitalInputConfig config : configs) {

            // Create input data
            DigitalInputData digitalInput = new DigitalInputData();

            // Connect to DIO channel
            try {
                digitalInput.input = new DigitalInput(config.port);
            } catch (RuntimeException e) {
                // If someone writes the same port twice, we will get a RuntimeException from
                // the FPGA because the same IO handle will have two locks on it.
                RobotLogger.getInstance().log("DigitalInputDashboard",
                        String.format(
                                "Failed to init input %d.\nThis is likely due to two configs for the same port number.",
                                config.port));
                continue;
            }

            // Add a display box to the panel
            digitalInput.value = tab.add(String.format("Channel %d", config.port), false).getEntry();

            // Add input to inputs
            digitalInputs.add(digitalInput);

        }

    }

    /**
     * Update all components
     */
    public void update() {
        for (DigitalInputData digitalInput : digitalInputs) {
            digitalInput.update();
        }
    }

    /**
     * Get the Digital Input Dashboard
     * 
     * @return the Digital Input Dashboard instance
     */
    public static DigitalInputDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new DigitalInputDashboard();
        }

        return m_instance;
    }

}