/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;

import frc.robot.lib.ConfigurationService;
import frc.robot.lib.components.Camera; 
import frc.robot.lib.components.DriveTrain;
import frc.robot.lib.tools.ColorSensor;
import frc.robot.lib.tools.Ultrasonic;
import frc.robot.game2020.modules.BallManipulator;
import frc.robot.game2020.modules.ControlSwitch;
import frc.robot.game2020.tasks.AutoTask;
import frc.robot.game2020.tasks.DriverTask;
import frc.robot.game2020.tasks.SecondaryTask;




/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private String autoSelected;
  private final SendableChooser<String> chooser = new SendableChooser<>();
  List<Translation2d> choosenPath;
  Pose2d startPose;
  Pose2d finalPose;

  DriverTask driver;
  SecondaryTask secondary;
  AutoTask auto;
  DriveTrain driveTrain;
  BallManipulator ballControl;
  Camera camera;
  ColorSensor color;
  ControlSwitch controlSwitch;
  Ultrasonic ultrasonic;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    chooser.setDefaultOption("Straight", "Straight");
    chooser.addOption("S-Curve", "S-Curve");
    SmartDashboard.putData("Auto Paths", chooser);

    I2C.Port i2cPort = I2C.Port.kOnboard;

    camera = new Camera();
    ultrasonic = new Ultrasonic(ConfigurationService.ULTRASONIC_PORT);
    color = new ColorSensor(i2cPort);
    controlSwitch = new ControlSwitch(color);

    driveTrain = new DriveTrain();
    ballControl = new BallManipulator();

    auto = new AutoTask(driveTrain, camera);
    driver = new DriverTask(0, driveTrain, camera);
    secondary = new SecondaryTask(1, camera, controlSwitch, ultrasonic);
    

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() 
  {
    
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    autoSelected = chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Path selected: " + autoSelected);
    switch (autoSelected) {
      case "Straight":
        //Path for going straight...
        choosenPath.add(new Translation2d(3,0));
        startPose = new Pose2d(0, 0, new Rotation2d(0));
        finalPose = new Pose2d(3, 0, new Rotation2d(0));
        break;
      case "S-Curve":
        choosenPath.add(new Translation2d(1,1));
        choosenPath.add(new Translation2d(2,-1));
        startPose = new Pose2d(0, 0, new Rotation2d(0));
        finalPose = new Pose2d(3, 0, new Rotation2d(0));
        break;
      default:
        //a default path
        choosenPath.add(new Translation2d(0,0));
        startPose = new Pose2d(0, 0, new Rotation2d(0));
        finalPose = new Pose2d(0, 0, new Rotation2d(0));
        break;
    }

    auto.initialize(startPose, finalPose, choosenPath);
    camera.setDriverMode(false);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

    auto.loop();
    SmartDashboard.putNumber("X offset", camera.getxOffset());
  }

  @Override
	public void teleopInit() 
	{
    camera.setDriverMode(true);
	}

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    ultrasonic.testBall();
    
    //Once the field has decided the color, set everything up for it  
    if(DriverStation.getGameSpecificMessage().length() > 0)
    {
      controlSwitch.updateColorChar(DriverStation.getGameSpecificMessage().charAt(0));
    }    
    color.updateColor();

    driver.teleop();
    secondary.teleop();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
    auto.centerAlign();
  }

  @Override
	public void disabledInit()
	{
   
	}

	@Override
	public void disabledPeriodic()
	{
    driveTrain.drivePercentageOutput(0, 0);
    if (color != null){
     /*
      double red = color.r/color.counter;
      double green = color.g/color.counter;
      double blue = color.b/color.counter;
  
      System.out.println("Average color: " + red + ", " + green + ", " + blue);
    */
    }
	}
}
//heeeeeeeeeeeeeeeeey
