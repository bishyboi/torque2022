package frc.robot.lib.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;//Mainly for debugging
import frc.robot.lib.ConfigurationService;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;



/**
 * Created by Nick Sloss on 1/31/17.
 */
public class DriveTrain
{
    //40, 20 and 12 <- gears, according to Alenna

    public final WPI_TalonSRX LeftMaster;
    public final WPI_TalonSRX LeftSlave;
    public final WPI_TalonSRX RightMaster;
    public final WPI_TalonSRX RightSlave;

    private final SimpleMotorFeedforward feedforward;
    private final PIDController leftPID;
    private final PIDController rightPID;

    public final PigeonIMU pigeon;
    private final double[] pigeonYPR = new double[3];
    double heading;

    private final int maxVoltage = 10;

    /**
     * Constructor for DriveTrain.
     */
    public DriveTrain() 
    {
        leftPID = new PIDController(2, 5, 0); // these need values too
        rightPID = new PIDController(2, 5, 0); //^^
        LeftMaster = new WPI_TalonSRX(ConfigurationService.DRIVETRAIN_LEFT_MASTER);
        RightMaster = new WPI_TalonSRX(ConfigurationService.DRIVETRAIN_RIGHT_MASTER);
        LeftSlave = new WPI_TalonSRX(ConfigurationService.DRIVETRAIN_LEFT_SLAVE);
        RightSlave = new WPI_TalonSRX(ConfigurationService.DRIVETRAIN_RIGHT_SLAVE);
        feedforward = new SimpleMotorFeedforward(5, 2); //needs constants? i have no clue what these should be lol

        this.configureTalons();

        pigeon = new PigeonIMU(RightSlave);
        heading = this.getHeading();
    }

    /**
     * Configures talons of the DriveTrain with 2 motors on each side in a Master-Follower config.
     * Also zeroes out the motor positions
     */
    public void configureTalons() 
    {
        /** 
         * Notes: -Use [motor.configContinuousCurrentLimit(value)] if we're ever worried about the 
         *        drivetrain somehow pulling too much power and making the robot die
         *        -And remember to [motor.enableCurrentLimit(true] if you do the above
         *        -Also, this class was written for a drivetrain using 2 motors on each side to drive
         *        (hence the Follower and Master setup)
         */

        LeftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        RightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

        LeftMaster.setNeutralMode(NeutralMode.Brake);
        RightMaster.setNeutralMode(NeutralMode.Brake);
        LeftSlave.set(ControlMode.Follower, LeftMaster.getDeviceID());
        RightSlave.set(ControlMode.Follower, RightMaster.getDeviceID());
    
        this.zero();
    }

    /**
     * Get left encoder sensor position
     *
     * @return The encoder position (in sensor units)
     */
    public double getLeftPos() 
    {
        return this.convertSensorUnits(LeftMaster.getSelectedSensorPosition(0));
    }

    /**
     * Get right encoder sensor position
     *
     * @return The encoder (in sensor units)
     */
    public double getRightPos() 
    {
        return this.convertSensorUnits(RightMaster.getSelectedSensorPosition(0));
    }

    /**
     * !!!!WIP!!!!
     * Converts encoder position from local units to cm
     * @return The encoder (in cm)
     */
    public double convertSensorUnits(double sensorUnits)
    {
        return sensorUnits;
    }

    /**
     * Get the heading of the robot
     *
     * @return The heading
     */
    public double getHeading() 
    {
        this.pigeon.getYawPitchRoll(pigeonYPR);
        return pigeonYPR[0];
    }

    /**
     * Zeroes out all encoders
     */
    public void zero() 
    {
        LeftMaster.setSelectedSensorPosition(0, 0, 10);
        RightMaster.setSelectedSensorPosition(0, 0, 10);
        LeftSlave.setSelectedSensorPosition(0, 0, 10);
        RightSlave.setSelectedSensorPosition(0, 0, 10);
    }

    /** 
     * Set each side of the robot to drive a certain percentage
     * @param left Percentage of power from 0.01 to 1 left motor
     * @param right Percentage of power from 0.01 to 1 to right motor
     * unused with PID, only use voltage output
     */
    public void drivePercentageOutput(double left, double right) 
    {
        LeftMaster.set(ControlMode.PercentOutput, left);
        RightMaster.set(ControlMode.PercentOutput, right);
    }

    /**
     * Set each side of the robot to drive with a certain amount of voltage (no PID or error correction)
     * @param left Volts, from 0 to 10 left motor
     * @param right Volts, from 0 to 10 right motor
     */
    public void driveVoltageOutput(double left, double right)
    {
        LeftMaster.setVoltage(left);
        RightMaster.setVoltage(right);
    }

    /**
     * Set each side of the motor to a certain voltage and use PID and feedforward for error correction
     */
    public void tankDriveWithFeedforwardPID(double leftVoltsSetpoint, double rightVoltsSetpoint)
    {
        LeftMaster.setVoltage(feedforward.calculate(leftVoltsSetpoint)
            + leftPID.calculate(LeftMaster.getBusVoltage(), leftVoltsSetpoint));
        RightMaster.setVoltage(feedforward.calculate(rightVoltsSetpoint)
            + rightPID.calculate(RightMaster.getBusVoltage(), rightVoltsSetpoint));
      }
}
