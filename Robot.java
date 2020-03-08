package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.can.*;
import com.revrobotics.ControlType;
import com.ctre.phoenix.motorcontrol.*;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.*;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.cscore.VideoMode;
//import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.cameraserver.CameraServer;


public class Robot extends TimedRobot {
  public static Gyro gyro = new Gyro();
  private final Climber climber = new Climber();
  Intake intake = new Intake();
  private Shooter shooter = new Shooter();
  public Indexer indexer = new Indexer();
  public ProximitySensor proximitySensor = new ProximitySensor();

  private final DriveTrain driveTrain = new DriveTrain();
  private final Limelight limeLight = new Limelight();
  private int autonomousStage;
  
  CvSink cvSink;
  CvSource outputStream;

  //public UsbCamera usbCamera = new UsbCamera("Rear", "UsbCamera");
  // PixelFormat pixelFormat = new Pixe
  // VideoMode videomode = new VIdeoMode()
  

  @Override
  public void robotInit() {
    // usbCamera.setResolution(400, 400);
    // usbCamera.setVideoMode()
    CameraServer.getInstance().startAutomaticCapture();
    cvSink = CameraServer.getInstance().getVideo();
    outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);
    
    
    climber.climberInit();
    shooter.initShooter();
    intake.initIntake();

    //shooter.initSpark1();
    //intake.initIntake();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    driveTrain.driveTrainAutoInit();
    autonomousStage = 1;
    // driveTrain.driveTrainByInchesGoFromBase(24);
    // driveTrain.seek();

    //shoot()?
    //seek()?
    //shoot()?
  }

  @Override
  public void autonomousPeriodic() {
    if (autonomousStage == 1) {
      shooter.talonShooterGroup.set(0.45);
      // if (shooter.talon6.getSensorCollection().getIntegratedSensorVelocity() > 8000) {
      //   autonomousStage = 2;
      // }
      try{
        Thread.sleep(3000);
      }
      catch (Exception e){}
      autonomousStage = 2;
    }
    if (autonomousStage == 2) {
      indexer.beltSpark7.set(-0.45);
      indexer.indexerSpark5.set(-0.45);
      try{
        Thread.sleep(5000);
      }
      catch (Exception e){}
      autonomousStage = 3;
    }
    if (autonomousStage == 3) {
      shooter.talonShooterGroup.set(0.0);
      indexer.beltSpark7.set(0.0);
      indexer.indexerSpark5.set(0.0);
      if (driveTrain.driveTrainByInches(36)) {
        autonomousStage = 4;
      }
    }
    // if(autonomousStage == 1){
    //   driveTrain.zeroDriveTrainEncoders();
    //   autonomousStage = 2;
    // }

    // if (autonomousStage == 0) {
    //   if (shooter.launchTracking()) {
    //     indexer.indexerSpark5.set(.5);
    //     indexer.beltSpark7.set(.5);
    //     if (!proximitySensor.ballsLoaded() && !proximitySensor.ballsReady()){
    //       indexer.indexerSpark5.set(0.0);
    //       indexer.beltSpark7.set(0.0);
    //       autonomousStage = 1;
    //     }
    //   }
    // }
    // driveTrain.autoDrivePeriodic();
  }

  @Override
  public void teleopInit() {

    //driveTrain.robotInitDriveTrain();
    //driveTrain.autoInitDrive();
    driveTrain.teleopInitDrive();
    //driveTrain.rotate();
    
  }


  @Override
  public void teleopPeriodic() {
    //shooter.testTurret();
    indexer.runIndexer();

    if (RobotMap.joystick.getRawButton(1) == true) {
      if (RobotMap.joystick.getThrottle() > 0) {
        // double[] steer = limeLight.ballTracking();
        // SmartDashboard.putNumber("steer", steer[0]);
        // SmartDashboard.putNumber("drive", steer[1]);
        // driveTrain.driveTrainByLimelight(steer);
        driveTrain.driveTrainByOperatorControl();
        intake.intakeSolenoidReverse();
        shooter.talonShooterGroup.set(0.0);
        intake.intakeSpark6.set(0.5);
        if (proximitySensor.proxSensor1.get() == false) {
          indexer.beltSpark7.set(-0.3);
        }
      }
      else if (RobotMap.joystick.getThrottle() < 0) {
        // shooter.talonShooterGroup.set(0.45);
        // if (shooter.talon6.getSensorCollection().getIntegratedSensorVelocity() > 4000){
        //   indexer.indexerSpark5.set(-.5);
        //   indexer.beltSpark7.set(-.5);
        // }
        shooter.launchTracking();
        }
        else {
          intake.intakeSpark6.set(0);
          indexer.indexerSpark5.set(0);
          indexer.beltSpark7.set(0);
        }
      }
    else {
      SmartDashboard.putBoolean("proxsensor", proximitySensor.proxSensor1.get());
      SmartDashboard.putBoolean("proxsensor2", proximitySensor.proxSensor6.get());
      indexer.beltSpark7.set(0.0);
      intake.intakeSpark6.set(0.0);
      shooter.talonShooterGroup.set(0.0);
      driveTrain.driveTrainByOperatorControl();
      driveTrain.moveALittleBit();
      driveTrain.rotateSlowly();
      if(RobotMap.joystick.getRawButton(11)){
        shooter.spark1PID.setReference(0.0, ControlType.kPosition);
      }
    }
    climber.deployHook();
    climber.deploySpool();
    if (RobotMap.joystick.getRawButton(4)) {
      intake.intakeSolenoidForward();
    }
    // if (RobotMap.joystick.getRawButton(10)) {
    //   SmartDashboard.putBoolean("true", true);
    //   shooter.spark2PID.setReference(-290, ControlType.kPosition);
    // }
    // else if((RobotMap.joystick.getRawButton(9))) {
    //   shooter.spark2PID.setReference(0, ControlType.kPosition);
    // }
    // SmartDashboard.putNumber("encoders", shooter.spark1Encoder.getPosition());

}


  @Override
  public void testPeriodic() {
  //   climber.deploySpool();
  //   climber.deployHook();
  //   shooter.testShooter();
  //   intake.runIntake();
  //   indexer.runIndexer();
  // if (RobotMap.joystick.getRawButton(10)) {
  //   SmartDashboard.putBoolean("true", true);
  //   shooter.spark2PID.setReference(-290, ControlType.kPosition);
  // }


  }

  @Override
  public void disabledInit() {
    //driveTrain.disabledInitDrive();
    autonomousStage = 1;
    driveTrain.zeroDriveTrainEncoders();
    climber.spark4PID.setReference(0, ControlType.kPosition);
    //shooter.turretSpark1
  }
}