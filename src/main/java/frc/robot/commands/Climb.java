/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.ClimbSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Climb extends PIDCommand {
  /**
   * Creates a new Climb.
   */
  public Climb(ClimbSubsystem climber, double targetHeight) {
    super(
        // The controller that the command will use
        new PIDController(0, 0, 0), //need to update these constants
        // This should return the measurement
        () -> climber.getCurrentPosition(),
        // This should return the setpoint (can also be a constant)
        targetHeight,
        // This uses the output
        output -> {
          climber.climb(output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
