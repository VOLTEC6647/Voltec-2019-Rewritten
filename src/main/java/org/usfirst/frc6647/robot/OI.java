/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.usfirst.frc6647.commands.AlignNext;
import org.usfirst.frc6647.commands.GyroAlign;
// import org.usfirst.frc6647.commands.MoveH;
import org.usfirst.frc6647.commands.Slide;
import org.usfirst.lib6647.oi.ButtonHelper;
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.util.Direction;

import edu.wpi.first.wpilibj.Filesystem;

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
		super(Filesystem.getDeployDirectory() + "/Profiles.json");

		joysticks.add(new JController(0));

		try {
			switch (joysticks.get(0).getName()) {
			case "Generic   USB  Joystick":
				oiButton(0, "LTrigger").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "RTrigger").whileHeld(new Slide(Direction.RIGHT));
				oiButton(0, "RStickBtn").toggleWhenPressed(new GyroAlign());
				break;
			case "Wireless Controller":
				oiButton(0, "L2").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "R2").whileHeld(new Slide(Direction.RIGHT));
				oiButton(0, "PS4Btn").toggleWhenPressed(new GyroAlign());
				break;
			}
			oiButton(0, "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
			oiButton(0, "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
		} catch (NullPointerException | IOException | ParseException e) {
			System.out.print(e.getMessage());
		}
	}
}