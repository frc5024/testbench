package io.github.frc5024.testbench.hardware;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Base class for all types of hardware (sections in config)
 */
public abstract class HardwareBase {

    // GUI tab to publish to
    private ShuffleboardTab dashboard;

    /**
     * Create a HardwareBase
     * 
     * @param typeName Name of the hardware type
     */
    public HardwareBase(String typeName) {
        dashboard = Shuffleboard.getTab(typeName);
    }

    /**
     * Access the underlying dashboard panel
     * 
     * @return Dashboard panel
     */
    protected ShuffleboardTab getDashPanel() {
        return dashboard;
    }

    /**
     * Update all hardware
     */
    public abstract void update();

}