package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.TimedRobot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

//I haven't written path following code in over a year, so forgive me, I'm rusty.

public class Robot extends TimedRobot {

  private DriveTrain driveTrain;

  private EncoderFollower m_left_follower;
  private EncoderFollower m_right_follower;

  private Notifier m_follower_notifier;

  private Joystick controller;

  @Override
  public void robotInit() {
    driveTrain = new DriveTrain();

    controller = new Joystick(0);

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    driveTrain.zeroSensors();

    //Define points for path
    Waypoint[] points = new Waypoint[] {
            new Waypoint(0, 0, 0),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
            new Waypoint(120, 120, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
    };

    //Define constants for path generation
    Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST,
            0.05, Constants.k_max_velocity, Constants.k_max_acceleration, Constants.k_max_jerk);

    //Generate Path using FPGA
    Trajectory trajectory = Pathfinder.generate(points, config);

    //Modify path to split into left and right side.
    TankModifier modifier = new TankModifier(trajectory).modify(Constants.k_wheelbaseWidth);
    Trajectory left_trajectory = modifier.getLeftTrajectory();
    Trajectory right_trajectory = modifier.getRightTrajectory();

    //Instantiate encoder followers for left and right side.
    m_left_follower = new EncoderFollower(left_trajectory);
    m_right_follower = new EncoderFollower(right_trajectory);

    m_left_follower.configureEncoder(driveTrain.getLeftSensorPos(), Constants.k_ticks_per_rev, Constants.k_wheel_diameter);
    m_left_follower.configurePIDVA(Constants.left_p, Constants.left_i, Constants.left_d, 1 / Constants.k_max_velocity, Constants.left_a);

    m_right_follower.configureEncoder(driveTrain.getRightSensorPos(), Constants.k_ticks_per_rev, Constants.k_wheel_diameter);
    m_right_follower.configurePIDVA(Constants.right_p, Constants.right_i,  Constants.right_d, 1 / Constants.k_max_velocity,  Constants.right_a);

    //Create 5ms Notifier loop and define it to followPath()
    m_follower_notifier = new Notifier(this::followPath);
    m_follower_notifier.startPeriodic(left_trajectory.get(0).dt);
  }

  private void followPath() {
    //If the path is done, stop the notifier.
    if (m_left_follower.isFinished() || m_right_follower.isFinished()) {
      m_follower_notifier.stop();
    } else {
      //Set left motor to left output, and right motor to right output.
      double left_speed = m_left_follower.calculate(driveTrain.getLeftSensorPos());
      double right_speed = m_right_follower.calculate(driveTrain.getRightSensorPos());
      driveTrain.setLeftVoltage(left_speed);
      driveTrain.setRightVoltage(right_speed);
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit(){
    //When entering teleop, stop notifier no matter the status of the path.
    try{
      m_follower_notifier.stop();
    }
    catch(NullPointerException e){
      DriverStation.reportError("Failed To Stop Notifier!", false);
    }

    //Stop the drivetrain.
    driveTrain.setLeftVoltage(0);
    driveTrain.setRightVoltage(0);
  }

  @Override
  public void teleopPeriodic() {
      driveTrain.setLeftVoltage(controller.getRawAxis(1));
      driveTrain.setRightVoltage(controller.getRawAxis(5));
  }

  @Override
  public void testPeriodic() {

  }

}
