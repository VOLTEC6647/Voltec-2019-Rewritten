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
import org.usfirst.lib6647.oi.Controller;
import org.usfirst.lib6647.oi.Controller.OIAngleButton;
// import org.usfirst.lib6647.oi.Controller.OIAxisButton;
import org.usfirst.lib6647.oi.Controller.OIButton;
import org.usfirst.lib6647.util.Direction;

/**
 * Class for registering input.
 */
public class OI {
	public ArrayList<Controller> joysticks;

	private static OI m_instance = null;

	public static void createInstance() {
		m_instance = new OI();
	}

	public static OI getInstance() {
		if (m_instance == null) {
			createInstance();
		}
		return m_instance;
	}

	public OI() {
		joysticks.add(new Controller(0));

		OIButton oiButton = (joystick, button) -> joysticks.get(joystick).buttons.get("Button" + button);
		/* OIAxisButton oiAxisButton = (joystick, axis) -> joysticks.get(joystick).buttons.get("Axis" + axis);
		OIAxisButton oiPOVButton = (joystick, pov) -> joysticks.get(joystick).buttons.get("POV" + pov);
		OIAngleButton oiAxis = (joystick, axis, angle) -> joysticks.get(joystick).buttons
				.get("Axis" + axis + "_" + angle); */
		OIAngleButton oiPOV = (joystick, pov, angle) -> joysticks.get(joystick).buttons
				.get("POV" + pov + "_" + angle);

		oiButton.get(1, 7).whileHeld(new Slide(Direction.LEFT));
		oiButton.get(1, 8).whileHeld(new Slide(Direction.RIGHT));
		oiButton.get(1, 14).whileHeld(new GyroAlign());

		oiPOV.get(1, 1, 270).whileHeld(new AlignNext(Direction.LEFT));
		oiPOV.get(1, 1, 90).whileHeld(new AlignNext(Direction.RIGHT));
	}
}