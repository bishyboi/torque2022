package frc.robot.lib;

/**
 * This class should be used to configure values used in the robot.
 * I expect to put values like ports, powers, limits, and thresholds in here.
 * @author Jaxon A Brown
 */
public class ConfigurationService
{
   
    //TALON/VICTOR PORT INTEGERS
    public static final int DRIVETRAIN_LEFT_MASTER    = 4; //1
    public static final int DRIVETRAIN_LEFT_SLAVE     = 5; //0
    public static final int DRIVETRAIN_RIGHT_MASTER   = 6; //2
    public static final int DRIVETRAIN_RIGHT_SLAVE    = 7; //3
    
    //TALON Port Integers for Modules (2022)
    public static final int ARM_LOWER                 = 0; //4
    public static final int ARM_UPPER                 = 1; //5
    public static final int COMBINE_INTAKE            = 2; //6
    public static final int COMBINE_LIFT              = 3; //7

    //Sensors
    public static final int ULTRASONIC_PORT           = 0; 

    //Misc.
    
    //GAMEPAD REFERENCES
    //DO NOT EDIT THIS CODE!!!
    public static final double JOYSTICK_DEADZONE      = 0.1;
    
    public static final int LEFT_X_AXIS		          = 0;
    public static final int LEFT_Y_AXIS		          = 1;
    public static final int RIGHT_X_AXIS	          = 4;
    public static final int RIGHT_Y_AXIS	          = 5;

    public static final int LEFT_TRIGGER              = 2;
    public static final int RIGHT_TRIGGER             = 3;

    public static int BTN_A 			              = 1;
    public static int BTN_B				              = 2;
    public static int BTN_X				              = 3;
    public static int BTN_Y				              = 4;
    public static int BTN_LB			              = 5;
    public static int BTN_RB			              = 6;
    public static int BTN_BACK			              = 7;
    public static int BTN_START			              = 8;
    public static int BTN_LEFT_JOYSTICK	              = 9;
    public static int BTN_RIGHT_JOYSTICK              = 10;

    public static int DPAD_NOT_PRESSED                = -1;

    public static int DPAD_UP		                  = 0;
    public static int DPAD_RIGHT	                  = 90;
    public static int DPAD_DOWN	                      = 180;
    public static int DPAD_LEFT	                      = 270;

    public static int DPAD_NORTH		              = 0;
    public static int DPAD_NORTHEAST	              = 45;
    public static int DPAD_EAST		                  = 90;
    public static int DPAD_SOUTHEAST	              = 135;
    public static int DPAD_SOUTH		              = 180;
    public static int DPAD_SOUTHWEST	              = 225;
    public static int DPAD_WEST		                  = 270;
    public static int DPAD_NORTHWEST	              = 315;
}
