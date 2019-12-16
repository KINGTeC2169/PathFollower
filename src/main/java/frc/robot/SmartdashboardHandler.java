package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartdashboardHandler {

    SmartdashboardHandler(){

        SmartDashboard.putNumber("Wheelbase Width", Constants.k_wheelbaseWidth);

        SmartDashboard.putNumber("Max Velocity", Constants.k_max_velocity);
        SmartDashboard.putNumber("Max Accel", Constants.k_max_acceleration);
        SmartDashboard.putNumber("Max Jerk", Constants.k_max_jerk);

        SmartDashboard.putNumber("Left P", Constants.left_p);
        SmartDashboard.putNumber("Left D", Constants.left_d);
        SmartDashboard.putNumber("Left A", Constants.left_a);

        SmartDashboard.putNumber("Right P", Constants.right_p);
        SmartDashboard.putNumber("Right D", Constants.right_d);
        SmartDashboard.putNumber("Right A", Constants.right_a);

        SmartDashboard.putNumber("Gyro P", Constants.gyroP);

    }

    void update(){
        Constants.k_wheelbaseWidth = SmartDashboard.getNumber("Wheelbase Width", 0);

        Constants.k_max_velocity = SmartDashboard.getNumber("Max Velocity", 0);
        Constants.k_max_acceleration = SmartDashboard.getNumber("Max Accel", 0);
        Constants.k_max_jerk = SmartDashboard.getNumber("Max Jerk", 0);

        Constants.left_p = SmartDashboard.getNumber("Left P", 0);
        Constants.left_d = SmartDashboard.getNumber("Left D", 0);
        Constants.left_a = SmartDashboard.getNumber("Left A", 0);

        Constants.right_p = SmartDashboard.getNumber("Right P", 0);
        Constants.right_d = SmartDashboard.getNumber("Right D", 0);
        Constants.right_a = SmartDashboard.getNumber("Right A", 0);

        Constants.gyroP = SmartDashboard.getNumber("Gyro P", 0);
    }

}
