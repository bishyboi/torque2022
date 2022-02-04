package frc.robot.lib.tools;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.revrobotics.ColorSensorV3;

public class ColorSensor{

    public ColorSensorV3 colorSensor;
    public Color8Bit color;
    public final Color8Bit blue = new Color8Bit(45, 115, 94);
    public final Color8Bit green = new Color8Bit(52, 137, 64);
    public final Color8Bit red = new Color8Bit(103, 104, 47);
    public final Color8Bit yellow = new Color8Bit(79, 134, 41);
    private final int uncertainty = 10;

    public double r = 0;
    public double g = 0;
    public double b = 0;
    public int counter = 0;

    public ColorSensor(I2C.Port port)
    {
        colorSensor = new ColorSensorV3(port);
    }

    //Blue: 52.08811475409836, 118.0, 83.51434426229508
    //Green: 0.2119, 0.5387, 0.2495
    //Red: 0.2804, 0.4934, 0.2262
    //Yellow 0.3103, 0.5432, 0.1465


    public void updateColor()
    {
        color = new Color8Bit(colorSensor.getColor());
        if (inRange(blue, color))
        {
            color = blue;
            SmartDashboard.putString("Color", "blue");
        }
        else if(inRange(green, color))
        {
            color = green;
            SmartDashboard.putString("Color", "green");
        }
        else if (inRange(red, color))
        {
            color = red;
            SmartDashboard.putString("Color", "red");
        }
        else if (inRange(yellow, color))
        {
            color = yellow;
            SmartDashboard.putString("Color","yellow");
        }
        else
        {
            SmartDashboard.putString("Color","No valid color");
        }

         /* (for finding average color value)
         r += color.red;
         g += color.green;
         b += color.blue;
         counter ++;
         */
    }

    public Color8Bit getColor()
    {
        color = new Color8Bit(colorSensor.getColor());
        return color;
    }

    //sees if sensor reading (sensor parameter) is close to a given preset color (first input)
    public boolean inRange(Color8Bit expected, Color8Bit reading)
    {
        boolean redInRange = (expected.red - uncertainty < reading.red && reading.red < expected.red + uncertainty);
        boolean greenInRange = (expected.green - uncertainty < reading.green && reading.green < expected.green + uncertainty);
        boolean blueInRange = (expected.blue - uncertainty < reading.blue && reading.blue < expected.blue + uncertainty);
        
        if(redInRange && greenInRange && blueInRange)
        {
            return true;
        }
        else
            return false;
    }

    //used for converting input from driverstation into color8bit
    public Color8Bit charToColor(char colorChar)
    {
        switch(colorChar)
        {
            case 'Y':
                return yellow;
            case 'R':
                return red;
            case 'G':
                return green;
            case 'B':
                return blue;
            default:
                return null;
        }

        
    }
}