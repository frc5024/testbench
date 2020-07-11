package io.github.frc5024.testbench.dashboard;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.lib5k.logging.RobotLogger;
import io.github.frc5024.testbench.config.hardwareconfigs.PCMConfig;

public class PCMDashboard {
    public static PCMDashboard m_instance = null;

    // The PCM shuffleboard tab
    private ShuffleboardTab tab;

    // PCM Data list
    private ArrayList<PCMData> pcmData;

    private class PCMData {

        public Compressor input;

        public NetworkTableEntry value;

        /**
         * Updates the pnuematics
         */
        public void update() {
            input.setClosedLoopControl(value.getBoolean(false));
        }

    }

    private PCMDashboard() {
        tab = Shuffleboard.getTab("PCM");
        pcmData = new ArrayList<>();
    }

    /**
     * Adds PCM to the list and adds them to shuffleboard
     * @param configs 
     */
    public void addPCMs(PCMConfig... configs) {
        for (PCMConfig config : configs) {

            // Creates a PCM Data Class
            PCMData pcmData = new PCMData();

            // Adds a compressor to the input
            pcmData.input = new Compressor(config.can_id);

            // adds the value to shuffleboard
            pcmData.value = tab.add("Closed Loop Inabled", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();

            // adds it to the pcm data list
            this.pcmData.add(pcmData);

        }
    }

    /**
     * Updates the pcm dashboard
     */
    public void update() {
        for (PCMData pcmData2 : pcmData) {
            pcmData2.update();
        }
    }

    /**
     * Gets the PCMDashboard instance
     * @return a PCMDashboard instance
     */
    public static PCMDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new PCMDashboard();
        }
        return m_instance;
    }

}