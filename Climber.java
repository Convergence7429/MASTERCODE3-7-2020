package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.time.StopWatch;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import com.revrobotics.ControlType;

/**
 * Add your docs here.
 */
public class Climber {
    public CANSparkMax hookSpark4 = new CANSparkMax(4, MotorType.kBrushless);
    public TalonFX talonSpool4 = new TalonFX(4);
    //StopWatch stopwatch = new StopWatch();
    public CANEncoder spark4Encoder = new CANEncoder(hookSpark4);
    public CANPIDController spark4PID = new CANPIDController(hookSpark4);
    double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    boolean canDoSmallAdjust;

    public void climberInit(){
        canDoSmallAdjust = false;
        spark4PID.setFeedbackDevice(spark4Encoder);
        spark4Encoder.setPosition(0);
        kP = 0.05;
        kI = 0;
        kD = 0;
        kIz = 0;
        kFF = 0;
        kMaxOutput = 1;
        kMinOutput = -1;
        spark4PID.setP(kP);
        spark4PID.setI(kI);
        spark4PID.setD(kD);
        spark4PID.setIZone(kIz);
        spark4PID.setFF(kFF);
        spark4PID.setOutputRange(kMinOutput, kMaxOutput);
        
        spark4PID.setReference(0, ControlType.kPosition); //////////////////////////////// just wondering if this is ok
        talonSpool4.setNeutralMode(NeutralMode.Brake);
    }


    public void deployHook(){
        //stopwatch.start();
        SmartDashboard.putNumber("spark", spark4Encoder.getPosition());
        if(RobotMap.controller.getRawButton(2)){ // B
            spark4PID.setReference(-258, ControlType.kPosition);
            canDoSmallAdjust = true;
        }
        else if (RobotMap.controller.getRawButton(3)) {
            spark4PID.setReference(0, ControlType.kPosition);
            canDoSmallAdjust = false;
        }
        if (canDoSmallAdjust && (RobotMap.controller.getRawAxis(5) > 0.1 || RobotMap.controller.getRawAxis(5) < -0.1)) {
            if (RobotMap.controller.getRawAxis(5) > 0.1) {
                spark4PID.setReference(spark4Encoder.getPosition() + 3, ControlType.kPosition);
            }
            else {
                spark4PID.setReference(spark4Encoder.getPosition() - 3, ControlType.kPosition);
            }
        }
    }

    public void deploySpool(){
        if(RobotMap.controller.getRawButton(4)){ // Y
            talonSpool4.set(ControlMode.PercentOutput, -0.9);
        }
        // else if(RobotMap.controller.getRawButton(6)){ // RB
        //     talonSpool4.set(ControlMode.PercentOutput, 0.5);
        // }
        else {
            talonSpool4.set(ControlMode.PercentOutput, 0);
        }
    }
}