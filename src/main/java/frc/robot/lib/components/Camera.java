package frc.robot.lib.components;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera {
    private NetworkTable table;
    private NetworkTableEntry tx, ty, ta; // x and y angle offset, ta: area of the screen the target takes up
    public double x, y, area;
    private final double mountAngle; // Angle the camera is mounted at. DOUBLE CHECK THIS IS ACCURATE
    private final double heightDiff; // Height from camera to goal. DOUBLE CHECK THIS IS ACCURATE
    private final double xPos;

    /**
     * Intializes vision
     * 
     * @param mountAngle angle the cmaera is mounted at
     * @param heightDiff height from camera to goal
     */
    public Camera(double mountAngle, double heightDiff, double xPos) {
        this.mountAngle = mountAngle;
        this.heightDiff = heightDiff;
        this.xPos = xPos;
        table = NetworkTableInstance.getDefault().getTable("limelight");

        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

    /**
     * changes modes between teleOP and autonomous for camera
     * 
     * @param isDriving true - teleop, false - autonomous
     */
    public void setDriverMode(boolean isDriving) {
        if (isDriving) {
            table.getEntry("camMode").setNumber(1); // normal camera
            table.getEntry("ledMode").setNumber(1); // no limelight
        } else {
            table.getEntry("camMode").setNumber(0); // low exposure to only see reflection
            table.getEntry("ledMode").setNumber(3); // limelight
        }
    }

    /**
     * primarily for testing
     * 
     * @return double to display x offset angle
     */
    public double getxOffset() {
        return tx.getDouble(0.0);
    }

    /**
     * calculates the distance from the robot to the target (one dimensional vector)
     * Only works on a target at fixed height
     */
    public double calculateDistance() {
        if (ty.getDouble(0.0) <= 0.01) {
            return 0;
        }
        return (heightDiff / Math.tan(Math.toRadians(mountAngle + ty.getDouble(0.0))));
    }

    /**
     * 
     * @param finalDistance distance from camera to goal
     * @param errorMargin   error margin
     * @return energy required to reach selected distance
     */
    public double getDistanceAdjust(double finalDistance, double errorMargin) {
        double distanceAdjust;
        double kpDistance = 0.01f; // proportional gain for distance
        double distanceError = calculateDistance() - finalDistance;

        if (Math.abs(distanceError) <= errorMargin) {
            distanceAdjust = 0;
        } else {
            distanceAdjust = distanceError * kpDistance;
        }

        SmartDashboard.putNumber("Distance", calculateDistance());
        SmartDashboard.putNumber("Distance Adjust", distanceAdjust);
        SmartDashboard.putNumber("Distance Error", distanceError);
        SmartDashboard.putNumber("Ty", ty.getDouble(0.0));
        return distanceAdjust;
    }

    /**
     * @param errorMargin degrees of error tolerated
     * @return energy required to steer to face the visible target
     */
    public double getSteeringAdjust(double errorMargin) {
        double steeringAdjust = 0.0;
        double kpSteering = 0.015f;
        double minCommand = 0.07f; // all speed commands will be no less than this value due to friction, but can
                                   // make accuracy difficult
        // -0.02 kP, 0.08f <--- pretty optimal numbers
        double opp = this.calculateDistance() * Math.tan(tx.getDouble(0.0)) + this.xPos;
        double xError = Math.atan(opp / this.calculateDistance());

        // double heading_error = tx.getDouble(0.0);

        if (xError > errorMargin) {
            steeringAdjust = kpSteering * xError + minCommand;
        } else if (xError == 0) {
            steeringAdjust = 0;
        } else if (xError < errorMargin) {
            steeringAdjust = kpSteering * xError - minCommand;
        }

        return steeringAdjust;
    }
}