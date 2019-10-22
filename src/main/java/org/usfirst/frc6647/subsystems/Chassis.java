/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import java.util.function.DoubleFunction;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.supercomponents.SuperTalon;
import org.usfirst.lib6647.subsystem.supercomponents.SuperVictor;

/**
 * Subsystem for the Chassis.
 */
public class Chassis extends SuperSubsystem implements SuperTalon, SuperVictor {

	private static Chassis m_instance = null;

	/**
	 * Creates static Chassis instance.
	 */
	public static void createInstance() {
		m_instance = new Chassis();
	}

	/**
	 * Gets static Chassis instance. If there is none, creates one.
	 * 
	 * @return static Chassis instance
	 */
	public static Chassis getInstance() {
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem. Initializes Talons and Victors, and sets the
	 * latter to follow its respective Talon.
	 */
	public Chassis() {
		super("chassis");

		initTalons(robotMap, getName());
		initVictors(robotMap, getName());

		victors.get("backLeft").follow(talons.get("frontLeft"));
		victors.get("backRight").follow(talons.get("frontRight"));
	}

	/**
	 * Lambda for joystick tolerance.
	 */
	private DoubleFunction<Double> joyTolerance = x -> Math.abs(x) < 0.15 ? 0
			: x < 0 ? (x + 0.15) / (1 - 0.15) : (x - 0.15) / (1 - 0.15);

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		if (!NavX.getInstance().getPIDController().isEnabled()) {
			double leftStickY = joyTolerance.apply(OI.getInstance().getJoystick("driver1").getLeftAxis()),
					rightStickY = joyTolerance.apply(OI.getInstance().getJoystick("driver1").getRightAxis());
			talons.get("frontLeft").setWithRamp(leftStickY);
			talons.get("frontRight").setWithRamp(rightStickY);
		}
	}

	@Override
	public void initDefaultCommand() {
	}
}