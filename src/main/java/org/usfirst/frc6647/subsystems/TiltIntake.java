package org.usfirst.frc6647.subsystems;

import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.supercomponents.SuperTalon;

import edu.wpi.first.wpilibj.Filesystem;

public class TiltIntake extends SuperSubsystem implements SuperTalon {

	private static TiltIntake m_instance = null;

	/**
	 * Creates static Intake instance.
	 */
	public static void createInstance() {
		m_instance = new TiltIntake();
	}

	/**
	 * Gets static TiltIntake instance. If there is none, creates one.
	 * 
	 * @return static TiltIntake instance
	 */
	public static TiltIntake getInstance() {
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public TiltIntake() {
		super("tiltIntake", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initTalons(robotMap, getName());

		finishedJSONInit();
	}

	@Override
	protected void initDefaultCommand() {
	}
}