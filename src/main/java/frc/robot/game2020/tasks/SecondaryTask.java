package frc.robot.game2020.tasks;

import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.lib.ConfigurationService;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;
import frc.robot.lib.components.Xbox;
import frc.robot.lib.components.Camera;
//import frc.robot.game2020.modules.BallManipulator;
//import frc.robot.game2020.modules.Climber;
//import frc.robot.game2020.modules.ControlSwitch;
import frc.robot.lib.tools.Ultrasonic;
import edu.wpi.first.wpilibj.Timer;
import java.time.Clock;

// public class SecondaryTask
// {
//     private final Xbox secondary;

//     private BallManipulator ballControl;
//     private Climber climber;
//     Camera camera;
//     ControlSwitch color;
//     Clock clock;
//     Ultrasonic ultrasonic;

//     double elevatorPower;
//     private final long lockoutPeriod = 500;//in milliseconds 0.001s
//     private long lastPress = 0;
//     private long lastBall = 0;
//     boolean isIntaking = false;
//     private long timeAfterBall = 100; //constant that needs to be found through experimentation (in milliseconds)


//     /**
//      * Xbox constructor
//      * @param driver The driver gamepad
//      * @param eyes the camera
//      * @param drivetrain The drivetrain responsible for robot control
//      * 
//      */
//     public SecondaryTask(int port, Camera camera, ControlSwitch color, Ultrasonic ultrasonic)
//     {
//         this.secondary = new Xbox(port);
//         this.camera = camera;     
//         this.color = color;
//         this.ultrasonic = ultrasonic;
//         this.ballControl = new BallManipulator();
//     }
    
//     public void teleop()
//     {
//         //Shoot when X is pressed
//         if(secondary.getButton(ConfigurationService.BTN_X))
//         {
//             ballControl.runBelt(0.75);
//         }
//         else
//         {
//             ballControl.runBelt(0);
//         }
        
//         //Starts spinning the combine when A is pressed
//         toggleCombine();
//         if (!secondary.getButton(ConfigurationService.BTN_A) && isIntaking)
//         {      
//             ballControl.runIntake(1);
//         }
//         else{
//             ballControl.runIntake(0);
//         }

//         //Color Sensor T O D O: (NEEDS A SAFEGUARD IN CASE PREMATURELY PRESSED) if you try to spin when not on stage 2, warning rumble
//         if(secondary.getButton(ConfigurationService.BTN_Y))
//         {
//             if(!color.completedStage2)
//             {
//                 color.spinThrice();
//             }
//             else if (color.isOnStage3)
//             {
//                 color.runStage3();
//             }
//             else
//             {
//                 secondary.setRumble(0.75);
//             }
//         }

             
//         //Moves elevator
//         elevatorPower = secondary.getAxis(ConfigurationService.LEFT_Y_AXIS);
//         climber.moveElevator(elevatorPower);

//         //Reels in the hook to climb
//         if (secondary.getButton(ConfigurationService.BTN_B))
//         {
//             climber.climb();
//         }

//         //autonomously deals with loading balls in the shooter of one is detected by ultrasonic
//         //T O D O: Might wanna see if we want to wait for the ball to stabalize (stop moving) first. So like,
//         //a lockout period where we see the ball for 0.5s then run the motors
//         if(ultrasonic.testBall())
//         {
//             lastBall = clock.millis();
//             ballControl.runBelt(1);
//         }
//         else if (clock.millis() > lastBall + timeAfterBall)
//         {
//             ballControl.runBelt(1);
//         }
//         else
//         {
//             ballControl.runBelt(0);
//         }
//     }
    
//     public void toggleCombine()
//     {
//         if (secondary.getButton(ConfigurationService.BTN_A))
//         {
//             boolean timedOut = ((clock.millis() - lastPress) >= lockoutPeriod);
//             if (isIntaking && timedOut)
//             {
//                 isIntaking = false;
//                 lastPress = clock.millis();
//             }
//             else if(!isIntaking && timedOut)
//             {
//                 isIntaking = true;
//                 lastPress = clock.millis();
//             }
//         }
//     }
// }
