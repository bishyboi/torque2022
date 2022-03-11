package frc.robot.game2022.tasks;
//import com.revrobotics.Config;
//import com.revrobotics.*;
import frc.robot.lib.ConfigurationService;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.components.Xbox;
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
    private final double powerPercent = 0.05; //Speed of Climbing Arm //TODO: TWEAK THIS VALUE AT COMPETITION
    
    //Climbing Arm Parameters
    private double armLowerPower;
    private double armUpperPower;

    //Combine Parameters
    private double intakePower;
    private int liftDirection;

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

        // //Upper Arm movement detecting RB and LB
        // if(driver.getButton(ConfigurationService.BTN_RB)){
        //     this.armUpperPower = powerPercent;
        // }
        // else if(driver.getButton(ConfigurationService.BTN_LB))
        // {
        //     this.armUpperPower = -powerPercent;
        // }
        // else
        // {
        //     this.armUpperPower = 0.0;
        // }

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

        //moving lift motor up & down when X & Y are pressed respectively, 
        //but won't allow it if it will exceed sensor limit determined in Combine.java (canMove())
        //note: the canMove check is obsolete but makes it easier to read
        if(driver.getButton(ConfigurationService.BTN_X) && this.combine.canMove(-1))
        {
            this.liftDirection = - 1;
        }
        else if(driver.getButton(ConfigurationService.BTN_Y) && this.combine.canMove(1))
        {
            this.liftDirection = 1;
        }
        else
        { 
            this.liftDirection = 0;
        }


        SmartDashboard.putNumber("Lift Torque", liftDirection);
        SmartDashboard.putNumber("Intake Power", intakePower);
        SmartDashboard.putNumber("Upper arm power", armUpperPower);
        SmartDashboard.putNumber("Lower arm power", armLowerPower);

        SmartDashboard.putNumber("Sensor Position", combine.getLiftPosition());
        SmartDashboard.putBoolean("Can Move", combine.canMove(liftDirection));
        SmartDashboard.putNumber("Adjusted Direction", combine.determinePower(liftDirection));

        this.arm.lowerMove(armLowerPower);
        this.arm.upperMove(armUpperPower);
             
        this.combine.intakeMove(intakePower);

        this.combine.liftMove(liftDirection);

    }
        
    public Xbox getDriver()
    {
        return driver;
    }
}