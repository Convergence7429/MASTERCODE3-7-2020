package frc.robot;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Indexer {
    public CANSparkMax indexerSpark5 = new CANSparkMax(5, MotorType.kBrushless);
    public CANSparkMax beltSpark7 = new CANSparkMax(7, MotorType.kBrushless);

    public CANEncoder spark5Encoder = new CANEncoder(indexerSpark5);
    public CANPIDController spark5PID = new CANPIDController(indexerSpark5);

    public CANEncoder spark7Encoder = new CANEncoder(beltSpark7);
    public CANPIDController spark7PID = new CANPIDController(beltSpark7);

    double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

    public void indexerInit(){
        spark5PID.setFeedbackDevice(spark5Encoder);
        spark5Encoder.setPosition(0);
        kP = 0.05;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0;
        kMaxOutput = 1;
        kMinOutput = -1;
        spark5PID.setP(kP);
        spark5PID.setI(kI);
        spark5PID.setD(kD);
        spark5PID.setIZone(kIz);
        spark5PID.setFF(kFF);
        spark5PID.setOutputRange(kMinOutput, kMaxOutput);

        spark7PID.setFeedbackDevice(spark7Encoder);
        spark7Encoder.setPosition(0);
        kP = 0.05;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0;
        kMaxOutput = 1;
        kMinOutput = -1;
        spark7PID.setP(kP);
        spark7PID.setI(kI);
        spark7PID.setD(kD);
        spark7PID.setIZone(kIz);
        spark7PID.setFF(kFF);
        spark7PID.setOutputRange(kMinOutput, kMaxOutput);
    }

    public void runIndexer(){

        if(RobotMap.joystick.getRawButton(10)){
            System.out.println("button 8 pressed");
            beltSpark7.set(-0.45);
        } 
        else {
            beltSpark7.set(0.0);   
        }

        if(RobotMap.joystick.getRawButton(12)){
            System.out.println("button 12 pressed");
            indexerSpark5.set(-0.45);
        } else {
            indexerSpark5.set(0.0);
        }
    }
}
