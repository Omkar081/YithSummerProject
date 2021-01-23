/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;

/**
 * Simulated NetworkTable entry to control and test vision code in the simulation. 
 * 
 * <p>Currently only has a single double value, but the value can be returned as an array of the form {value, 0, 0}.
 */
public class SimulatedNetworkTableEntry {

    SimDevice simDevice;

    SimDouble simValue;

    /**
     * creates a new simulated NetworkTable entry with the given name. From the simulation, you will be able to change the double value of the entry.
     * 
     * @param name the name of the entry that will appear in the simulation window.
     */
    public SimulatedNetworkTableEntry(String name){
        simDevice = SimDevice.create(name);
        simValue = simDevice.createDouble("value", false, 0);
    }

    /**
     * Gets the value of the entry as a double, or the default value if it doesn't exist.
     * @param defaultValue the value to return if the entry does not exist, or is not a double.
     * @return the value of the entry, or the default value if the entry either doesn't exist or is not a double.
     */
    public double getDouble(double defaultValue){
        return simValue.get();
    }

    /**
     * returns the value of the entry as a double array of the form {value, 0, 0}, or the default value if it doesn't exist.
     * @param defaultValue the value to return if the entry does not exist, or is not a double array.
     * @return the value of the entry, or the default value if the entry either doesn't exist or is not a double array.
     */
    public double[] getDoubleArray(double[] defaultValue){
        return new double[]{simValue.get(), 0, 0};
    }
}
