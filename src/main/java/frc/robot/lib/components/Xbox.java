package frc.robot.lib.components;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.lib.ConfigurationService;

/**
 * Created by Nick Sloss on 1/13/18.
 */
public class Xbox {
    private final Joystick wpiJoystick;

    /**
     * Contructor for class
     * 
     * @param joystickPort The port ID that the controller is plugged into
     */
    public Xbox(int joystickPort) {
        int driverNumber = joystickPort;
        wpiJoystick = new Joystick(driverNumber);
    }

    /**
     * Returns if a button is being pressed
     * 
     * @param buttonPort The ID of the button being press (Retrieved using
     *                   ConfigurationService)
     * @return a boolean
     */
    public boolean getButton(int buttonPort) {
        return wpiJoystick.getRawButton(buttonPort);
    }

    /**
     * Makes the controller rumble
     * 
     * @param power the strength of the rumble
     */
    public void setRumble(double power) {
        wpiJoystick.setRumble(GenericHID.RumbleType.kRightRumble, power);

    }

    /**
     * Gets the axis of a Joystick on the controller
     * 
     * @param axisPort ID of the desired joystick to track
     * @return position of the joystick
     */
    public double getAxis(int axisPort) {
        return wpiJoystick.getRawAxis(axisPort);
    }

    public boolean getAxisActive(int axisPort) {
        return (wpiJoystick.getRawAxis(axisPort) > 0.1d) ? true : false;
    }

    /*
     * !!!ACCELERATE METHOD AND SUBSEQUENT ONES HAVE BEEN ARCHIVED FOR LACK OF
     * EFFECTIVE USE!!! (still kept just in case)!!!
     * 
     * private boolean wasSlowed = false;
     * private int instances = 1;
     * 
     * public double accelerate(double xPower, double yPower)
     * {
     * 
     * if(wasSlowed && xPower == yPower && instances < 5 && (xPower != 0 || yPower
     * !=0))
     * {
     * xPower = xPower - Math.pow(0.5,instances);
     * 
     * instances ++;
     * }
     * else
     * {
     * wasSlowed = false;
     * instances = 1;
     * }
     * return xPower;
     * }
     * public void setWasSlowed(boolean slowed)
     * {
     * wasSlowed = slowed;
     * }
     * public boolean getWasSlowed()
     * {
     * return wasSlowed;
     * }
     * public void resetInstances()
     * {
     * instances = 1;
     * }
     */
}
