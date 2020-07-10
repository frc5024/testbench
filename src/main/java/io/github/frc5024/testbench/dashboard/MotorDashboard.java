package io.github.frc5024.testbench.dashboard;

import io.github.frc5024.testbench.config.hardwareconfigs.MotorConfig;
import java.util.ArrayList;
import edu.wpi.first.wpilibj.shuffleboard.*;

public class MotorDashboard {
    public static MotorDashboard m_instance = null;

    private ArrayList<MotorConfig> m_motorConfigs;

    ShuffleboardTab tab;


    private MotorDashboard() {
        m_motorConfigs = new ArrayList<MotorConfig>();
    }

    public void addMotor(MotorConfig motor) {
        m_motorConfigs.add(motor);
    }

    public void update(){
        // Read from dashboard
        //  - Motor voltage (slider)
        //  - Motor inverted (switch)
        // Write to dashboard
        //  - Encoder value (only if talon, falcon, or sparkmax) (graph)
        // https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/shuffleboard/getting-started/shuffleboard-graphs.html
    }


    /**
     * @return the motor dashboard instance
     */
    public static MotorDashboard getInstance(){
        if(m_instance == null){
            m_instance = new MotorDashboard();
        }
        return m_instance;

    }
    

}