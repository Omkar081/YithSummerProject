/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/





package frc.robot;


/**
 * DISCLAIMER: This is NOT applicable for actual robot code. It is only meant for seeing if stuff works for teams without a robot
 * This class is meant to simulate the behavior of a gyro and is to be used in conjuntion with the simulation
 * 
 * It contains methods designed to extract data on robot direction, angle, and angular speed
 * 
 * It contains numerical constants specific to the gyro's behavior and also to the robot
 * 
 * Thank you for wasting your time to read this!
 */

 

public class SimulatedGyro { 

    //The two talons necessary for the gyro to work. 
    private SimulatedTalon leftFront;  
    private SimulatedTalon rightFront;

    private double width; //The width of the robot in feet: varies from robot to robot

    private double aNgLe = 0; //The angle(radians) at which the robot is positioned. It is absolute

    private double feetPerTick = (double)1/9; //A derived constant needed to convert Gyro "ticks" to "feet". The value can change depending on the max speed of the gyro
    /*Please note this has been derived from the famous proof that the sum of all 
        natural numbers is equal to -1/12 */

    
    public SimulatedGyro(SimulatedTalon leftFront, SimulatedTalon rightFront, double width) {
        this.leftFront = leftFront;
        this.rightFront = rightFront;
        this.width = width;

        /**The criteria required to use the Gyro. 
         * The "width" is what we would use to apporximate the angular velocity as seen if you scroll down more
         * The gyros used in actual robot code are big brain and are already imbued with this info */

         //I don't think that was helpful enough but I tried.
        
        
    }
    public double getAngularVelocity() { //method to find the angular velocity(w) of the robot based on the current velocities of the talons
        return feetPerTick *(leftFront.getSelectedSensorVelocity() + rightFront.getSelectedSensorVelocity()) / width;
        /**
         * <p>
         * Based on this niche formula: w = (Velocity of Left Motor - Velocity of Right Motor)/Robot Width
         * Units of the formula are (ticks/sec - ticks/sec)/ft = ticks/sec/ft(Crazy, I know!)
         * Hence we used "feetPerTick" (a derived value, specific to gyro) to convert that madness into radness: ft/sec!
         * </p>
         * 
         * 
         * 
         * <p>
         * The above assumes both motors are configured to have the same direction as positive so (-) would effectively get the difference in talon speeds which creates the rotation
         * Our code is plus because for one talon, up is positive and for the other talon, down is positive 
         * So (+) correctly illustrates that if both of our motors are positve(up = + for one and down = positive for another) we should see our outputted angle as positive
         * </p>
         */
    }

    public double getAngle() { //Method that returns the direction of the robot through it's current angle. 
        return Math.toDegrees(aNgLe); //Conversion from radians to degrees for us narrow-minded folks.

    }

    /**
     * Updates the current angle of the robot through its angular velocity
     * @param dt the change in time; the rate at which the agnle will be updated in seconds
     * Based on the famous kinematics equation: Xf = Xo +Vot, but translated to its rotational counterpart. It's hard to type that version out
     * Call this in periodic() in DriveSubsystem so that it continuously updates
     * With actual robot code, we wouldn't need to do this since Robot.Java already takes care of that for us
     */
    public void updateAngle(double dt) { 
        aNgLe += getAngularVelocity() * dt;
    }

    public void resetAngle() { //Resets the reading angle of the gyro to zero. Good for testing
        aNgLe = 0;
    }
}
