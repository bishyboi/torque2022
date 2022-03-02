// package frc.robot.game2022.oldtasks;

// import java.util.List;


// import frc.robot.lib.components.DriveTrain;
// import frc.robot.lib.components.Camera;
// import edu.wpi.first.math.kinematics.ChassisSpeeds;
// import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
// import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
// import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import edu.wpi.first.math.trajectory.Trajectory;
// import edu.wpi.first.math.trajectory.TrajectoryConfig;
// import edu.wpi.first.math.trajectory.TrajectoryGenerator;
// import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
// import edu.wpi.first.math.controller.PIDController;
// import edu.wpi.first.math.controller.RamseteController;
// import edu.wpi.first.math.controller.SimpleMotorFeedforward;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.math.util.Units;

// import edu.wpi.first.wpilibj2.command.RamseteCommand;

// public class OldAutoTask
// {

//     //REPLACE THESE FROM FRC-CHARACTERIZATION
//     public static final double ksVolts = 0.22;//Volt                    | Displacement
//     public static final double kvVolts = 1.98;//SecondsPerMeter         | Velocity
//     public static final double kaVolts = 0.2;//SecondsSquaredPerMeter   | Acceleration
//     public static final double kPDriveVel = 8.5;

//     //SET THESE TO WHAT WE WANT THEM TO BE
//     public static final double maxVelocity = 10; //MetersPerSecond
//     public static final double maxAcceleration = 10;//MetersPerSecondSquared

//     //Given by the example, I'll trust this works
//     public static final double kRamseteB = 2;
//     public static final double kRamseteZeta = 0.7;

//     private final double driveTrainWidth = 23.8; //Width between wheels from end to end (from middle to middle is 23.0)
//     private DriveTrain driveTrain;
//     private Camera camera;
//     private DifferentialDriveKinematics kinematics; //help change chassis-speed to individual wheel speed
//     private DifferentialDriveOdometry odometry; //helps estimate position and orientation
//     private Pose2d currentPose;
//     private Rotation2d gyroAngle;
//     private final Timer timer = new Timer();
//     DifferentialDriveVoltageConstraint voltageConstraint;
//     TrajectoryConfig config;
//     Trajectory trajectory;
//     DifferentialDriveWheelSpeeds initialSpeeds;
//     RamseteController ramseteController;
//     DifferentialDriveWheelSpeeds prevSpeeds;
//     double prevTime = 0;
//     List<Translation2d> path;
    
//     RamseteCommand ramseteCommand;//Just here so I can jump to its class file

//     double leftVelocity = 0;
//     double rightVelocity = 0;
//     double leftPower = 0;
//     double rightPower = 0;  

//     public OldAutoTask(DriveTrain driveTrain, Camera camera)
//     {
//         this.driveTrain = driveTrain;
//         this.camera = camera;
//         gyroAngle = Rotation2d.fromDegrees(0);
//         kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(driveTrainWidth));
//         odometry = new DifferentialDriveOdometry(gyroAngle);        
//     }

//     public void initialize(Pose2d startPose, Pose2d finalPose, List<Translation2d> path)
//     {
//         currentPose = startPose;

//         voltageConstraint = new DifferentialDriveVoltageConstraint(
//             new SimpleMotorFeedforward(ksVolts, kvVolts, kaVolts),
//             kinematics, 8); //Int at the end of the parameter is the max Voltage allowed    

//         config = new TrajectoryConfig(maxVelocity,maxAcceleration).setKinematics(kinematics)
//             .addConstraint(voltageConstraint);

//         trajectory = TrajectoryGenerator.generateTrajectory(startPose, path, finalPose, config);

//         var initialState = trajectory.sample(0);
//         initialSpeeds = kinematics.toWheelSpeeds(new ChassisSpeeds(initialState.velocityMetersPerSecond,0, 
//             initialState.curvatureRadPerMeter* initialState.velocityMetersPerSecond));

//         timer.reset();
//         timer.start();
//         //PID STUFF GOES HERE
//         /*
//         if (m_usePID) {
//             m_leftController.reset();
//             m_rightController.reset();
//         }   
//         */
//     }


//     public void loop()
//     {
//         double curTime = timer.get();
//        //double dt = curTime - prevTime;
    
//         //T O D O: Pose2d should be replaced with a function that supplies a list of Pose2ds
//         var targetWheelSpeeds = kinematics.toWheelSpeeds(
//             ramseteController.calculate(currentPose, trajectory.sample(curTime)));
    
//         var leftSpeedSetpoint = targetWheelSpeeds.leftMetersPerSecond;
//         var rightSpeedSetpoint = targetWheelSpeeds.rightMetersPerSecond;
    
//         double leftOutput;
//         double rightOutput;
    
//         /*
//         if (m_usePID) {
//           double leftFeedforward =
//               m_feedforward.calculate(leftSpeedSetpoint,
//                   (leftSpeedSetpoint - m_prevSpeeds.leftMetersPerSecond) / dt);
    
//           double rightFeedforward =
//               m_feedforward.calculate(rightSpeedSetpoint,
//                   (rightSpeedSetpoint - m_prevSpeeds.rightMetersPerSecond) / dt);
    
//           leftOutput = leftFeedforward
//               + m_leftController.calculate(m_speeds.get().leftMetersPerSecond,
//               leftSpeedSetpoint);
    
//           rightOutput = rightFeedforward
//               + m_rightController.calculate(m_speeds.get().rightMetersPerSecond,
//               rightSpeedSetpoint);
//         } else {}
//             */
//         leftOutput = leftSpeedSetpoint;
//         rightOutput = rightSpeedSetpoint;
    
//         System.out.println("Left: " + leftOutput + "    | Right: " + rightOutput);
//         //T O D O: Convert leftOutput and rightOutput into voltages or PercentOutput
//         double leftPower = leftOutput;
//         double rightPower = rightOutput;
//         //Conversion takes place above ^^^^
        
//         driveTrain.driveVoltageOutput(leftPower, rightPower);

//         prevTime = curTime;
//         this.update();
//         //prevSpeeds = targetWheelSpeeds;   <--- For PID
//     }


//     public void update()
//     {
//         gyroAngle = Rotation2d.fromDegrees(0);//it's negative b/c Rotation2d uses a unit circle, but gyros have right = Positive
//         currentPose = odometry.update(gyroAngle, driveTrain.getLeftPos(), driveTrain.getRightPos());//Distance NEEDS TO BE IN METERS
//     }


//     //Do we need this?
//     public void resetPose()
//     {

//     }

//     //Ignore this
//     public void centerAlign()
//     {
//         double leftPower = 0;
//         double rightPower = 0;
//         leftPower -= camera.getSteering_Adjust();
//         leftPower -= camera.getDistance_Adjust(36);
//         rightPower += camera.getSteering_Adjust();
//         rightPower -= camera.getDistance_Adjust(36);
//         driveTrain.drivePercentageOutput(leftPower, rightPower);
//     }

//     public double getCircumference(double radius){ //2*pi*r for lazy ppl
//         return Math.PI*radius*2;
//     }

//     public double getDisplacement(double revolutions, double radius){ 
//         return revolutions*getCircumference(radius);
//     }
// }
// /*
// point to reflective tape and move to hub
//     AutoTask.centerAlign()
// drop ball
//     Combine.dropBall()
// move out
//     move (double distance)
// (find ball maybe)
// */