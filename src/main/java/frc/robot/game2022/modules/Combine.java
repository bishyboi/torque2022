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

    public Combine(){
        intakeMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_INTAKE);
        liftMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_LIFT);

        intakeMotor.setNeutralMode(NeutralMode.Brake);
        liftMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void intakeMove(double power){
        intakeMotor.set(ControlMode.PercentOutput, power);
    }

    public void liftMove(double power){
        liftMotor.set(ControlMode.PercentOutput, power);
    }
}
