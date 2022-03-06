package frc.robot.game2022.tasks;

import frc.robot.lib.components.DriveTrain;
import frc.robot.lib.components.Camera;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.game2022.modules.Combine;

public class AutoTask {
    private DriveTrain driveTrain;
    private Camera camera;
    private Combine combine;

    private final double errorMargin = 3;
    private final double alignmentError = 1;
    private final double reflectiveDistance = 36; // TODO: find distance from reflective tape to camera after limelight is mounted
    private final double exitDistance = 130;
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

        switch(phase){

            case 1: // phase 1: move to shooting location
                combine.intakeMove(intakePower);
                this.centerAlign(reflectiveDistance, errorMargin);

                if( (camera.getSteering_Adjust(alignmentError)==0) &&  (camera.getDistance_Adjust(reflectiveDistance, errorMargin)==0)){
                    //phase++;
                }

            break;

            case 2: // phase 2: shoot
                count++;
                combine.intakeMove(-intakePower);

                if(count >= 100){
                    phase++;
                }

            break;

            case 3: // phase 3: move out
                combine.intakeMove(intakePower);
                this.centerAlign(exitDistance, errorMargin);

            break;
        }
    }

    /**
     * align the robot to a reflective tape at a distance
     * @param distance distance we need to be from the reflective tape
     */
    public void centerAlign(double distance, double error)
    {
        double leftPower = 0;
        double rightPower = 0;

        leftPower -= camera.getSteering_Adjust(alignmentError);
        leftPower -= camera.getDistance_Adjust(distance, error);

        // rightPower += camera.getSteering_Adjust(alignmentError);
        // rightPower -= camera.getDistance_Adjust(distance, error);

        rightPower = -leftPower;

        SmartDashboard.putNumber("LeftPower_Autonomous", leftPower);
        SmartDashboard.putNumber("RightPower_Autonomous", rightPower);

        driveTrain.drivePercentageOutput(leftPower, rightPower);
    }
}