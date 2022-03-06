package frc.robot.game2022.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.lib.ConfigurationService;
import frc.robot.lib.components.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import frc.robot.lib.components.Xbox;
import frc.robot.lib.components.Camera;
import java.time.Clock;

public class Combine {
    //Motors that refer to the lower and upper parts of the hanging arm motor
    public final WPI_TalonSRX intakeMotor;
    public final WPI_TalonSRX liftMotor;

    private final double maxLowerDist = 10; //DISTANCE IN ENCODER TICKS
    private double currentDist; //DISTANCE IN ENCODER TICKS

    public Combine(){
        intakeMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_INTAKE);
        liftMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_LIFT);

        currentDist=0;

        intakeMotor.setNeutralMode(NeutralMode.Brake);
        liftMotor.setNeutralMode(NeutralMode.Brake);


        }

    public void intakeMove(double power){
        intakeMotor.set(ControlMode.PercentOutput, power);
        //intakeMotor.setVoltage(power);
    }

    /**
     * Determines if there is available space for the liftMotor to move without breaking the robot based on the pre-determined maxLowerDist
     */
    private boolean canMove(double encoderTicks){
        double futureDist= this.currentDist + encoderTicks;

        if ((futureDist<=maxLowerDist) && (futureDist>= 0)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Returns a value between 0 and 1 to show how far the lower lift can move
     * @return currentDist/maxLowerDist
     */
    public double getLiftPosition(){
        return currentDist/maxLowerDist;
    }

    /**
     * Method to move the lift motor of the Combine system
     * @param encoderTicks Will move the motor based on encoder ticks
     */
    public void liftMove(double encoderTicks){
        if (this.canMove(encoderTicks)){
            this.liftMotor.set(ControlMode.Position,encoderTicks);

            currentDist += encoderTicks;
        }
    }
}
