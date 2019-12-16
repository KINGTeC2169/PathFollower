package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

class DriveTrain {

    private TalonSRX left;
    private TalonSRX right;

    DriveTrain(){
//
//        Compressor c= new Compressor();
//        c.start();

        //Initialize left side of the driveline
        left = new TalonSRX(0);
        TalonSRX leftTop = new TalonSRX(1);
        TalonSRX leftBottom = new TalonSRX(2);

        left.setInverted(true);
        leftTop.setInverted(false);
        leftBottom.setInverted(true);

        leftTop.follow(left);
        leftBottom.follow(left);

        //Initialize right side of the driveline
        right = new TalonSRX(5);
        TalonSRX rightTop = new TalonSRX(4);
        TalonSRX rightBottom = new TalonSRX(3);

        right.setInverted(false);
        rightBottom.setInverted(false);
        rightTop.setInverted(true);

        rightBottom.follow(right);
        rightTop.follow(right);

        left.config_kP(0,10);
        right.config_kP(0,10);
        left.setSensorPhase(true);
        right.setSensorPhase(true);

    }

    void setLeftVoltage(double val){
        left.set(ControlMode.PercentOutput, val);
        SmartDashboard.putNumber("Left Position", left.getSelectedSensorPosition());
    }

    void setRightVoltage(double val){
        right.set(ControlMode.PercentOutput, val);
        SmartDashboard.putNumber("Right Position", right.getSelectedSensorPosition());
    }

    int getLeftSensorPos(){
        return left.getSelectedSensorPosition();
    }

    int getRightSensorPos(){
        return right.getSelectedSensorPosition();
    }

    void zeroSensors(){
        left.setSelectedSensorPosition(0,0,0);
        right.setSelectedSensorPosition(0,0,0);
    }





}
