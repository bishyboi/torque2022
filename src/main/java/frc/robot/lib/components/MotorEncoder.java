package frc.robot.lib.components;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;//Mainly for debugging
import frc.robot.lib.ConfigurationService;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Encoder;

public class MotorEncoder {

    /**
     *  motorId is the id for the motor specified in CongifurationService
     *  encoder is the encoder
     *  distance is the distance that the specified motor travelled (encoder deals with
     * units and motor power nd stuff)
     */

    private int motorId;
    private Encoder encoder;
    private double distance;

    public MotorEncoder(int id)
    {
        encoder = new Encoder(0, 1); //no idea what the params should be ('channel a' and 'channel')
        motorId = id;
        distance = 0;
    }

    public double getDist()
    {
        return encoder.getDistance();
    }
}
