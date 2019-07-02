package org.usfirst.frc6647.subsystems;

import org.usfirst.frc6647.commands.GeneratePIDData;
import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;
import org.usfirst.lib6647.subsystem.supercomponents.SuperEncoder;
import org.usfirst.lib6647.subsystem.supercomponents.SuperTalon;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the Lift.
 */
public class Lift extends PIDSuperSubsystem implements SuperEncoder, SuperTalon {

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

		initEncoders(robotMap, getName());
		initTalons(robotMap, getName());

		finishedJSONInit();

		talons.get("liftFollower").follow(talons.get("liftMain"));

		SmartDashboard.putData(getName() + "Generate", new GeneratePIDData(this));
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
		talons.get("liftMain").set(output);
		
		pidOutput = talons.get("liftMain").getMotorOutputVoltage();
	}
}