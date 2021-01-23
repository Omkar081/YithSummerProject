/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.AutoShoot;
import frc.robot.commands.Drive;
import frc.robot.commands.DriveDistance;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.FeedBalls;
import frc.robot.commands.MoveToIntake;
import frc.robot.commands.MoveToShooter;
import frc.robot.commands.SpinIntake;
import frc.robot.commands.SpinUp;
import frc.robot.commands.Succ;
import frc.robot.subsystems.BallTrackingSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.HopperSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  Joystick joystick = new Joystick(0);
  XboxController xboxController = new XboxController(0);

  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  public final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final HopperSubsystem m_hopperSubsystem = new HopperSubsystem();
  private final BallTrackingSubsystem m_ballTrackingSubsystem = new BallTrackingSubsystem();
  private final IntakeSubsystem m_intakeSubsystem = new IntakeSubsystem();
  private final ShooterSubsystem m_shooterSubsystem = new ShooterSubsystem();
  private final VisionSubsystem m_visionSubsystem = new VisionSubsystem();
  
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final Drive m_drive = new Drive(m_driveSubsystem, joystick::getY, joystick::getX);  


  TriggerButton intakeButton = new TriggerButton(xboxController, Hand.kLeft);
  TriggerButton shootButton = new TriggerButton(xboxController, Hand.kRight);
  JoystickButton spinUpButton = new JoystickButton(xboxController, 4);

  //Manual Buttons

  POVButton moveToIntakeButton = new POVButton(xboxController, 270);
  POVButton moveToShooterButton = new POVButton(xboxController, 0);
  POVButton manualIntakeButton = new POVButton(xboxController, 180);


  

 




  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_driveSubsystem.setDefaultCommand(m_drive);
   // m_shooterSubsystem.setDefaultCommand(new RunCommand(() -> m_shooterSubsystem.movePID(70*xboxController.getY()), m_shooterSubsystem));
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    intakeButton.whileHeld(new ConditionalCommand(
      new WaitCommand(0), 
      new Succ(m_intakeSubsystem, m_hopperSubsystem, m_ballTrackingSubsystem), 
      m_ballTrackingSubsystem :: isHopperFull   
    ));

    shootButton.whileHeld(new FeedBalls(m_hopperSubsystem, m_shooterSubsystem, m_ballTrackingSubsystem));
    spinUpButton.toggleWhenActive(new SpinUp(m_shooterSubsystem)); 
      /* Shooter takes time to spin up. If we were to have it be whenPressed -> 
      if button psi slips, then we lose the spinUp starting over and wasting time.
      ToggleWhenActive allows for flexibility and calling it when we want without the condition of holding the button. 
      */

    moveToIntakeButton.whileHeld(new MoveToIntake(m_hopperSubsystem));
    moveToShooterButton.whileHeld(new MoveToShooter(m_hopperSubsystem));
    manualIntakeButton.whileHeld(new SpinIntake(m_intakeSubsystem));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new AutoShoot(m_shooterSubsystem, m_visionSubsystem);
  }
}
