/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SimulatedTalon;
import frc.robot.Constants.HopperConstants;

public class HopperSubsystem extends SubsystemBase {
  /**
   * Creates a new HopperSubsystem.
   */

    //Hopper holds the balls from the intake and transports the balls to the shooter
   SimulatedTalon hopperMotor = new SimulatedTalon("hopperMotor", HopperConstants.hopperMotorID);
   SimulatedTalon hopperMotor2 = new SimulatedTalon("hopperMotor2", HopperConstants.hopperMotor2ID);

   SpeedControllerGroup hopper = new SpeedControllerGroup(hopperMotor, hopperMotor2);

  public HopperSubsystem() {
      hopperMotor.setInverted(true);
  }

  public void moveToIntake() { ///moves towards Intake for negative
    hopper.set(0.25);
  }

  public void moveToShooter() {
    hopper.set(-0.25);
  }

  public void move(double speed) {
    hopper.set(speed); 
  }

  public void stop() { //whenever we don't want the hopper to move
    hopper.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
