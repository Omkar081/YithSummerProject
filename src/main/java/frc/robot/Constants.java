/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class DriveConstants {
        public static final int leftFrontID = 0;
        public static final int leftBackID = 1;
        public static final int rightFrontID = 2;
        public static final int rightBackID = 3;
        public static final double feetPerTick = 1.0/9;
    }

    public static final class HopperConstants {
        public static final int hopperMotorID = 6;
        public static final int hopperMotor2ID = 7;
    }

    public static final class IntakeConstants {
        public static final int lowerIntakeMotorID = 8;
        public static final int upperIntakeMotorID = 9;
    }

    public static final class TrackerConstants {
        public static final int topTrackerPort = 1;
        public static final int bottomTrackerPort = 2;
        public static final int intakeTrackerPort = 3;
        
    }

    public static final class ShooterConstants {
        public static final int shooterMasterID = 11;
        public static final int shooterFollowerID = 12;

        public static final double tolerance = 600;
    }

    public static final class ClimbConstants {
        public static final int climbMotor1ID = 13;
        public static final int climbMotor2ID = 14;
        public static final int climbTrackerPort = 4;


        
    }



    public static final class GyroTurnPID {
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
    }

    
    public static final class EncDrivePID {
        public static final double kP = .3;
        public static final double kI = 0;
        public static final double kD = 0;
    }

    public static final class VisionDrivePID {
        public static final double kP = 0;
        public static final double kI = 0;
        public static final double kD = 0;
    }

    public static final class ShooterPID {
        public static final double kP = .1;
        public static final double kI = .05;
        public static final double kD = .0;
        public static final double kF = .0114;
        public static final double maxIntegral = .1;
    }



}
