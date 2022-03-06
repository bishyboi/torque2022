package frc.robot.game2022.tasks;
import com.ctre.phoenix.motorcontrol.ControlMode;
//import com.revrobotics.Config;
//import com.revrobotics.*;
import frc.robot.lib.ConfigurationService;
import frc.robot.lib.components.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.components.Xbox;
import frc.robot.lib.components.Camera;
import java.time.Clock;
import frc.robot.game2022.modules.Arm;
import frc.robot.game2022.modules.Combine;
public class SecondaryTask {

    private final Xbox driver;
    private Arm arm = new Arm();
    private Combine combine = new Combine();

    /*
    Controls the percent of maximum power that a motor can output using talon.set(ControlMode.PercentOutput, power)
    This is a static amount that controls how fast each of the secondary motors will move, 
    adjust between 0.1-1.0 to speed up and slow down the power of the secondary motors
    */
    private final double powerPercent = 1;
    private final double encoderTicks= 4096;
    
    private double armLowerPower;
    private double armUpperPower;
    private double intakePower;
    private boolean liftUp; //True if lift needs to be Up, false if down

    public SecondaryTask(int port, Arm arm, Combine combine)
    {
        this.driver = new Xbox(port);
        this.arm = arm;
        this.combine = combine;
    }

    /**
     * for every motor (intake, lift, upper arm, lower arm)
     * sets voltage to boundCap(powerPercent) or boundCap(-powerPercent)
     */
    public void teleop()
    {
        //Lower Arm movement detecting Button LT and RT
        if(driver.getAxisActive(ConfigurationService.LEFT_TRIGGER)){
            this.armLowerPower = powerPercent;
        }
        else if(driver.getAxisActive(ConfigurationService.RIGHT_TRIGGER))
        {
            this.armLowerPower = -powerPercent;
        }
        else
        {      
            this.armLowerPower = 0.0;
        }

        //Upper Arm movement detecting RB and LB
        if(driver.getButton(ConfigurationService.BTN_RB)){
            this.armUpperPower = powerPercent;
        }
        else if(driver.getButton(ConfigurationService.BTN_LB))
        {
            this.armUpperPower = -powerPercent;
        }
        else
        {
            this.armUpperPower = 0.0;
        }

        //moving intake motor forward & backward when A & B are pressed respectively
        if(driver.getButton(ConfigurationService.BTN_A))
        {
            this.intakePower =  powerPercent;
        }
        else if(driver.getButton(ConfigurationService.BTN_B))
        {
            this.intakePower = -powerPercent;
        }
        else
        {
            this.intakePower = 0.0;
        }

        //moving lift motor up & down when X & Y are pressed respectively
        if(driver.getButton(ConfigurationService.BTN_X))
        {
            this.liftUp = false;
        }
        else if(driver.getButton(ConfigurationService.BTN_Y))
        {
            this.liftUp = true;
        }
        else
        { 
            this.liftUp = false;
        }


        SmartDashboard.putNumber("Lift Power", liftUp);
        SmartDashboard.putNumber("Intake Power", intakePower);
        SmartDashboard.putNumber("Upper arm power", armUpperPower);
        SmartDashboard.putNumber("Lower arm power", armLowerPower);

        SmartDashboard.putNumber("Sensor Position", combine.getLiftPosition());


        this.arm.lowerMove(armLowerPower);
        this.arm.upperMove(armUpperPower);
             
        this.combine.intakeMove(intakePower);


        this.combine.liftMove(liftUp);

    }
    // /**
    //  * 
    //  * @param powerOutput
    //  * @return 1 if powerOutput>1, -1 if powerOutput<-1,
    //  *         powerOutput otherwise
    //  */
    // private double boundCap(double powerOutput)
    // {
    //     // if (Math.abs(powerOutput) > 1)
    //     // {
    //     //     return (powerOutput/Math.abs(powerOutput));
    //     // }
    //     // else
    //     // {
    //     //     return powerOutput;
    //     return powerOutput; //TODO: Replace this code with what is commented out
        
    // }
    public Xbox getDriver()
    {
        return driver;
    }
}