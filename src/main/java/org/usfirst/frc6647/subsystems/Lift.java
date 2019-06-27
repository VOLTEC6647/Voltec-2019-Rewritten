package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;
import org.usfirst.lib6647.subsystem.components.SuperDigitalInput;
import org.usfirst.lib6647.subsystem.components.SuperEncoder;
import org.usfirst.lib6647.subsystem.components.SuperTalon;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the Lift.
 */
public class Lift extends PIDSuperSubsystem implements SuperTalon, SuperEncoder, SuperDigitalInput {

	private static Lift m_instance = null;

	/**
	 * Creates static Lift instance.
	 */
	public static void createInstance() {
		m_instance = new Lift();
	}

	/**
	 * Gets static Lift instance. If there is none, creates one.
	 * 
	 * @return static Lift instance
	 */
	public static Lift getInstance() {
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public Lift() {
		super("lift", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initTalons(robotMap, getName());
		initEncoders(robotMap, getName());
		initDigitalInputs(robotMap, getName());

		finishedJSONInit();

		talons.get("liftFollower").follow(talons.get("liftMain"));
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		SmartDashboard.putNumber("Lift Encoder Value", encoders.get("liftEncoder").get());
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Gets input for PID.
	 * 
	 * @return pidInput
	 */
	@Override
	protected double returnPIDInput() {
		return encoders.get("liftEncoder").get();
	}

	/**
	 * Sets liftMain Talon to the PID's calculated speed.
	 */
	@Override
	protected void usePIDOutput(double output) {
		setLift(output);
	}

	/**
	 * Sets liftMain Talon to a specific speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void setLift(double speed) {
		talons.get("liftMain").set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Stops the liftMain Victor dead in its tracks.
	 */
	public void stopLift() {
		setLift(0.0);
	}

	/**
	 * Gets Lift Encoder value.
	 * 
	 * @return liftEncoder
	 */
	public int getEncoderValue() {
		return encoders.get("liftEncoder").get();
	}

	/**
	 * Resets Lift Encoder.
	 */
	public void resetEncoder() {
		encoders.get("liftEncoder").reset();
	}
}