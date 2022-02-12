// package frc.robot.game2020.modules;

// import com.ctre.phoenix.motorcontrol.ControlMode;
// import com.ctre.phoenix.motorcontrol.NeutralMode;
// import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.lib.tools.Ultrasonic;
// import frc.robot.lib.ConfigurationService;

// public class BallManipulator
// {

//     public final WPI_TalonSRX combine;
//     public final WPI_TalonSRX belt;

//     public BallManipulator()
//     {
//         combine = new WPI_TalonSRX(ConfigurationService.COMBINE);
//         combine.setNeutralMode(NeutralMode.Coast);

//         belt = new WPI_TalonSRX(ConfigurationService.SHOOTER);
//         belt.setNeutralMode(NeutralMode.Brake);
//     }

//     public void runIntake(double power)
//     {
//         combine.set(ControlMode.PercentOutput, .75);//Adjust number as needed
//     }

//     public void runBelt(double power)
//     {
//         belt.set(ControlMode.PercentOutput, power);
//     }
// }