package io.github.frc5024.testbench.dashboard;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.lib5k.logging.RobotLogger;
import io.github.frc5024.testbench.config.hardwareconfigs.AnalogInputConfig;

public class AnalogInputDashboard {
    public static AnalogInputDashboard m_instance = null;

    // Dashboard tab
    private ShuffleboardTab tab;

    // List of analog input
    private ArrayList<AnalogInputData> analogInputData;


    private AnalogInputDashboard() {

        // Gets the tab
        tab = Shuffleboard.getTab("Analog Input");

        // Creates the analog input arraylist
        analogInputData = new ArrayList<>();

    }

    /**
     * Class for storing data
     */
    private class AnalogInputData {

        public AnalogInput input;

        public NetworkTableEntry value;

        /**
         * Update the value
         */
        public void update() {
            value.setDouble(input.getValue());
        }

    }

    /**
     * Adds an analog config
     * 
     * @param configs the analog configs to add
     */
    public void addAnalogInputs(AnalogInputConfig... configs) {
        for (AnalogInputConfig config : configs) {

            AnalogInputData analogInput = new AnalogInputData();

            // Tries to create an analog input, skips if it fails
            try {
                analogInput.input = new AnalogInput(config.port);
            } catch (RuntimeException e) {
                RobotLogger.getInstance().log("DigitalInputDashboard",
                        String.format(
                                "Failed to init input %d.\nThis is likely due to two configs for the same port number.",
                                config.port));
                continue;
            }

            // adds the value to network tables
            analogInput.value = tab.add(String.format("Channel %d", config.port), 0).getEntry();

            // adds the Analog Input Data to the list
            analogInputData.add(analogInput);

        }
    }

    /**
     * Updates all Analog Inputs added to the list
     */
    public void update() {
        for (AnalogInputData analogInput : analogInputData) {
            analogInput.update();
        }
    }

    /**
     * Gets the AnalogInputDashboard instance
     * @return AnalogInputDashboard instance
     */
    public static AnalogInputDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new AnalogInputDashboard();
        }

        return m_instance;
    }

}