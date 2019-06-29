package org.usfirst.frc6647.subsystems;

import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.supercomponents.SuperCompressor;
import org.usfirst.lib6647.subsystem.supercomponents.SuperSolenoid;
import org.usfirst.lib6647.subsystem.supercomponents.SuperTalon;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Subsystem for the Intake.
 */
public class Intake extends SuperSubsystem implements SuperTalon, SuperSolenoid, SuperCompressor {

	private static Intake m_instance = null;

	/**
	 * Creates static Intake instance.
	 */
	public static void createInstance() {
		m_instance = new Intake();
	}

	/**
	 * Gets static Intake instance. If there is none, creates one.
	 * 
	 * @return static Intake instance
	 */
	public static Intake getInstance() {
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public Intake() {
		super("intake", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initTalons(robotMap, getName());
		initCompressors(robotMap, getName());
		initSolenoids(robotMap, getName());

		finishedJSONInit();
	}

	@Override
	protected void initDefaultCommand() {
	}

	public void setH(boolean value) {
		solenoids.get("pushHatchForward").set(value);
		solenoids.get("pushHatchBackward").set(!value);
	}

	public void toggleH() {
		setH(!solenoids.get("pushHatchForward").get());
	}

	public void stopH() {
		solenoids.get("pushHatchForward").set(false);
		solenoids.get("pushHatchBackward").set(false);
	}
}