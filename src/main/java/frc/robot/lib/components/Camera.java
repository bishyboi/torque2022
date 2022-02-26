package frc.robot.lib.components;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Camera
{
    private NetworkTable table;
    private NetworkTableEntry tx, ty, ta; //x and y angle offset, ta: area of the screen the target takes up
    
    //TODO: adjust mountAngle heightDiff and finalDistance when limelight is mounted on the robot
    public double x,y,area;
    private final double mountAngle = 0; //Angle the camera is mounted at. DOUBLE CHECK THIS IS ACCURATE
    private final double heightDiff = 5.5; //Height from camera to goal. DOUBLE CHECK THIS IS ACCURATE
    private final double finalDistance = 36; //Distance we want to be from the goal. DOUBLE CHECK THIS IS ACCURATE
    /**
     * Intializes vision
     */
    public Camera()
    {
        table = NetworkTableInstance.getDefault().getTable("limelight");

        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }
    
    /**
     * changes modes between teleOP and autonomous for camera
     */
    public void setDriverMode(boolean isDriving)
    {
        if(isDriving)
        {
            table.getEntry("camMode").setNumber(1);
            table.getEntry("ledMode").setNumber(1);
        }
        else
        {
            table.getEntry("camMode").setNumber(0);
            table.getEntry("ledMode").setNumber(3);
        }
    }

    /**
     * primarily for testing
     * @return double to display x offset angle
     */
    public double getxOffset()
    {
        return tx.getDouble(0.0);
    }

    /**
     * calculates the distance from the robot to the target (one dimensional vector) Only works on a target at fixed height
     */
    public double calculateDistance()
    {
        if(ty.getDouble(0.0) <= 0.01)
        {
            return 0;
        }
        return (heightDiff/Math.tan(Math.toRadians(mountAngle+ty.getDouble(0.0))));
    }
    
    public double getDistance_Adjust()
    {   
        double distance_adjust;
        double kpDistance = 0.01f; //proportional gain for distance
        double distance_error = calculateDistance() - finalDistance;
        
        if (distance_error <= 0)
        {
            distance_adjust = 0;
        }
        else
        {
            distance_adjust = distance_error * kpDistance;
        }

        SmartDashboard.putNumber("Distance", calculateDistance());
        SmartDashboard.putNumber("Distance Adjust", distance_adjust);
        SmartDashboard.putNumber("Distance Error", distance_error);
        SmartDashboard.putNumber("Ty", ty.getDouble(0.0));
        return distance_adjust;
    }

    public double getSteering_Adjust()
    {
        double steering_adjust = 0.0;
        double kpSteering = 0.015f;
        double min_command = 0.07f; //all speed commands will be no less than this value due to friction, but can make accuracy difficult
        //-0.02 kP, 0.08f <--- pretty optimal numbers
        double heading_error = tx.getDouble(0.0);
        
        if (heading_error > 1.0)
        {
            steering_adjust = kpSteering*heading_error + min_command;
        }
        else if (heading_error == 0)
        {
            steering_adjust = 0;
        }
        else if (heading_error < 1.0)
        {
            steering_adjust = kpSteering*heading_error - min_command;
        }

        return steering_adjust;
    }
}