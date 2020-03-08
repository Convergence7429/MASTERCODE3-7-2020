package frc.robot;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.smartdashboard.*;
import com.revrobotics.*;
import edu.wpi.first.wpilibj.Solenoid;

public class ColorSensor {
    // Remember
    // red = blue
    // blue = red
    // yellow = green
    // green = yellow

    public CANSparkMax colorSpinnerMotor = new CANSparkMax(6, MotorType.kBrushless);
    public CANEncoder colorSpinEncoder = new CANEncoder(colorSpinnerMotor);
    public CANPIDController colorPID = new CANPIDController(colorSpinnerMotor);
    double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    public String desiredColor;
     
    public I2C.Port i2cPort = I2C.Port.kOnboard;
    public ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    public ColorMatch m_colorMatcher = new ColorMatch();

    private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
    private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
    private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
    private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

    public void initColorSpinnerMotor(){
        colorPID.setFeedbackDevice(colorSpinEncoder);
        colorSpinEncoder.setPosition(0);
        kP = 0.05;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0;
        kMaxOutput = .3;
        kMinOutput = -.3;
        colorPID.setP(kP);
        colorPID.setI(kI);
        colorPID.setD(kD);
        colorPID.setIZone(kIz);
        colorPID.setFF(kFF);
        colorPID.setOutputRange(kMinOutput, kMaxOutput);
    }
    public void initColor(){
        m_colorMatcher.addColorMatch(kBlueTarget);
        m_colorMatcher.addColorMatch(kGreenTarget);
        m_colorMatcher.addColorMatch(kRedTarget);
        m_colorMatcher.addColorMatch(kYellowTarget);
        
    }

    public void teleopColor(){
        Color detectedColor = m_colorSensor.getColor();
        String colorString;
        ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

        if (match.color == kBlueTarget) {
            colorString = "Blue";
        } else if (match.color == kRedTarget) {
            colorString = "Red";
        } else if (match.color == kGreenTarget) {
            colorString = "Green";
        } else if (match.color == kYellowTarget) {
            colorString = "Yellow";
        } else {
            colorString = "Unknown";
        }

        SmartDashboard.putNumber("Red", detectedColor.red);
        SmartDashboard.putNumber("Green", detectedColor.green);
        SmartDashboard.putNumber("Blue", detectedColor.blue);
        SmartDashboard.putNumber("Confidence", match.confidence);
        SmartDashboard.putString("Detected Color", colorString);
    }

    public void spinWheel(){
        if(RobotMap.joystick.getRawButton(10)){
            colorSpinnerMotor.set(.25);
        }
        if(RobotMap.joystick.getRawButton(11)){
            colorSpinnerMotor.set(0);
        }
    }

    }