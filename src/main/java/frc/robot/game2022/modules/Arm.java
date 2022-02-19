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

public class Arm {
    //TODO: make the entire climbing task :)

    //Motors that refer to the lower and upper parts of the hanging arm motor
    public final WPI_TalonSRX lowerMotor;
    public final WPI_TalonSRX upperMotor;

    public Arm(){
        lowerMotor = new WPI_TalonSRX(ConfigurationService.ARM_LOWER);
        upperMotor = new WPI_TalonSRX(ConfigurationService.ARM_UPPER);

        lowerMotor.setNeutralMode(NeutralMode.Brake);
        upperMotor.setNeutralMode(NeutralMode.Brake);
    }

    public void lowerMove(double power){
        lowerMotor.set(ControlMode.PercentOutput, power);
    }

    public void upperMove(double power){
        upperMotor.set(ControlMode.PercentOutput, power);
    }
}
