package org.usfirst.frc6647.subsystems;

import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;
import org.usfirst.lib6647.subsystem.supercomponents.SuperDigitalInput;
import org.usfirst.lib6647.subsystem.supercomponents.SuperEncoder;
import org.usfirst.lib6647.subsystem.supercomponents.SuperVictor;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the Lift.
 */
public class Lift extends PIDSuperSubsystem implements SuperVictor, SuperEncoder, SuperDigitalInput {

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

		initVictors(robotMap, getName());
		initEncoders(robotMap, getName());
		initDigitalInputs(robotMap, getName());

		finishedJSONInit();

		victors.get("liftFollower").follow(victors.get("liftMain"));
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
	 * Sets liftMain Victor to the PID's calculated speed.
	 */
	@Override
	protected void usePIDOutput(double output) {
		victors.get("liftMain").set(output);
	}
}