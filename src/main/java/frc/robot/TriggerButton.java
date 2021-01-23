/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Add your docs here.
 */
public class TriggerButton extends Button {

  XboxController xboxController;
  Hand hand;

  public TriggerButton(XboxController xboxController, Hand hand) {
    this.xboxController = xboxController;
    this.hand = hand;
  }

  @Override
  public boolean get() {
    return xboxController.getTriggerAxis(hand) > 0.5;
  }
}
