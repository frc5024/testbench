package io.github.frc5024.testbench.dashboard;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.lib5k.hardware.generic.pneumatics.LazySolenoid;

public class PCMDashboard {
    public static PCMDashboard m_instance = null;

    private ShuffleboardTab tab;


    private class PCMData{

        public Compressor input = new Compressor(0);

        public NetworkTableEntry value;

        public void update(){
            
        }

    }


    private PCMDashboard(){
        tab = Shuffleboard.getTab("PCM");
    }

    





    public static PCMDashboard getInstance(){
        if(m_instance == null){
            m_instance = new PCMDashboard();
        }
        return m_instance;
    }



}