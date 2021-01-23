/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SimulatedGyro;
import frc.robot.SimulatedTalon;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
 
  SimulatedTalon leftFront = new SimulatedTalon("leftFront", DriveConstants.leftFrontID);
  SimulatedTalon leftBack = new SimulatedTalon("leftBack", DriveConstants.leftBackID);
  SimulatedTalon rightFront = new SimulatedTalon("rightFront", DriveConstants.rightFrontID);
  SimulatedTalon rightBack = new SimulatedTalon("rightBack", DriveConstants.rightBackID);

  SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFront, leftBack);
  SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFront, rightBack);

  DifferentialDrive diffDrive = new DifferentialDrive(leftGroup, rightGroup);  //rate at which it turns is the difference between the two groups of wheels

  SimulatedGyro gyro = new SimulatedGyro(leftFront, rightFront, 3);

  
  
  public DriveSubsystem() {
    

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    gyro.updateAngle(.02);

    System.out.println(gyro.getAngle());
  }

  public void drive(double forward, double turn) {
    diffDrive.arcadeDrive(forward, turn); //calculates the motor powers to config speeds of forward and turn

    /**
     * Square Inputs
     * Humans are not good at making small changes (e.g moving joystick 1 %)
     * So using squareInputs if we were to move the joystick by input of 5, square inputs returns .25;
     * Essentially, gives a bigger space for human control while respecting precision
     * 
     */

  }



  public double getDistance() {
    return DriveConstants.feetPerTick*(leftFront.getSelectedSensorPosition() - rightFront.getSelectedSensorPosition())/2.0;
    /* motors face opposite directions so (-) instead of (+)
      bc postive direction for one motor is "negative direction" for the other */
  }

  public double getAngle() {
    return gyro.getAngle();
  }

  public void resetGyro() {
    gyro.resetAngle();
  }

  public void resetEncoders() {
    leftFront.setSelectedSensorPosition(0);
    rightFront.setSelectedSensorPosition(0);

  }


  public void stopDrive() { 
    leftFront.set(0);
    leftBack.set(0);
    rightFront.set(0);
    leftBack.set(0);

  }

}
