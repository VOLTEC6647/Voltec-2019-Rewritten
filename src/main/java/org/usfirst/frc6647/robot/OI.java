/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import java.util.ArrayList;

import org.usfirst.frc6647.commands.AlignNext;
import org.usfirst.frc6647.commands.GyroAlign;
import org.usfirst.frc6647.commands.Slide;
import org.usfirst.lib6647.oi.ButtonHelper;
import org.usfirst.lib6647.oi.Controller;
import org.usfirst.lib6647.util.Direction;

/**
 * Class for registering driver input.
 */
public class OI extends ButtonHelper {
	public ArrayList<Controller> joysticks;

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
	 * Add joysticks and button inputs here. See lambda declarations in
	 * org.usfirst.lib6647.oi.ButtonHelper for examples.
	 */
	public OI() {
		joysticks.add(new Controller(0));

		oiButton.get(1, 7).whileHeld(new Slide(Direction.LEFT));
		oiButton.get(1, 8).whileHeld(new Slide(Direction.RIGHT));
		oiButton.get(1, 14).whileHeld(new GyroAlign());

		oiPOV.get(1, 1, 270).whileHeld(new AlignNext(Direction.LEFT));
		oiPOV.get(1, 1, 90).whileHeld(new AlignNext(Direction.RIGHT));
	}
}