/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.lib.ConfigurationService;
import frc.robot.lib.components.Camera; 
import frc.robot.lib.components.DriveTrain;
import frc.robot.game2022.modules.Arm;
import frc.robot.game2022.modules.Combine;
import frc.robot.game2022.tasks.DriverTask;
import frc.robot.game2022.tasks.SecondaryTask;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  DriverTask driver;
  SecondaryTask secondary;
  //OldAutoTask auto;
  DriveTrain driveTrain;
  Camera camera;
  Arm arm = new Arm();
  Combine combine = new Combine();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    
    //TODO: adjust mountAngle and heightDiff when limelight is mounted on the robot
    camera = new Camera(0,5.5);
    driveTrain = new DriveTrain();
    //auto = new AutoTask(driveTrain, camera);
    driver = new DriverTask(0, driveTrain, camera);
    secondary = new SecondaryTask(1, arm, combine);
    
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
    camera.setDriverMode(false);
  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    //auto.loop(); //TODO: Add in the loop method in AutoTask.java
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
    driver.teleop();    
    secondary.teleop();
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {

  }
  @Override
	public void disabledInit()
	{
   
	}
	@Override
	public void disabledPeriodic()
	{
    driveTrain.drivePercentageOutput(0, 0);
	}
}