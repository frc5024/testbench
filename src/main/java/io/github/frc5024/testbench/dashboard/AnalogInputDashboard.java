package io.github.frc5024.testbench.dashboard;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.testbench.config.hardwareconfigs.AnalogInputConfig;

public class AnalogInputDashboard {
    public static AnalogInputDashboard m_instance = null;

    // Dashboard tab
    private ShuffleboardTab tab;

    private ArrayList<AnalogInputData> analogInputData;

    private AnalogInputDashboard() {

        tab = Shuffleboard.getTab("Analog Input");

        analogInputData = new ArrayList<>();

    }

    private class AnalogInputData {

        public AnalogInput input;

        public NetworkTableEntry value;

        public void update() {
            value.setDouble(input.getValue());
        }

    }

    public void addAnalogInputs(AnalogInputConfig... configs) {
        for (AnalogInputConfig config : configs) {

            AnalogInputData analogInput = new AnalogInputData();

            analogInput.input = new AnalogInput(config.port);

            analogInput.value = tab.add(String.format("Channel %d", config.port), 0).getEntry();

            analogInputData.add(analogInput);

        }
    }

    public void update() {
        for (AnalogInputData analogInput : analogInputData) {
            analogInput.update();
        }
    }

    public static AnalogInputDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new AnalogInputDashboard();
        }

        return m_instance;
    }

}