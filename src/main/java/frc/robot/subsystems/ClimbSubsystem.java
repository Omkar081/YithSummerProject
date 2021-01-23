/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.TreeMap;
import java.util.Map.Entry;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SimulatedTalon;
import frc.robot.Constants.ClimbConstants;

public class ClimbSubsystem extends SubsystemBase {
  SimulatedTalon climbMotor1 = new SimulatedTalon("climbMotor1", ClimbConstants.climbMotor1ID);
  SimulatedTalon climbMotor2 = new SimulatedTalon("climbMotor2", ClimbConstants.climbMotor2ID);
  DigitalInput climbTracker = new DigitalInput(ClimbConstants.climbTrackerPort);

  SpeedControllerGroup climber = new SpeedControllerGroup(climbMotor1, climbMotor2);
  
  /**
   * Creates a new ClimbSubsystem.
   */
  public ClimbSubsystem() {
    climbMotor1.configureMotorConstants(.075, .01, .001, .3); // kS, kV, kA, kG
    climbMotor2.configureMotorConstants(.075, .01, .001, .3); // kS, kV, kA, kG
  }

 

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
 

  public void climb(double speed) {
    climber.set(speed);
  }

  public double getCurrentPosition() {
    return climbMotor1.getSelectedSensorPosition();
  }

  public void climbUp() {
    climber.set(0.5);
  }

  public void climbDown() {
    climber.set(-0.5);
  }

  public void stopClimb() {
    climber.set(0);
  }
}
