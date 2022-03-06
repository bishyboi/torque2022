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

            case 1: // phase 1: steer
                combine.intakeMove(intakePower);
                this.align(alignmentError);

                if( (camera.getSteering_Adjust(alignmentError)==0)){
                    phase++;
                }

            break;

            case 2: // phase 2: move in
                driveTrain.drivePercentageOutput(1, 1);
                
                this.goTo(reflectiveDistance, errorMargin);

                if(camera.getSteering_Adjust(alignmentError)!=0)
                {
                    phase--;
                }
                else if(camera.getDistance_Adjust(reflectiveDistance, errorMargin)==0)
                {
                    phase++;
                }
            break;

            case 3: // phase 3: shoot
                count++;
                combine.intakeMove(-intakePower);

                if(count >= 100){
                    phase++;
                }

            break;

            case 4: // phase 4: move out
                combine.intakeMove(intakePower);
                this.goTo(exitDistance, errorMargin);
            break;
        }
    }

    /**
     * align the robot to a reflective tape at a distance
     * @param distance distance we need to be from the reflective tape
     */
    public void align(double error)
    {
        double leftPower = 0;
        double rightPower = 0;

        leftPower -= camera.getSteering_Adjust(alignmentError);
        rightPower = -leftPower;

        SmartDashboard.putNumber("LeftPower_Autonomous", leftPower);
        SmartDashboard.putNumber("RightPower_Autonomous", rightPower);

        driveTrain.driveVoltageOutput(leftPower, rightPower);
    }

    public void goTo(double distance, double error)
    {
        double drivePower = 0;

        drivePower = -camera.getDistance_Adjust(distance, error);

        SmartDashboard.putNumber("LeftPower_Autonomous", drivePower);
        SmartDashboard.putNumber("RightPower_Autonomous", drivePower);

        driveTrain.driveVoltageOutput(drivePower, drivePower);
    }
}