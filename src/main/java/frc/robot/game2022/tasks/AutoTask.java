package frc.robot.game2022.tasks;

import frc.robot.lib.components.DriveTrain;
import frc.robot.lib.components.Camera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.game2022.modules.Combine;

public class AutoTask {
    private DriveTrain driveTrain;
    private Camera camera;
    private Combine combine;

    private final double errorMargin = 5; //inches
    private final double alignmentError = 5; //angle in degrees
    private final double horizontalShootingDistance = 9.5+15.5; //HORIZONTAL distance from reflective tape to camera when shooting
    private final double exitDistance = 15.5+93+9.5+6/*130+6*/; //distance travelled backwards after shooting
    private final double intakePower = 0;

    private int phase = 1;
    private int count = 0; // timer by counting


    public AutoTask(DriveTrain driveTrain, Camera camera, Combine combine)
    {
        this.driveTrain = driveTrain;
        this.camera = camera;
        this.combine = combine;
    }

    public void initialize() {
    }

    public void setPhase (int phase){
        this.phase = phase;
    }

    public void setCount (int count){
        this.count = count;
    }

    public void loop() {

        driveTrain.drivePercentageOutput(-1, -1);
        // switch(phase){

        //     case 1: // phase 1: steer
        //         combine.intakeMove(intakePower);
        //         this.align(alignmentError);
        //         count ++;

        //         if (count < 7.5*50){ // first 7.5 secs:
        //             if( (camera.getSteeringAdjust(alignmentError)==0)){ // good angle: move in
        //                 phase++;
        //             }
        //         }else{ // last 7.5 secs
        //             phase = 4; // taxi out
        //         }

        //     break;

        //     case 2: // phase 2: move in
        //         driveTrain.drivePercentageOutput(0.25, 0.25);
                
        //         this.goTo(horizontalShootingDistance, errorMargin);

        //         count++;
        //         if (count < 7.5*50){ // first 7.5 secs:

        //             if(camera.getSteeringAdjust(alignmentError)!=0) // bad angle: align
        //             {
        //                 phase--;
        //             }
        //             else if(camera.getDistanceAdjust(horizontalShootingDistance, errorMargin)==0) // good angle and distance: next phase and reset count for phase 3
        //             {
        //                 phase++;
        //                 count = 0;
        //             }
        //         }else{ // last 7.5 secs:
        //             phase = 4; // taxi out
        //         }
        //     break;

        //     case 3: // phase 3: shoot - push ball out for 2 secs
        //         count++;
        //         combine.intakeMove(-intakePower);

        //         if(count >= 100){
        //             phase++;
        //         }

        //     break;

        //     case 4: // phase 4: move out
        //         combine.intakeMove(intakePower);
        //         //this.goTo(exitDistance, errorMargin);
        //         driveTrain.drivePercentageOutput(-0.4, -0.4);
        //     break;
        // }
    }

    /**
     * align the robot to a reflective tape at a distance
     * @param distance distance we need to be from the reflective tape
     */
    public void align(double error)
    {
        double leftPower = 0;
        double rightPower = 0;

        leftPower -= camera.getSteeringAdjust(alignmentError);
        rightPower = -leftPower;

        SmartDashboard.putNumber("LeftPower_Autonomous", leftPower);
        SmartDashboard.putNumber("RightPower_Autonomous", rightPower);

        driveTrain.driveVoltageOutput(leftPower, rightPower);
    }

    /**
     * goes to a specified distance from the reflective tape
     * @param distance distance from reflective tape to go to
     * @param error error margin for distance
     */
    public void goTo(double distance, double error)
    {
        double drivePower = 0;

        drivePower = -camera.getDistanceAdjust(distance, error);

        SmartDashboard.putNumber("LeftPower_Autonomous", drivePower);
        SmartDashboard.putNumber("RightPower_Autonomous", drivePower);

        driveTrain.driveVoltageOutput(drivePower, drivePower);
    }
}