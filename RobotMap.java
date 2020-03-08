package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
public class RobotMap {
	// Button 10: autonomous go 200k encoder counts.
    // Button 11: Zero Yaw on Gyro
    public static int ledPort = 9;
	public static int fieldStartingPosition; // can be 1, 2, or 3. Counting from the left of the field.

	public static double Limelight_steerSensitivity = 0.8;
	public static double Limelight_driveSensitivity = 0.5;
	public static double Limelight_minCommand = 0.2;

    public static int DriveTrain_leftMaster = 1;
    public static int DriveTrain_leftSlave = 0;
    public static int DriveTrain_rightMaster = 3;
	public static int DriveTrain_rightSlave = 2;
	
	public static int ProxSensor_Primary = 1;
	public static int ProxSensor_Secondary = 6;
    
    public static int DriveTrain_wheelSize = 8;
    public static int DriveTrain_encodersPerRev = 2048;
    public static double DriveTrain_gearRatio = 7.56;

    public static int Control_Joystick = 0;
	public static int Control_Controller = 4;
	
	public static double Launcher_targetHeight = 101.69291;
	public static double Launcher_launchHeight = 1;
	public static int Launcher_hoodEncodersPerRev = 42;
	public static int Launcher_motorSpeed = 6380;
	public static double Launcher_gearRatio = 0.1;
	public static double Launcher_wheelDiameter = 8;
	public static double Launcher_hoodGearRatio = 0.1;
	public static double Launcher_camOffset = 0;

	public static int intakeSolenoidIn = 4;
	public static int intakeSolenoidOut = 3;
	public static int spinnerSolenoidIn = 5;
	public static int spinnerSolenoidOut = 2;
	public static int openSolenoidIn = 6;
	public static int openSolenoidOut = 1;

    //public static double DriveTrain_limitSpeed = 0.8;
    public static double DriveTrain_limitSpeed = 0.75;

    public static Joystick joystick = new Joystick(RobotMap.Control_Joystick);
    public static Joystick controller = new Joystick(RobotMap.Control_Controller);


    /**
	 * Which PID slot to pull gains from. Starting 2018, you can choose from
	 * 0,1,2 or 3. Only the first two (0,1) are visible in web-based
	 * configuration.
	 */
	public static final int kSlotIdx = 0;

	/**
	 * Talon SRX/ Victor SPX will supported multiple (cascaded) PID loops. For
	 * now we just want the primary one.
	 */
	public static final int kPIDLoopIdx = 0;

	/**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
	public static final int kTimeoutMs = 0;
	
	/* Choose so that Talon does not report sensor out of phase */
	public static boolean kSensorPhase = true;

	/**
	 * Choose based on what direction you want to be positive,
	 * this does not affect motor invert. 
	 */
	public static boolean kMotorInvert = false;

	/**
	 * Gains used in Positon Closed Loop, to be adjusted accordingly
     * Gains(kp, ki, kd, kf, izone, peak output);
     */
	static final Gains kGains = new Gains(.1, 0, 0.2, 0, 0, 1.0); // p was .4
		// kP = _kP;
		// kI = _kI;
		// kD = _kD;
		// kF = _kF;
		// kIzone = _kIzone;
		// kPeakOutput = _kPeakOutput;

}