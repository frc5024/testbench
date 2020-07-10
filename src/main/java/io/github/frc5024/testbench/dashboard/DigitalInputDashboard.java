package io.github.frc5024.testbench.dashboard;

import java.util.ArrayList;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import io.github.frc5024.testbench.config.hardwareconfigs.DigitalInputConfig;

public class DigitalInputDashboard {
    public static DigitalInputDashboard m_instance = null;

    private ShuffleboardTab tab;

    private ArrayList<DigitalInputData> digitalInputs;

    private DigitalInputDashboard() {

        tab = Shuffleboard.getTab("Digital Input");

        digitalInputs = new ArrayList<>();
    }

    


    private class DigitalInputData{

        public int port;

        public NetworkTableEntry inverted;

        public boolean defaultInverted;

        public DigitalInput digitalInput;

        public NetworkTableEntry value;

        

        public DigitalInputData(int port, boolean inverted){
            this.port = port;

            this.defaultInverted = inverted;

            this.digitalInput = new DigitalInput(port);
        }

        public boolean readDigitalInput(){
            return inverted.getBoolean(defaultInverted) ? digitalInput.get() == false : digitalInput.get();
        }

        public void update(){
            value.setBoolean(readDigitalInput());
        }



    }



    public void addDigitalInput(DigitalInputConfig... configs){

        for(DigitalInputConfig config:  configs){

            DigitalInputData digitalInput = new DigitalInputData(config.port, config.inverted);

            digitalInputs.add(digitalInput);

            ShuffleboardLayout layout = tab.getLayout(String.format("Digital Input: %d", config.port),
                    BuiltInLayouts.kList).withSize(2, 4);

            digitalInput.inverted = layout.add("Digital Input Invert", config.inverted).withWidget(BuiltInWidgets.kToggleSwitch)
                     .getEntry();

            digitalInput.value = layout.add("Input Value", 0.0).withWidget(BuiltInWidgets.kGraph).getEntry();


        }

    }


    private void update(){
        for(DigitalInputData digitalInput:  digitalInputs){
            digitalInput.update();
        }
    }



    /**
     * Get the Digital Input Dashboard
     * @return the Digital Input Dashboard instance
     */
    public static DigitalInputDashboard getInstance(){
        if(m_instance == null){
            m_instance = new DigitalInputDashboard();
        }

        return m_instance;
    }




}