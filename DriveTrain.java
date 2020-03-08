package frc.robot;

import frc.robot.RobotMap;

//import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.ctre.phoenix.motorcontrol.*;


public class DriveTrain {
    WPI_TalonFX leftMaster = new WPI_TalonFX(RobotMap.DriveTrain_leftMaster);
    WPI_TalonFX leftSlave = new WPI_TalonFX(RobotMap.DriveTrain_leftSlave);
    WPI_TalonFX rightMaster = new WPI_TalonFX(RobotMap.DriveTrain_rightMaster);
    WPI_TalonFX rightSlave = new WPI_TalonFX(RobotMap.DriveTrain_rightSlave);

    SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftMaster, leftSlave);
    SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightMaster, rightSlave);

    DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);


    public void robotInitDriveTrain(){

      rightMaster.configFactoryDefault();
      rightSlave.configFactoryDefault();
      leftMaster.configFactoryDefault();
      leftSlave.configFactoryDefault();

      leftMaster.setNeutralMode(NeutralMode.Coast);
      rightMaster.setNeutralMode(NeutralMode.Coast);
      leftSlave.setNeutralMode(NeutralMode.Coast);
      rightSlave.setNeutralMode(NeutralMode.Coast);

      leftMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      leftSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);

      leftMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, RobotMap.kPIDLoopIdx,RobotMap.kTimeoutMs);
      leftMaster.setSensorPhase(RobotMap.kSensorPhase);
      leftMaster.setInverted(RobotMap.kMotorInvert);
      rightMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, RobotMap.kPIDLoopIdx,RobotMap.kTimeoutMs);
      rightMaster.setSensorPhase(RobotMap.kSensorPhase);
      rightMaster.setInverted(RobotMap.kMotorInvert);
      leftSlave.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, RobotMap.kPIDLoopIdx,RobotMap.kTimeoutMs);
      leftSlave.setSensorPhase(RobotMap.kSensorPhase);
      leftSlave.setInverted(RobotMap.kMotorInvert);
      rightSlave.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, RobotMap.kPIDLoopIdx,RobotMap.kTimeoutMs);
      rightSlave.setSensorPhase(RobotMap.kSensorPhase);
      rightSlave.setInverted(RobotMap.kMotorInvert);

      leftMaster.config_kF(RobotMap.kPIDLoopIdx, RobotMap.kGains.kF, RobotMap.kTimeoutMs);
      leftMaster.config_kP(RobotMap.kPIDLoopIdx, RobotMap.kGains.kP, RobotMap.kTimeoutMs);
      leftMaster.config_kI(RobotMap.kPIDLoopIdx, RobotMap.kGains.kI, RobotMap.kTimeoutMs);
      leftMaster.config_kD(RobotMap.kPIDLoopIdx, RobotMap.kGains.kD, RobotMap.kTimeoutMs);
      rightMaster.config_kF(RobotMap.kPIDLoopIdx, RobotMap.kGains.kF, RobotMap.kTimeoutMs);
      rightMaster.config_kP(RobotMap.kPIDLoopIdx, RobotMap.kGains.kP, RobotMap.kTimeoutMs);
      rightMaster.config_kI(RobotMap.kPIDLoopIdx, RobotMap.kGains.kI, RobotMap.kTimeoutMs);
      rightMaster.config_kD(RobotMap.kPIDLoopIdx, RobotMap.kGains.kD, RobotMap.kTimeoutMs);
      leftSlave.config_kF(RobotMap.kPIDLoopIdx, RobotMap.kGains.kF, RobotMap.kTimeoutMs);
      leftSlave.config_kP(RobotMap.kPIDLoopIdx, RobotMap.kGains.kP, RobotMap.kTimeoutMs);
      leftSlave.config_kI(RobotMap.kPIDLoopIdx, RobotMap.kGains.kI, RobotMap.kTimeoutMs);
      leftSlave.config_kD(RobotMap.kPIDLoopIdx, RobotMap.kGains.kD, RobotMap.kTimeoutMs);
      rightSlave.config_kF(RobotMap.kPIDLoopIdx, RobotMap.kGains.kF, RobotMap.kTimeoutMs);
      rightSlave.config_kP(RobotMap.kPIDLoopIdx, RobotMap.kGains.kP, RobotMap.kTimeoutMs);
      rightSlave.config_kI(RobotMap.kPIDLoopIdx, RobotMap.kGains.kI, RobotMap.kTimeoutMs);
      rightSlave.config_kD(RobotMap.kPIDLoopIdx, RobotMap.kGains.kD, RobotMap.kTimeoutMs);
    }

    public void driveTrainByLimelight(double[] control) {
      drive.arcadeDrive(control[0], control[1]);
    }

    public void seek() {
      drive.curvatureDrive(0.4, 0.4, false);
    }
    

    public void driveTrainByOperatorControl() {
      if (RobotMap.joystick.getThrottle() > 0) {
        drive.curvatureDrive(Math.pow(RobotMap.joystick.getY(),3)*0.5,RobotMap.joystick.getX(), RobotMap.joystick.getRawButton(2));
      }
      else {
        drive.curvatureDrive(Math.pow(-RobotMap.joystick.getY(),3)*0.5,RobotMap.joystick.getX(), RobotMap.joystick.getRawButton(2));
      }
    }

    public void driveTrainAutoInit() {
      configureOutput(.2);
      zeroDriveTrainEncoders();

      
    }

    public boolean driveTrainByInches(double position) {
      double[] data = new double[4];
      data[0] = position;
      data[1] = RobotMap.DriveTrain_wheelSize;
      data[2] = RobotMap.DriveTrain_encodersPerRev;
      data[3] = RobotMap.DriveTrain_gearRatio;
      int encoders = Conversions.inchesToEncoders(data);
      leftMaster.set(ControlMode.Position, encoders);
      leftSlave.set(ControlMode.Position, encoders);
      rightMaster.set(ControlMode.Position, -encoders);
      rightSlave.set(ControlMode.Position, -encoders);
      if (leftMaster.getSensorCollection().getIntegratedSensorPosition() > encoders * 0.97 && leftMaster.getSensorCollection().getIntegratedSensorPosition() < encoders * 1.03) {
        return true;
      }
      return false;
    }

    public void driveTrainByInchesGoFromBase(double position){ // might work if only run once in autoInit();
      double currentEncoderCount = leftMaster.getSensorCollection().getIntegratedSensorPosition();
      double[] data = new double[4];
      data[0] = position;
      data[1] = RobotMap.DriveTrain_wheelSize;
      data[2] = RobotMap.DriveTrain_encodersPerRev;
      data[3] = RobotMap.DriveTrain_gearRatio;
      int encoders = Conversions.inchesToEncoders(data);
      leftMaster.set(ControlMode.Position, encoders + currentEncoderCount);
      leftSlave.set(ControlMode.Position, encoders + currentEncoderCount);
      rightMaster.set(ControlMode.Position, -encoders - currentEncoderCount);
      rightSlave.set(ControlMode.Position, -encoders - currentEncoderCount);
    }

    public void driveTrainByInchesTELEOP(double position){
      leftMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      leftSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      double[] data = new double[4];
      data[0] = position;
      data[1] = RobotMap.DriveTrain_wheelSize;
      data[2] = RobotMap.DriveTrain_encodersPerRev;
      data[3] = RobotMap.DriveTrain_gearRatio;
      int encoders = Conversions.inchesToEncoders(data);
      leftMaster.set(ControlMode.Position, encoders);
      leftSlave.set(ControlMode.Position, encoders);
      rightMaster.set(ControlMode.Position, -encoders);
      rightSlave.set(ControlMode.Position, -encoders);
    }

    public void driveTrainByInchesTeleopForRotation(double position){
      leftMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      leftSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      double[] data = new double[4];
      data[0] = position;
      data[1] = RobotMap.DriveTrain_wheelSize;
      data[2] = RobotMap.DriveTrain_encodersPerRev;
      data[3] = RobotMap.DriveTrain_gearRatio;
      int encoders = Conversions.inchesToEncoders(data);
      leftMaster.set(ControlMode.Position, encoders);
      leftSlave.set(ControlMode.Position, encoders);
      rightMaster.set(ControlMode.Position, encoders);
      rightSlave.set(ControlMode.Position, encoders);
    }

    public void teleopInitDrive(){
      configureOutput(1);
      leftMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      leftSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
    }

 
    public void disabledInitDrive(){
      leftMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      leftSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
    }

    // public void rotate(){
    //   // leftMaster.set(ControlMode.PercentOutput, RobotMap.rightJoystick.getZ());
    //   // leftSlave.set(ControlMode.PercentOutput, RobotMap.rightJoystick.getZ());
    //   // rightMaster.set(ControlMode.PercentOutput, RobotMap.rightJoystick.getZ());
    //   // rightSlave.set(ControlMode.PercentOutput, RobotMap.rightJoystick.getZ());
    //   double heading = Robot.gyro.ahrs.getCompassHeading();
    //   double temp;
    //   double temp1;
    //   if(RobotMap.joystick.getRawButton(7)){
    //     if((heading + 45.0) > 360.0){
    //       temp = (heading + 45.0);
    //       temp1 = temp - 360.0;
    //       while ((Robot.gyro.ahrs.getCompassHeading() < temp1) && (Robot.gyro.ahrs.getCompassHeading() > heading)){
    //         // leftMaster.set(ControlMode.PercentOutput, 0.25);
    //         // leftSlave.set(ControlMode.PercentOutput, 0.25);
    //         // rightMaster.set(ControlMode.PercentOutput, 0.25);
    //         // rightSlave.set(ControlMode.PercentOutput, 0.25);
    //         drive.arcadeDrive(0, 0.25);
    //       }
    //     } else {
    //       while ((Robot.gyro.ahrs.getCompassHeading() < (heading + 45.0))){
    //         drive.arcadeDrive(0, 0.25);
    //       }
    //     }
    //     drive.arcadeDrive(0, 0.25);
    //   }
    // }

    public void moveALittleBit(){
      if(RobotMap.joystick.getPOV() == 0){
        driveTrainByInchesTELEOP(1.5);
      }

      if(RobotMap.joystick.getPOV() == 180){
        driveTrainByInchesTELEOP(-1.5);
      }
    }

    public void rotateSlowly(){
        if(RobotMap.joystick.getPOV() == 90){
            driveTrainByInchesTeleopForRotation(2);
          }
    
          if(RobotMap.joystick.getPOV() == 270){
            driveTrainByInchesTeleopForRotation(-2);
          }
    }

    public void configureOutput(double output){
      leftMaster.configNominalOutputForward(0, RobotMap.kTimeoutMs);
      leftMaster.configNominalOutputReverse(0, RobotMap.kTimeoutMs);
      leftMaster.configPeakOutputForward(output, RobotMap.kTimeoutMs);
      leftMaster.configPeakOutputReverse(-output, RobotMap.kTimeoutMs);
      rightMaster.configNominalOutputForward(0, RobotMap.kTimeoutMs);
      rightMaster.configNominalOutputReverse(0, RobotMap.kTimeoutMs);
      rightMaster.configPeakOutputForward(output, RobotMap.kTimeoutMs);
      rightMaster.configPeakOutputReverse(-output, RobotMap.kTimeoutMs);
      leftSlave.configNominalOutputForward(0, RobotMap.kTimeoutMs);
      leftSlave.configNominalOutputReverse(0, RobotMap.kTimeoutMs);
      leftSlave.configPeakOutputForward(output, RobotMap.kTimeoutMs);
      leftSlave.configPeakOutputReverse(-output, RobotMap.kTimeoutMs);
      rightSlave.configNominalOutputForward(0, RobotMap.kTimeoutMs);
      rightSlave.configNominalOutputReverse(0, RobotMap.kTimeoutMs);
      rightSlave.configPeakOutputForward(output, RobotMap.kTimeoutMs);
      rightSlave.configPeakOutputReverse(-output, RobotMap.kTimeoutMs);
    }


    public void leftPositionAuto(){
      // shooting high basically from starting line
      driveTrainByInchesTELEOP(-12.0);

      // Shoot high very close:
      driveTrainByInchesTELEOP(-117.5);

      // shoot high from far away if other team clashes with us
      driveTrainByInchesTELEOP(-48.0);


    }

    // public int getStage(){
    //   return this.stage;
    // }

    public void zeroDriveTrainEncoders(){
      leftMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightMaster.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      leftSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
      rightSlave.getSensorCollection().setIntegratedSensorPosition(0, RobotMap.kTimeoutMs);
    }

  }