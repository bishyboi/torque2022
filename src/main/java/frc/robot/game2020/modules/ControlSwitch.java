package frc.robot.game2020.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import frc.robot.lib.ConfigurationService;
import frc.robot.lib.tools.ColorSensor;
import edu.wpi.first.wpilibj.util.Color8Bit;



public class ControlSwitch
{
    private final WPI_TalonSRX wheelMotor;
    private final ColorSensor colorSensor;
    public boolean completedStage2 = false;
    public boolean isOnStage3 = false;  

    Color8Bit targetColor;
    Color8Bit referenceColor;
    Color8Bit prevColor;
    int colorCounter = 0;
    char colorChar;


    public ControlSwitch(ColorSensor colorSensor)
    {
        colorChar = DriverStation.getGameSpecificMessage().charAt(0);
        wheelMotor = new WPI_TalonSRX(ConfigurationService.CONTROLPANEL);
        wheelMotor.setNeutralMode(NeutralMode.Coast);
        this.colorSensor = colorSensor;
        prevColor = colorSensor.getColor();
    }

    public void spinThrice()
    {

        //Sets color we base our spins around
        if(referenceColor == null)
        {
            referenceColor = colorSensor.getColor();
        }
        
        //Checks if the color changes onto the set color and increases the counter by one each time it does
        boolean changedColor = (!colorSensor.inRange(prevColor, colorSensor.getColor()));
        boolean isOnReference = (colorSensor.inRange(referenceColor, colorSensor.getColor()));
        if(changedColor && isOnReference)
        {
            colorCounter++;
        }

        //Checks if we've spun enough times + a little extra to be safe
        if(colorCounter == 6 && changedColor)
        {
            completedStage2 = true;
        }
        //Keep spinning the wheel until it has spun 6 times, then stop spinning
        if(!completedStage2)
        {
            runWheel(0.3);
        }
        else
        {
            runWheel(0.0);
        }

        prevColor = colorSensor.getColor();

    }
    //Spin the color wheel until it lands on the selected color, then stop
    public void runStage3()
    {
        if (colorSensor.inRange(targetColor, colorSensor.getColor()))
        {
            runWheel(0.3);
        }
        else
        {
            runWheel(0.0); 
        }
    }
    //Adjust number as needed
    public void runWheel(double power)
    {
        wheelMotor.set(ControlMode.PercentOutput, power);
    }

    public void updateColorChar(char colorChar)
    {
        this.colorChar = colorChar;
        isOnStage3 = true;
        completedStage2 = true;
        targetColor = colorSensor.charToColor(colorChar);
    }
}