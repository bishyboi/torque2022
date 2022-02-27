package frc.robot.game2022.tasks;

import frc.robot.lib.components.DriveTrain;
import frc.robot.lib.components.Camera;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.game2022.modules.Combine;

public class AutoTask {
    private DriveTrain driveTrain;
    private Camera camera;
    private Encoder encoder;
    private Combine combine;

    private double finalDistance = 36; // TODO: find distance from goal to camera after limelight is mounted
    public AutoTask(DriveTrain driveTrain, Camera camera, Encoder encoder, Combine combine)
    {
        this.driveTrain = driveTrain;
        this.camera = camera;
        this.encoder = encoder;
        this.combine = combine;
    }

    public void initialize() {
    }


    public void loop() {
        if (camera.getSteering_Adjust()==0&&camera.getDistance_Adjust(finalDistance)==0){
            // push ball out
        }else{
            this.centerAlign();
        }
        // double leftOutput;
        // double rightOutput;
    
        // /*
        // if (m_usePID) {
        //   double leftFeedforward =
        //       m_feedforward.calculate(leftSpeedSetpoint,
        //           (leftSpeedSetpoint - m_prevSpeeds.leftMetersPerSecond) / dt);
    
        //   double rightFeedforward =
        //       m_feedforward.calculate(rightSpeedSetpoint,
        //           (rightSpeedSetpoint - m_prevSpeeds.rightMetersPerSecond) / dt);
    
        //   leftOutput = leftFeedforward
        //       + m_leftController.calculate(m_speeds.get().leftMetersPerSecond,
        //       leftSpeedSetpoint);
    
        //   rightOutput = rightFeedforward
        //       + m_rightController.calculate(m_speeds.get().rightMetersPerSecond,
        //       rightSpeedSetpoint);
        // } else {}
        //     */
        // leftOutput = leftSpeedSetpoint;
        // rightOutput = rightSpeedSetpoint;
    
        // System.out.println("Left: " + leftOutput + "    | Right: " + rightOutput);
        // //T O D O: Convert leftOutput and rightOutput into voltages or PercentOutput
        // double leftPower = leftOutput;
        // double rightPower = rightOutput;
        // //Conversion takes place above ^^^^
        
        // driveTrain.driveVoltageOutput(leftPower, rightPower);

        // prevTime = curTime;
        // this.update();
        // //prevSpeeds = targetWheelSpeeds;   <--- For PID
    }

    public void centerAlign()
    {
        double leftPower = 0;
        double rightPower = 0;
        leftPower -= camera.getSteering_Adjust();
        leftPower -= camera.getDistance_Adjust(finalDistance);
        rightPower += camera.getSteering_Adjust();
        rightPower -= camera.getDistance_Adjust(finalDistance);
        driveTrain.drivePercentageOutput(leftPower, rightPower);
    }
}
/*
point to reflective tape and move to hub
    AutoTask.centerAlign()
drop ball
    Combine.dropBall()
move out
    move (double distance)
(find ball maybe)
*/