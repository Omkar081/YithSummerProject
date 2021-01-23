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
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
  SimulatedTalon lowerIntakeMotor = new SimulatedTalon("lowerIntakeMotor", IntakeConstants.lowerIntakeMotorID);
  SimulatedTalon upperIntakeMotor = new SimulatedTalon("upperIntakeMotor", IntakeConstants.upperIntakeMotorID);

  SpeedControllerGroup intakeMotors =  new SpeedControllerGroup(lowerIntakeMotor, upperIntakeMotor);


  public IntakeSubsystem() {
      //no inversion bc intake has 2 wheels where it's one motor for each of them
  }

  public void intake() {
    intakeMotors.set(.5);
  }

  public void stopIntake() {
    intakeMotors.set(0);
  }

  public void setSpeed(double speed) {
    intakeMotors.set(speed);
  }



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
