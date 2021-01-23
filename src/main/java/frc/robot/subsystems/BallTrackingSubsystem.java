/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TrackerConstants;

public class BallTrackingSubsystem extends SubsystemBase {
  /**
   * Creates a new BallTrackingSubsystem.
   */

   DigitalInput topTracker = new DigitalInput(TrackerConstants.topTrackerPort);
   DigitalInput bottomTracker = new DigitalInput(TrackerConstants.bottomTrackerPort);
   

  public BallTrackingSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  public boolean isBallAtTop() {
    return topTracker.get();
  }

  public boolean isBallAtBottom() {
    return bottomTracker.get();
  }

  public boolean isHopperFull() {
    return isBallAtTop() && isBallAtBottom();
  }


}
