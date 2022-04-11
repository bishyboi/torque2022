package frc.robot.game2022.modules;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.lib.ConfigurationService;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Combine {
    // Motors that refer to the lower and upper parts of the hanging arm motor
    public final WPI_TalonSRX intakeMotor;
    public final WPI_TalonSRX liftMotor;

    public static final int maxDistinRev = 10; // The maximum revolutions required to reach the height of the combine
    public static final int ticksperRev = 4096; // Amount of encoder ticks per revolution
    public static final int maxTicks = maxDistinRev * ticksperRev; // Upper limit of the combine lift
    public static final double LIFT_SPEED = 0.05;
    public static final double LIFT_SPEED_DIVISOR = Combine.binlog(maxTicks);

    public Combine() {
        intakeMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_INTAKE);
        liftMotor = new WPI_TalonSRX(ConfigurationService.COMBINE_LIFT);

        intakeMotor.setNeutralMode(NeutralMode.Brake);
        liftMotor.setNeutralMode(NeutralMode.Brake);

        liftMotor.setSelectedSensorPosition(0);
    }

    /**
     * spins the combine to gather balls
     * 
     * @param power is from the controller input
     */
    public void intakeMove(double power) {
        intakeMotor.set(ControlMode.PercentOutput, power);
        // intakeMotor.setVoltage(power);
    }

    /**
     * Returns a raw sensor data for the Encoder
     * 
     * @return liftMotor.getSelectedSensorPosition()
     */
    public int getLiftPosition() {
        return (int) liftMotor.getSelectedSensorPosition();
    }

    /**
     * Determines if the robot is allowed to move based on the direction its trying
     * to go to
     * 
     * @param direction refers to the PercentOutput in the _talon.set() method
     * @returns true if the robot is allowed to move in that direction, otherwise,
     *          false
     */
    public boolean canMove(int direction) {
        /*
         * if ((direction >0) && (this.getLiftPosition()>= maxTicks))
         * {
         * return false;
         * }
         * else if ((direction<0) && (this.getLiftPosition() <0))
         * {
         * return false;
         * }
         * else{
         * return true;
         * }
         */
        return true;
    }

    /**
     * Moves the ball lift taking in the direction
     */
    public void liftMove(int direction) {
        if (this.canMove(direction)) {
            liftMotor.set(ControlMode.PercentOutput, this.determinePower(direction));
        }
    }

    /**
     * Determines the power output based on a function
     */

    public double determinePower(int direction) {
        // //$$ is untested changes
        // double dampener= 2; //TODO: ORIGINALLY 1.5, TWEak at competition
        // //double dampener = 2.5; //$$
        // double outpPower = 0;
        // if (direction ==0){
        // return 0;
        // }
        // else if (direction>0){
        // outpPower = binlog(maxTicks-this.getLiftPosition());
        // //outpPower -= 7; //$$
        // outpPower /= -LIFT_SPEED_DIVISOR;
        // outpPower = (outpPower > -0.1) ? -0.1 : outpPower;
        // return outpPower/dampener;
        // //return (Combine.LIFT_SPEED) * ( (binlog(this.getLiftPosition()))
        // /LIFT_SPEED_DIVISOR);
        // }
        // else{
        // outpPower = binlog(this.getLiftPosition());
        // //outpPower -= 7; //$$
        // outpPower /= LIFT_SPEED_DIVISOR;
        // outpPower = (outpPower < 0.1) ? 0.1 : outpPower;
        // return outpPower/dampener;
        // //return -((Combine.LIFT_SPEED) * ( (binlog( maxTicks-this.getLiftPosition()
        // )) /LIFT_SPEED_DIVISOR));
        // }
        if (direction > 0) {
            return 0.50;
        } else if (direction < 0) {
            return -0.30;
        } else {
            return 0;
        }

    }

    /**
     * Log-Base 2 function
     * 
     * @param int number
     * @return log base 2 (n)
     */
    public static int binlog(int number) // returns 0 for bits=0
    {
        int log = 0;
        if ((number & 0xffff0000) != 0) {
            number >>>= 16;
            log = 16;
        }
        if (number >= 256) {
            number >>>= 8;
            log += 8;
        }
        if (number >= 16) {
            number >>>= 4;
            log += 4;
        }
        if (number >= 4) {
            number >>>= 2;
            log += 2;
        }
        return log + (number >>> 1);
    }
}