package frc.robot;

public class Conversions {
    public static final int inchesToEncoders(double[] data) {
        double doubleEncoders = (data[0]/(data[1] * Math.PI)) * data[2] * data[3];
        // data 0: inches, data 1: wheel size, data 2: encoders per rev, data 3: gear ratio
        int encoders = (int)doubleEncoders;
        return encoders;
    }

    public static double[] launcherTrajectory(double distance) {
        double time = Math.sqrt((RobotMap.Launcher_targetHeight - RobotMap.Launcher_launchHeight)/193.110236);
        double velocityY = (distance/time) + (193.110236 * time);
        double velocityX = distance/time;
        double velocity = Math.sqrt(Math.pow(velocityX,2)+Math.pow(velocityY,2));
        double[] speedAndAngle = new double[2];
        double motorSetSpeed = velocity/(RobotMap.Launcher_motorSpeed * RobotMap.Launcher_gearRatio * RobotMap.Launcher_wheelDiameter * Math.PI);
        if (motorSetSpeed > 1) {
            motorSetSpeed = 1;
        }
        if (motorSetSpeed < -1) {
            motorSetSpeed = -1;
        }
        double angle = Math.toDegrees(Math.acos(velocityX/velocity));
        angle = RobotMap.Launcher_hoodEncodersPerRev/2 * angle/180 * RobotMap.Launcher_hoodGearRatio;
        speedAndAngle[0] = motorSetSpeed;
        speedAndAngle[1] = (int)angle;
        return speedAndAngle;
    }
}