/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallTrackingSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class Succ extends CommandBase {
  /**
   * Creates a new Succ.
   */

   //picks balls up and adds balls to the hopper automatically

  int loopsSinceClear = 0; //how long it has been before we stopped seeing anything
  int spacingLoops = 6; //how long it sgould keep going before intaking

  IntakeSubsystem intaker;
  BallTrackingSubsystem tracker;
  HopperSubsystem hopper;
  public Succ(IntakeSubsystem intaker, HopperSubsystem hopper, BallTrackingSubsystem tracker) {
// Use addRequirements() here to declare subsystem dependencies.
    this.intaker = intaker;
    this.tracker = tracker;
    this.hopper = hopper;
    addRequirements(intaker, hopper, tracker);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intaker.intake();

    if (spacingLoops < loopsSinceClear && !tracker.isBallAtBottom()) {
      hopper.moveToShooter();
      loopsSinceClear++;
    } else {
      hopper.stop();
      loopsSinceClear = 0;
    }
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intaker.stopIntake();
    hopper.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return tracker.isHopperFull();
  }
}
