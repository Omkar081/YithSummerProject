/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import java.util.TreeMap;
import java.util.Map.Entry;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SimulatedTalon;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.ShooterPID;


public class ShooterSubsystem extends SubsystemBase {
  /**
   * Creates a new ShooterSubsystem.
   */
  
  SimulatedTalon shooterMaster = new SimulatedTalon("shooterMaster", ShooterConstants.shooterMasterID, true);
  SimulatedTalon shooterFollower = new SimulatedTalon("shooterFollower", ShooterConstants.shooterFollowerID);

  double targetSpeed = 0;

  TreeMap <Double, Double> shooterSpeeds = new TreeMap<>(); //distance(meters), speed(STUsPerDecisecond)
  public ShooterSubsystem() {
    shooterFollower.setInverted(true);
    shooterFollower.set(ControlMode.Follower, 11);

    shooterMaster.selectProfileSlot(0, 0);

    populateSpeedsTable();

    shooterMaster.config_kP(0, ShooterPID.kP);
    shooterMaster.config_kI(0, ShooterPID.kI);
    shooterMaster.config_kD(0, ShooterPID.kD);
    shooterMaster.config_kF(0, ShooterPID.kF);
    shooterMaster.configMaxIntegralAccumulator(0, ShooterPID.maxIntegral);

  }

  public void populateSpeedsTable(){
    shooterSpeeds.put(3.69, -30d);
    shooterSpeeds.put(4.17, -40d);
    shooterSpeeds.put(4.98, -55d);
    shooterSpeeds.put(5.59, -69d);
    shooterSpeeds.put(6.00, -89d);

    

  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void shoot(){
    shooterMaster.set(0.25);
  }
  public void stopShooter(){
    shooterMaster.set(0);
  }

  public void move(double speed){
    shooterMaster.set(speed);
  }

  public void movePID(double STUsPerDecisecond){
    targetSpeed = STUsPerDecisecond;
    shooterMaster.set(ControlMode.Velocity, STUsPerDecisecond);
  }

  public double getTargetSpeed(double distance) {
        //get the shooter speeds for the two distances closest to the one provided.
        Entry<Double, Double> p1 = shooterSpeeds.floorEntry(distance);
        Entry<Double, Double> p2 = shooterSpeeds.ceilingEntry(distance);
        if(p1 == null){
          return p2.getValue();
        }else if(p2 == null){
          p2 = shooterSpeeds.lowerEntry(p1.getKey());
        }else if (p1.equals(p2)) {
          return p1.getValue();
        }  

    
        //linearly interpolate the two points to get an approximate speed for the shooter.
        return (p2.getValue() - p1.getValue())/(p2.getKey() - p1.getKey())
                    * (distance - p1.getKey()) + p1.getValue();
        //(a,b), (c,d)
        //y = (d-b)/(c-a) * (x-a) + b
    
  }

  public boolean isAtSpeed() {
    return Math.abs(shooterMaster.getClosedLoopError()) < ShooterConstants.tolerance;
  }
}
