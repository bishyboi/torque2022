package frc.robot.game2022.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.lib.ConfigurationService;
import frc.robot.lib.components.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import frc.robot.lib.components.Xbox;
import frc.robot.lib.components.Camera;
import java.time.Clock;

public class Combine 
{
    //Motors that refer to the lower and upper parts of the hanging arm motor
    public final WPI_TalonSRX intakeMotor;
    public final WPI_TalonSRX liftMotor;

    private final double maxDistinRev= 10; //The maximum revolutions required to reach the height of the combine
    private double ticksperRev = 4096; //Amount of encoder ticks per revolution

    public Combine()
    {
        intakeMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_INTAKE);
        liftMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_LIFT);


        intakeMotor.setNeutralMode(NeutralMode.Brake);
        liftMotor.setNeutralMode(NeutralMode.Brake);


        }

    public void intakeMove(double power)
    {
        intakeMotor.set(ControlMode.PercentOutput, power);
        //intakeMotor.setVoltage(power);
    }

    /**
     * Returns a raw sensor data for the Encoder
     * @return liftMotor.getSelectedSensorPosition()
     */
    public double getLiftPosition()
    {
        return liftMotor.getSelectedSensorPosition();
    }

    /**
     * Method to move the lift motor of the Combine system
     * @param encoderTicks Will move the motor based on its encoder value
     */
    public void liftMove(boolean up){
        if (up){
            liftMotor.set(ControlMode.Position, maxDistinRev * ticksperRev);
        }
        else{
            liftMotor.set(ControlMode.Position, -maxDistinRev * ticksperRev);
        }
    }
}
