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

public class SecondaryTask {

    private final Xbox driver;
    private final Arm arm;
    private final Combine combine;

    private final double maxVoltage = 2.0;

    
    private double armLowerPower;
    private double armUpperPower;
    private double intakePower;
    private double liftPower;

    public SecondaryTask(int port, Arm arm, Combine combine)
    {
        this.driver = new Xbox(port);
        this.arm = arm;
        this.combine = combine;
    }

    /**
     * for every motor (intake, lift, upper arm, lower arm)
     * sets voltage to boundCap(maxVoltage) or boundCap(-maxVoltage)
     */

    public void teleop()
    {
        //Lower Arm movement detecting Button LT and RT
        if(driver.getAxisActive(ConfigurationService.LEFT_TRIGGER)){
            this.armLowerPower = boundCap(maxVoltage);
        }
        else if(driver.getAxisActive(ConfigurationService.RIGHT_TRIGGER))
        {
            this.armLowerPower = boundCap(-maxVoltage);
        }
        else
        {
            this.armLowerPower = 0;
        }
        
        //Upper Arm movement detecting RB and LB
        if(driver.getButton(ConfigurationService.BTN_RB)){
            this.armUpperPower = boundCap(maxVoltage);
        }
        else if(driver.getButton(ConfigurationService.BTN_LB))
        {
            this.armUpperPower = boundCap(-maxVoltage);
        }
        else
        {
            this.armUpperPower = 0;
        }

        //moving intake motor forward & backward when A & B are pressed respectively
        if(driver.getButton(ConfigurationService.BTN_A))
        {
            this.intakePower = boundCap(maxVoltage);
        }
        else if(driver.getButton(ConfigurationService.BTN_B))
        {
            this.intakePower = boundCap(-maxVoltage);
        }
        else
        {
            this.intakePower = 0;
        }

        //moving lift motor up & down when X & Y are pressed respectively
        if(driver.getButton(ConfigurationService.BTN_X))
        {
            this.liftPower = boundCap(maxVoltage);
        }
        else if(driver.getButton(ConfigurationService.BTN_Y))
        {
            this.liftPower = boundCap(-maxVoltage);
        }
        else
        {
            this.liftPower = 0;
        }



        SmartDashboard.putNumber("Lift Power", liftPower);
        SmartDashboard.putNumber("Intake Power", intakePower);
        SmartDashboard.putNumber("Upper arm power", armUpperPower);
        SmartDashboard.putNumber("Lower arm power", armLowerPower);

        /*
        this.arm.lowerMove(armLowerPower);
        this.arm.upperMove(armUpperPower);

        this.combine.intakeMove(intakePower);
        this.combine.liftMove(liftPower);
        */
    }

    /**
     * 
     * @param powerOutput
     * @return 1 if powerOutput>1, -1 if powerOutput<-1,
     *         powerOutput otherwise
     */
    private double boundCap(double powerOutput)
    {
        // if (Math.abs(powerOutput) > 1)
        // {
        //     return (powerOutput/Math.abs(powerOutput));
        // }
        // else
        // {
        //     return powerOutput;

        return powerOutput; //TODO: Replace this code with what is commented out
        
    }

    public Xbox getDriver()
    {
        return driver;
    }
}
