package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;

public class ProximitySensor {
    DigitalInput proxSensor1 = new DigitalInput(RobotMap.ProxSensor_Primary); // closer to intake
    DigitalInput proxSensor6 = new DigitalInput(RobotMap.ProxSensor_Secondary); // farther from intake

    public boolean ballsReady() { // proxSensor1
        if (proxSensor1.get() == true) {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean ballsLoaded(){ // proxSensor6
        if(proxSensor6.get()){
            return false;
        } else {
            return true;
        }

    }
}