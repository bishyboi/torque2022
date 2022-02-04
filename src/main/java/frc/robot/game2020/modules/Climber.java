package frc.robot.game2020.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.lib.ConfigurationService;

public class Climber
{
    public final WPI_TalonSRX elevatorMotor;
    public final WPI_TalonSRX climberMotor;

    public Climber()
    {
        climberMotor = new WPI_TalonSRX(ConfigurationService.CLIMBER);
        elevatorMotor = new WPI_TalonSRX(ConfigurationService.ELEVATOR);
        climberMotor.setNeutralMode(NeutralMode.Coast);
        elevatorMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void moveElevator(double power)
    {
        elevatorMotor.set(ControlMode.PercentOutput, power);
    }

    public void climb()
    {
        climberMotor.set(ControlMode.PercentOutput, 0.5);
        climberMotor.setNeutralMode(NeutralMode.Brake);
    }
}