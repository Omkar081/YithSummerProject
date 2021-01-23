/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.hal.SimBoolean;
import edu.wpi.first.hal.SimDevice;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

/**
 * A class created to emulate a TalonSRX/FX in the robot code simulator.
 * 
 * <p>Because this class implements the SpeedController interface, it works just like any Talon or other speed controller.
 * 
 * <p>Features a built in PID controller similar to a TalonSRX/FX, with similar amounts of useless settings you have to configure beforehand.
 */
public class SimulatedTalon implements SpeedController {
    /**
     * Omnipotent being created to keep track of all of the simulated talons. 
     * 
     * <p>Rather than updating every talon manually in its subsystem's periodic method, you can simply put this code into robotPeriodic() in Robot.java:
     * 
     * <pre>
     * <code>
     * for(SimulatedTalon talon : SimulatedTalon.simTalons){
     *    talon.update(.02);
     *}
     * </code>
     * </pre>
     */
    public static HashMap<Integer, SimulatedTalon> simTalons = new HashMap<Integer, SimulatedTalon>();

    SimDevice sim;
    int followerId = -1;

    //information about the state of the motor. Value is the percent voltage applied to the motor.
    double value = 0;
    boolean inverted = false;
    double velocity = 0;
    double position = 0;

    //constants regarding how the motor moves. Specific to the motor/mechanism; we can't change these. Also, I completely made these numbers up.
    double kS = .1;
    double kV = .01;
    double kA = .005;
    //the constant force applied to the mechanism, e.g. the weight of the climber pulling the mechanism down.
    double kG = 0;

    //values to be displayed in the simulation
    SimDouble sim_value;
    SimDouble sim_position;
    SimDouble sim_vel;
    SimBoolean sim_inverted;

    //feedforward. Adds a constant times the setpoint to the output.
    double kF;

    SimDouble sim_kP;
    SimDouble sim_kI;
    SimDouble sim_kD;
    SimDouble sim_kF;
    SimDouble sim_maxI;

    PIDController pid = new PIDController(0, 0, 0);

    boolean hasCalledStupidSlotMethod;
    
    /**
     * <p> creates a simulated motor to be used with the simulation. Is not connected to any actual motors. Do not use this for actual robot code.
     * 
     * @param name the name that will appear in the simulator. Must be unique for each motor.
     * @param id the "CAN ID" of the talon.
     */
    public SimulatedTalon(String name, int id){
        this(name, id, false);
    }

    /**
     * <p> creates a simulated motor to be used with the simulation. Is not connected to any actual motors. Do not use this for actual robot code.
     * 
     * @param name the name that will appear in the simulator. Must be unique for each motor.
     * @param id the "CAN ID" of the talon.
     * @param hasPid if you will be using the built in PID controller for this motor. Adds several values to the simulator, which may be annoying if you won't be using the talon's build in PID.
     */
    public SimulatedTalon(String name, int id, boolean hasPid){
        sim = SimDevice.create(name);
        sim_value = sim.createDouble("Motor Output", true, 0);
        sim_position = sim.createDouble("position", false, 0);
        sim_vel = sim.createDouble("velocity", false, 0);
        sim_inverted = sim.createBoolean("Inverted", true, false);

        if(hasPid){
            sim_kP = sim.createDouble("kP", false, 0);
            sim_kI = sim.createDouble("kI", false, 0);
            sim_kD = sim.createDouble("kD", false, 0);
            sim_kF = sim.createDouble("kF", false, 0);
            sim_maxI = sim.createDouble("max integral", false, 0);
        }

        simTalons.put(id, this);
    }

    /**
     * <b>*jedi hand wave*</b> This is not the method you're looking for. 
     * (it's not implemented, and I'm not even exactly sure what it's supposed to do.)
     */
    @Override
    public void pidWrite(double output) {

    }

    @Override
    public void set(double speed) {
        value = MathUtil.clamp(speed, -1, 1);
        sim_value.set(value);

    }

    @Override
    public double get() {
        return value;
    }

    @Override
    public void setInverted(boolean isInverted) {
        inverted = isInverted;
        sim_inverted.set(isInverted);
    }

    @Override
    public boolean getInverted() {
        return inverted;
    }

    @Override
    public void disable() {
        set(0);
    }

    @Override
    public void stopMotor() {
        set(0);
    }

    /**
     * Updates the SimulatedTalon's velocity and position based on the given timestep. Should be called once per robot loop.
     * @param dt the timestep for which the simulation is calculated.
     */
    public void update(double dt){
        if(simTalons.containsKey(followerId)){
            set(simTalons.get(followerId).get());
        }

        //if the user manually changed the position or velocity, update accordingly.
        position = sim_position.get();
        velocity = sim_vel.get();

        //calculate the acceleration of the motor (based on https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/robot-characterization/introduction.html)
        double acc = (value - kS*Math.signum(velocity) - kV*velocity - kG) / kA;
        velocity += acc * dt;
        position += velocity * dt;

        sim_vel.set(velocity);
        sim_position.set(position);
    }

    /**
	 * Sets the appropriate output on the talon, depending on the mode.
	 * @param controlMode The output mode to apply.
	 * In PercentOutput, the output is between -1.0 and 1.0, with 0.0 as stopped.
	 * In Current mode, it throws an error because you're dumb.
	 * In Velocity mode, output value is in position change / 100ms.
	 * In Position mode, output value is in encoder ticks or an analog value,
	 *   depending on the sensor.
	 * In Follower mode, the output value is the integer device ID of the talon to
	 * duplicate.
     * 
     * @apiNote to use the position or velocity modes, you must call selectProfileSlot first, because...reasons.
	 *
	 * @param value The setpoint value, as described above.
	 *
	 *
	 *	Standard Driving Example:
	 *	_talonLeft.set(ControlMode.PercentOutput, leftJoy);
	 *	_talonRght.set(ControlMode.PercentOutput, rghtJoy);
	 */
    public void set(ControlMode controlMode, double value){
        switch(controlMode){
            case PercentOutput:
                set(value);
                break;
            case Position:
                setPositionPID(value);
                break;
            case Velocity:
                setVelocityPID(value);
                break;
            case Follower:
                if(simTalons.containsKey((int)value)){
                    followerId = (int)value;
                    break;
                }else{
                    throw new IllegalArgumentException("There is no SimulatedTalon with ID "+(int)value+", idiot!");
                }
            default:
                throw new IllegalArgumentException("Sorry, I'm too lazy to implement that control mode. Sue me.");
        }
    }

    void setPositionPID(double targetPos){
        if(!hasCalledStupidSlotMethod){
            throw new IllegalStateException("You can't use PID if you haven't called the selectProfileSlot method, because...reasons!");
        }
        pid.setP(sim_kP.get());
        pid.setI(sim_kI.get());
        pid.setD(sim_kD.get());
        pid.setIntegratorRange(-sim_maxI.get(), sim_maxI.get());
        kF = sim_kF.get();

        set( pid.calculate(position, targetPos) + kF*targetPos );

    }

    void setVelocityPID(double targetVel){
        if(!hasCalledStupidSlotMethod){
            throw new IllegalStateException("You can't use PID if you haven't called the selectProfileSlot method, because...reasons!");
        }
        pid.setP(sim_kP.get());
        pid.setI(sim_kI.get());
        pid.setD(sim_kD.get());
        pid.setIntegratorRange(-sim_maxI.get(), sim_maxI.get());
        kF = sim_kF.get();

        set( pid.calculate(velocity, targetVel) + kF*targetVel );
    }

    /**
     * The only time you should be using this method is if someone told you to. 
     * These constants depend on the mechanism attached to the motor, and are beyond our control.
     * They change how the motor behaves, and we tune the PID constants to deal with that behavior.
     * 
     * <p>in normal circumstances, you never even have to see these numbers (I didn't know they existed until recently).
     * 
     * @param kS is the output needed to overcome the motor’s static friction, or in other words to just barely get it moving; it turns out that this static friction (because it’s, well, static) has the same effect regardless of velocity or acceleration. That is, no matter what speed you’re going or how fast you’re accelerating, some constant portion of the voltage you’ve applied to your motor (depending on the specific mechanism assembly) will be going towards overcoming the static friction in your gears, bearings, etc; this value is your kS.
     * @param kV describes how much output is needed to hold (or “cruise”) at a given constant velocity while overcoming the electromagnetic resistance in the motor and any additional friction that increases with speed (known as viscous drag).
     * @param kA describes the output needed to induce a given acceleration in the motor shaft.
     * @param kG describes the output required to counteract the effects of gravity on the system, such as when raising the climber.
     */
    public void configureMotorConstants(double kS, double kV, double kA, double kG){
        this.kS = kS;
        this.kV = kV;
        this.kA = kA;
        this.kG = kG;
    }

    /**
     * gets the position of the motor. For real talons, it returns the value of the encoder plugged into the talon, which almost always returns the position of the motor, or a multiple of the position.
     * 
     * <p>The "selected" part goes back to the profile slots. Don't worry about it. See selectProfileSlot for more information.
     * 
     * @return the position of the motor.
     */
    public double getSelectedSensorPosition(){
        return position;
    }
    /**
     * 
     * @param value the value to change the encoder position to
     */
    public void setSelectedSensorPosition(double value) {
        position = value;
    }

    /**
     * gets the velocity of the motor. For real talons, it returns the velocity of the encoder plugged into the talon, which almost always returns the velocity of the motor, or a multiple of the velocity.
     * 
     * <p>The "selected" part goes back to the profile slots. Don't worry about it. See selectProfileSlot for more information.
     * 
     * @return the velocity of the motor.
     */
    public double getSelectedSensorVelocity(){
        return velocity;
    }

    /**
     * The current error of the PID controller. Useful for seeing if you are close enough to the target position/velocity.
     * @return The current error of the PID controller.
     */
    public double getClosedLoopError(){
        return pid.getPositionError();
    }

    /**
     * Sets the proportional term of the built in PID controller.
     * 
     * @param slotIdx which profile slot's kP you want to change. If you're not sure, use 0. See selectProfileSlot for more info.
     * @param kP the value to set kP to.
     */
    public void config_kP(int slotIdx, double kP){
        pid.setP(kP);
        sim_kP.set(kP);
    }

    /**
     * Sets the integral term of the built in PID controller.
     * 
     * @param slotIdx which profile slot's kI you want to change. If you're not sure, use 0. See selectProfileSlot for more info.
     * @param kI the value to set kI to.
     */
    public void config_kI(int slotIdx, double kI){
        pid.setP(kI);
        sim_kI.set(kI);
    }

    /**
     * Sets the derivative term of the built in PID controller.
     * 
     * @param slotIdx which profile slot's kD you want to change. If you're not sure, use 0. See selectProfileSlot for more info.
     * @param kD the value to set kD to.
     */
    public void config_kD(int slotIdx, double kD){
        pid.setP(kD);
        sim_kD.set(kD);
    }

    /**
     * Sets the feedforward term of the built in PID controller.
     * 
     * @param slotIdx which profile slot's kF you want to change. If you're not sure, use 0. See selectProfileSlot for more info.
     * @param kF the value to set kF to.
     */
    public void config_kF(int slotIdx, double kF){
        this.kF = kF;
        sim_kF.set(kF);
    }

    /**
     * Sets the maximum amount the integrator can accumulate for the built in PID controller.
     * 
     * @param slotIdx which profile slot's maxI you want to change. If you're not sure, use 0. See selectProfileSlot for more info.
     * @param value the value to set maxI to.
     */
    public void configMaxIntegralAccumulator(int slotIdx, double value){
        pid.setIntegratorRange(-value, value);
        sim_maxI.set(value);
    }

    /**
     * TalonSRX/FXs have multiple "profile slots," in the event you want to use different PID constants for different situations, for example:
     * <ul>
     *  <li>raising the lightweight climber mechanism to the bar vs. lifting the entire robot off the ground.</li>
     *  <li>-getting a motor approximately to the setpoint quickly vs. getting precisely to the setpoint slightly slower.</li>
     * </ul>
     * 
     * <p>This method needs to be called to use the talon's built in PID, but only advanced users will ever call it as anything other than setProfileSlot(0, 0);
     * 
     * @param slotIdx which slot to use. If you're not sure, stick to 0.
     * @param pidIdx whether to use the primary (0) or auxiliary (1) pid loop. I'm still not entirely sure what this means, so stick to 0.
     */
    public void selectProfileSlot(int slotIdx, int pidIdx){
        hasCalledStupidSlotMethod = true;
    }
}