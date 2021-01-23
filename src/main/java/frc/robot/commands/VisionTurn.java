/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.VisionDrivePID;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.VisionSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class VisionTurn extends PIDCommand {
  /**
   * Creates a new VisionTurn.
   */
  private boolean runExecute = true;
  public VisionTurn(VisionSubsystem vision, DriveSubsystem drive) {
    super(
        // The controller that the command will use
        new PIDController(VisionDrivePID.kP, VisionDrivePID.kI, VisionDrivePID.kD, .04),
        // This should return the measurement
        () -> -vision.getTargetAngle(),
        // This should return the setpoint (can also be a constant)
        () -> 0,
        // This uses the output
        output -> {
          drive.drive(0, output);
        });
    // Use addRequirements() here to declare subsystem dependencies.
    // Configure additional PID options by calling `getController` here.
    addRequirements(drive);
    getController().setTolerance(.5, .8);
  }

  
  @Override
  public void execute() {
     if(runExecute) {
       super.execute();
       runExecute = false;
     } else {
       runExecute = false;
     }

  } 
  
  
  @Override
  // Returns true when the command should end.
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
