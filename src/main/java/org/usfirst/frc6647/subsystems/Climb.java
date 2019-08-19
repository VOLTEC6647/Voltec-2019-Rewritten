package org.usfirst.frc6647.subsystems;

import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.supercomponents.SuperDoubleSolenoid;

public class Climb extends SuperSubsystem implements SuperDoubleSolenoid {

	private static Climb m_instance = null;

	/**
	 * Creates static Climb instance.
	 */
	public static void createInstance() {
		m_instance = new Climb();
	}

	/**
	 * Gets static Climb instance. If there is none, creates one.
	 * 
	 * @return static Climb instance
	 */
	public static Climb getInstance() {
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the command.
	 */
	public Climb() {
		super("climb");

		initDoubleSolenoids(robotMap, getName());
	}

	@Override
	protected void initDefaultCommand() {
	}
}