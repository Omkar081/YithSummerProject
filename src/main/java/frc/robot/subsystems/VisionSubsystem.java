/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  /**
   * Creates a new VisionSubsystem.
   */

   NetworkTableInstance table = NetworkTableInstance.getDefault();
   NetworkTable visionTable = table.getTable("chameleon-vision").getSubTable("Microsoft LifeCam HD-3000");
   NetworkTableEntry poseEntry = visionTable.getEntry("targetPose");
   NetworkTableEntry yawEntry = visionTable.getEntry("targetYaw");
   

  public VisionSubsystem() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


  public double getTargetDistance(){
    double[] defaultPose = {0, 0, 0};
    double[] pose = poseEntry.getDoubleArray(defaultPose);
    return Math.hypot(pose[0], pose[1]);
  }

  public double getTargetX(){
    return yawEntry.getDouble(0) / 30.0;
  }

  public double getTargetAngle(){
    return yawEntry.getDouble(0);
  }
  public double[] getCoordinates(){
    double[] pose = poseEntry.getDoubleArray(new double[3]); 
    return pose;
  }

  
}
