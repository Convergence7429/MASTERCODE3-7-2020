package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

public class LED {

    AddressableLED led;
    AddressableLEDBuffer ledBuffer;


    public void initLED(){
        led = new AddressableLED(RobotMap.ledPort);
        ledBuffer = new AddressableLEDBuffer(60);
        led.setLength(ledBuffer.getLength());

        led.setData(ledBuffer);
        led.start();
    }

    public boolean LEDset(){
        for(int i = 0; i < ledBuffer.getLength(); i++){
            ledBuffer.setRGB(i, 255, 0, 0);
        }
        led.setData(ledBuffer);
        return true;
    }
}
