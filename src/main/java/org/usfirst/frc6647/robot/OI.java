/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import org.usfirst.frc6647.commands.AlignNext;
import org.usfirst.frc6647.commands.GyroAlign;
import org.usfirst.frc6647.commands.Slide;
import org.usfirst.lib6647.oi.ButtonHelper;
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.util.Direction;

/**
 * Class for registering driver input.
 */
public class OI extends ButtonHelper {

	private static OI m_instance = null;

	/**
	 * Creates static OI instance.
	 */
	public static void createInstance() {
		m_instance = new OI();
	}

	/**
	 * Gets static OI instance. If there is none, creates one.
	 * 
	 * @return static OI instance
	 */
	public static OI getInstance() {
		if (m_instance == null) {
			createInstance();
		}
		return m_instance;
	}

	/**
	 * Constructor for the class.
	 * 
	 * Add joysticks and button inputs here.
	 */
	public OI() {
		joysticks.add(new JController(0));

		oiButton(0, "LBumper").whileHeld(new Slide(Direction.LEFT));
		oiButton(0, "RBumper").whileHeld(new Slide(Direction.RIGHT));
		oiButton(0, 14).whileHeld(new GyroAlign());

		oiButton(0, "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
		oiButton(0, "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
	}
}