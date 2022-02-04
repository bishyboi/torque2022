package frc.robot.lib.tools;

import edu.wpi.first.wpilibj.AnalogInput;

public class Ultrasonic{

    public final double defaultDistance = 18.5;//find a value for this (the default distance to the wall inside robot(convert to inches in value))
    public final double valueToInches = 0.048; //constant to convert sensor units to inches (based on MaxBotix, Vex may differ)

    private final AnalogInput ultrasonic;

    /**
     * Constructor for Ultrasonic
     */
    public Ultrasonic(int ultrasonicPort)
    {
        ultrasonic = new AnalogInput(ultrasonicPort);
    }

    /**
     * tests if the distance is less than 12 inches (subject to change), start to move motors
     */
    public boolean testBall()
    {
        double currentDistance = ultrasonic.getValue() * valueToInches;
        //System.out.println(currentDistance);
        if(currentDistance <= defaultDistance - .5)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}