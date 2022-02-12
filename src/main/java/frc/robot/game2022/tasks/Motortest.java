package frc.robot.game2022.tasks;
import frc.robot.lib.components.Xbox;
import frc.robot.lib.ConfigurationService;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Motortest {
    private WPI_TalonSRX tester;
    public Motortest(int motor){
        tester = new WPI_TalonSRX(motor);
        tester.setNeutralMode(NeutralMode.Brake);
        tester.set(ControlMode.PercentOutput, 0.5);
    }
}


