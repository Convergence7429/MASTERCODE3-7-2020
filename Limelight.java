package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class Limelight {
    NetworkTableEntry tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv");
    NetworkTableEntry tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx");
    NetworkTableEntry ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty");
    public double[] ballTracking() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
        double steering = 0.0;
        double drive = 0.0;
        double[] steer = new double[3];
        double validTarget = tv.getDouble(0.0);
        SmartDashboard.putNumber("x", tx.getDouble(0.0));
        SmartDashboard.putNumber("valid target", validTarget);
        if (validTarget == 1) {
            double x = tx.getDouble(0.0);
            double y = ty.getDouble(0.0);
            SmartDashboard.putNumber("y", y);
            if (x > 27) {
                x = 27;
            }
            if (y > 20.5) {
                y = 20.5;
            }
            if (x > 1) {
                steering = 0.8*(x/27) - RobotMap.Limelight_minCommand;
            }
            if (x < 1) {
                steering = 0.8*(x/27) + RobotMap.Limelight_minCommand;
            }
            if (y < 0 && y > -22) {
                drive = -((1+y/22)  * RobotMap.Limelight_driveSensitivity) - 0.2;
            }
            steer[0] = drive;
            steer[1] = steering * RobotMap.Limelight_steerSensitivity;
            steer[2] = 1;
        }
        else {
            steer[0] = 0;
            steer[1] = 0;
            steer[2] = 0;
        }
        return steer;
    }
    public double[] launcherTracking() { // GEAR RATIO FOR HOOD IS 6. BIG ONE IS 72, SMALL ONE IS 12
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(1);
        double rotate = 0.0;
        double validTarget = tv.getDouble(0.0);
        double[] launch = new double[4];
        if (validTarget == 1) {
            double x = tx.getDouble(0.0);
            double y = ty.getDouble(0.0);
            if (x > 27) {
                x = 27;
            }
            if (x > 1) {
                rotate = x/27;
            }
            if (x < 1) {
                rotate = x/27;
            }
            rotate = rotate * RobotMap.Limelight_steerSensitivity;
            // double distance = (RobotMap.Launcher_targetHeight - RobotMap.Launcher_launchHeight) / Math.tan(y + RobotMap.Launcher_camOffset);
            // double[] speedAndAngle = Conversions.launcherTrajectory(distance);
            launch[0] = rotate;
            // launch[1] = speedAndAngle[0];
            // launch[2] = 0;
            // launch[3] = 1;
        }
        else {
            // launch[0] = 0;
            launch[1] = 0;
            // launch[2] = 0;
            // launch[3] = 0;
        }
        return launch;
    }
}