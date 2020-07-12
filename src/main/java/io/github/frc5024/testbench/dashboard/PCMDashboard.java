package io.github.frc5024.testbench.dashboard;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.lib5k.logging.RobotLogger;
import io.github.frc5024.testbench.config.hardwareconfigs.PCMConfig;

public class PCMDashboard {
    public static PCMDashboard m_instance = null;

    // The PCM shuffleboard tab
    private ShuffleboardTab tab;

    // Compressor
    private Compressor compressor;
    private NetworkTableEntry compressorValue;

    // Solenoids
    private ArrayList<SolenoidData> solenoids = new ArrayList<>();

    private class SolenoidData {
        public Solenoid output;
        public NetworkTableEntry value;

        public void update() {
            output.set(value.getBoolean(false));
        }
    }

    private PCMDashboard() {
        tab = Shuffleboard.getTab("PCM");
    }

    public void configPCM(PCMConfig config) {

        // Set up compressor
        compressor = new Compressor(config.can_id);
        compressorValue = tab.add("Compressor", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();

        // Set up each PCM channel
        for (int i = 0; i < 8; i++) {
            SolenoidData s = new SolenoidData();

            // Set up output
            s.output = new Solenoid(config.can_id, i);

            // Set up dashboard output
            s.value = tab.add(String.format("Channel %d", i), false).withWidget(BuiltInWidgets.kToggleSwitch)
                    .getEntry();

            // Add to list
            solenoids.add(s);
        }

    }

    /**
     * Updates the pcm dashboard
     */
    public void update() {

        // Handle compressor
        if (compressor != null) {
            compressor.setClosedLoopControl(compressorValue.getBoolean(false));
        }

        // Handle all channels
        for (SolenoidData solenoid : solenoids) {
            solenoid.update();
        }
    }

    /**
     * Gets the PCMDashboard instance
     * 
     * @return a PCMDashboard instance
     */
    public static PCMDashboard getInstance() {
        if (m_instance == null) {
            m_instance = new PCMDashboard();
        }
        return m_instance;
    }

}