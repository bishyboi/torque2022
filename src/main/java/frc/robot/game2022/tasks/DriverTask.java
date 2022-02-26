package frc.robot.game2022.tasks;

import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.revrobotics.Config;
//import com.revrobotics.*;

import frc.robot.lib.ConfigurationService;
import frc.robot.lib.components.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import frc.robot.lib.components.Xbox;
import frc.robot.lib.components.Camera;
import java.time.Clock;
import frc.robot.game2022.modules.Arm;
import frc.robot.game2022.modules.Combine;

/**
 * Created by Nick Sloss on 2/7/2017.
 */
public class DriverTask
{
    //Defaults for any game (driving)
    private final Xbox driver;
    

    private final DriveTrain driveTrain;
    private final Arm arm;
    private final Combine combine;
    private Camera camera;

    // TODO: ABSOLUTELY NEEDS TUNING
    private final int maxVoltage =1; 
    
    Clock clock;
    private final long lockoutPeriod = 500;//in milliseconds 0.001s
    private long lastPress = 0;
    private boolean driveFront = true;
   
    /**
     * Xbox constructor
     * @param driver The driver gamepad
     * @param eyes the camera
     * @param drivetrain The drivetrain responsible for robot control
     * @param arm the arm
     * @param combine the combine :)
     */
    public DriverTask(int port, DriveTrain driveTrain, Camera camera, Arm arm, Combine combine)
    {
        this.driver = new Xbox(port);
        this.driveTrain = driveTrain;
        this.camera = camera;
        this.arm = arm;
        this.combine = combine;     
    }

    /**
     * Runs the driver controller during teleop period. Constantly cycled through in teleopPeriodic()
     */
    public void teleop()
    {
        //Reading in axes from Controller and if they're within the deadband range, it sets it to 0
        double left_y = deadband(driver.getAxis(ConfigurationService.LEFT_Y_AXIS));
        double right_y = deadband(driver.getAxis(ConfigurationService.RIGHT_Y_AXIS));

        //Add conversions to power output based on controls here
        double leftPower = left_y;
        double rightPower = right_y;

        double armLowerPower = maxVoltage/2;
        double armUpperPower = maxVoltage/2;
        double intakePower = maxVoltage/2;
        double liftPower = maxVoltage/2;

        //To make sure no side go OutOfBounds with the power and crash the code (other side is divdided to keep relative power)
        if (Math.abs(leftPower) > 1)
        {
            leftPower /= Math.abs(leftPower);
            rightPower /= Math.abs(leftPower);
        }
        if (Math.abs(rightPower) > 1)
        {
            leftPower /= Math.abs(rightPower);
            rightPower /= Math.abs(rightPower);
        }
        

        //Sets output to 25% normal power if RB is pressed 
        if(isSlowed())
        {
            leftPower *= 0.25;
            rightPower *= 0.25;
        }

        // //Automatically centers the robot if B is held
        // if(isRunningAutoVision())
        // {
        //     camera.setDriverMode(false);
        //     leftPower -= camera.getSteering_Adjust();
        //     //leftPower -= camera.getDistance_Adjust();  really only for autonomous
        //     rightPower += camera.getSteering_Adjust();
        //     //rightPower -= camera.getDistance_Adjust(); really only for autonomous
        // }
        // else
        // {
        //     camera.setDriverMode(true);
        // }
        camera.setDriverMode(true);
        
        // //If Y is pressed, toggle which side is front (from driver's perspective)
        // toggleFrontSide();

        if(!driveFront)
        {
            leftPower = -leftPower;
            rightPower = -rightPower;
        }

        //Drive the robot. Should always be last line and alone! Mostly used for debugging
        SmartDashboard.putNumber("Left Power", leftPower);
        SmartDashboard.putNumber("Right Power", rightPower);
        SmartDashboard.putNumber("Left-Y", left_y);
        SmartDashboard.putNumber("Right-Y", right_y);
        
        if(!driver.getButton(ConfigurationService.BTN_A))
        {
            driveTrain.drivePercentageOutput(leftPower, rightPower);
        }
        else
        {
            driveTrain.tankDriveWithFeedforwardPID(-leftPower, rightPower);
        }

        //Lower Arm movement detecting Button LT and RT
        if(driver.getAxisActive(ConfigurationService.LEFT_TRIGGER)){
            arm.lowerMove(armLowerPower);;
        }
        else if(driver.getAxisActive(ConfigurationService.RIGHT_TRIGGER))
        {
            arm.lowerMove(-armLowerPower);
        }

        //Upper Arm movement detecting RB and LB
        if(driver.getButton(ConfigurationService.BTN_RB)){
            arm.upperMove(armUpperPower);;
        }
        else if(driver.getButton(ConfigurationService.BTN_LB))
        {
            arm.upperMove(-armUpperPower);
        }
        
        //moving intake motor forward & backward when A & B are pressed respectively
        if(driver.getButton(ConfigurationService.BTN_A))
        {
            combine.intakeMove(intakePower);
        }
        else if(driver.getButton(ConfigurationService.BTN_B))
        {
            combine.intakeMove(-intakePower);
        }
        
        //moving lift motor up & down when X & Y are pressed respectively
        if(driver.getButton(ConfigurationService.BTN_X))
        {
            combine.liftMove(liftPower);
        }
        else if(driver.getButton(ConfigurationService.BTN_Y))
        {
            combine.liftMove(-liftPower);
        }

    }
    /**
     * Checks to see if the driver wants to slow down the drive speed of the car
     * @return boolean testing if Right Bumper is pressed
     */
    private boolean isSlowed()
    {
        return driver.getButton(ConfigurationService.BTN_RB);
    }

    /**
     * Checks to see if the driver wants to automatically drive to a vision target/piece dropoff
     * @return boolean testing if Button B is pressed
     */
    private boolean isRunningAutoVision()
    {
        return driver.getButton(ConfigurationService.BTN_B);
    }
   
    // private void toggleFrontSide()
    // {
    //     if (driver.getButton(ConfigurationService.BTN_Y))
    //     {
    //         boolean timedOut = ((clock.millis() - lastPress) >= lockoutPeriod);
    //         if (driveFront && timedOut)
    //         {
    //             driveFront = false;
    //             lastPress = clock.millis();
    //         }
    //         else if(!driveFront && timedOut)
    //         {
    //             driveFront = true;
    //             lastPress = clock.millis();
    //         }
    //     }
    // }
    /**
     * deadbands a specified input
     * @param input the input of the axis
     * @return the corrected axis.
     */
    private double deadband(double input) {
        if (Math.abs(input)< ConfigurationService.JOYSTICK_DEADZONE){
            return 0.0;
        }
        else{
            return input;
        }
    }

    /**
     * gets the native xbox controller
     * @return The xbox controller
     */
    public Xbox getDriver()
    {
        return driver;
    }

    /*
    !!!ACCELERATE METHOD AND SUBSEQUENT ONES HAVE BEEN ARCHIVED FOR LACK OF EFFECTIVE USE!!! (still kept just in case)!!!
    if (isSlowed()) {

            leftPower /= 1.5;
            rightPower /= 1.5;
            driver.setWasSlowed(true);
            driver.resetInstances();
        }
        if(driver.getWasSlowed() && !isSlowed())
        {
            double holdLeftPower = leftPower;
            leftPower = driver.accelerate(leftPower, rightPower);
            rightPower = driver.accelerate(rightPower, holdLeftPower);
        }
    */
}
