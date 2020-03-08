package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
public class Intake {
    public CANSparkMax intakeSpark6 = new CANSparkMax(6, MotorType.kBrushless);
    // public CANSparkMax intakeMotor2 = new CANSparkMax(4, MotorType.kBrushless)
    public DoubleSolenoid intake = new DoubleSolenoid(RobotMap.intakeSolenoidIn, RobotMap.intakeSolenoidOut);
    public DoubleSolenoid spinner = new DoubleSolenoid(RobotMap.spinnerSolenoidIn, RobotMap.spinnerSolenoidOut);
    public DoubleSolenoid open = new DoubleSolenoid(RobotMap.openSolenoidIn, RobotMap.openSolenoidOut);
    public boolean isIntakeDown;

    public void initIntake(){


        intakeSolenoidOff();
        openSolenoidOff();
        spinnerSolenoidOff();
        isIntakeDown = false;
    }

    

    public void intakeSolenoidReverse(){
        intake.set(DoubleSolenoid.Value.kReverse);
    }

    public void spinnerSolenoidReverse(){
        spinner.set(DoubleSolenoid.Value.kReverse);
    }

    public void openSolenoidReverse(){
        open.set(DoubleSolenoid.Value.kReverse);
    }

    public void intakeSolenoidForward(){
        intake.set(DoubleSolenoid.Value.kForward);
    }

    public void spinnerSolenoidForward(){
        spinner.set(DoubleSolenoid.Value.kForward);
    }

    public void openSolenoidForward(){
        open.set(DoubleSolenoid.Value.kForward);
    }

    public void intakeSolenoidOff(){
        intake.set(DoubleSolenoid.Value.kOff);
    }

    public void spinnerSolenoidOff(){
        spinner.set(DoubleSolenoid.Value.kOff);
    }

    public void openSolenoidOff(){
        open.set(DoubleSolenoid.Value.kOff);
    }

    public void runIntake(){
        if(RobotMap.joystick.getRawButton(9)){
            intakeSpark6.set(0.45);
        }
        else if(RobotMap.joystick.getRawButton(4)){
            intakeSpark6.set(-0.45);
        }
        else {
            intakeSpark6.set(0);
        }
    }


    
}