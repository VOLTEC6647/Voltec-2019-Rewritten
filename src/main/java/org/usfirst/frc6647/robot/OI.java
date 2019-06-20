/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import org.usfirst.frc6647.commands.*;
import org.usfirst.frc6647.commands.MoveLiftPID.Height;
import org.usfirst.frc6647.commands.MoveLiftPID.Target;
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

		oiButton(0, 4).whenPressed(new ChangeVelocity(0.75, 0.9, false));
		oiButton(0, 3).whileHeld(new ControlH(Direction.FORWARD));
		oiButton(0, 2).whileHeld(new ControlH(Direction.BACKWARD));
		oiButton(0, 1).whenPressed(new ChangeVelocity(0.6, 0.6, true));

		oiButton(0, 5).whileHeld(new MoveBall(Direction.IN, 0.7));
		oiButton(0, 6).whileHeld(new MoveBall(Direction.OUT, 0.7));

		oiButton(0, 7).whileHeld(new Slide(Direction.LEFT));
		oiButton(0, 8).whileHeld(new Slide(Direction.RIGHT));

		oiButton(0, 9).whenPressed(new ZeroYaw());
		oiButton(0, 10).toggleWhenPressed(new GyroAlign());

		oiButton(0, "dPad", 0, 270).whileHeld(new AlignNext(Direction.LEFT));
		oiButton(0, "dPad", 0, 90).whileHeld(new AlignNext(Direction.RIGHT));

		joysticks.add(new JController(1));

		oiButton(1, 4).whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
		oiButton(1, 3).whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
		oiButton(1, 2).whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
		oiButton(1, 1).whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

		oiButton(1, 5).whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));
		oiButton(1, 6).whileHeld(new MoveLiftManual(Direction.DOWN));

		oiButton(1, 7).whileHeld(new TiltIntakeManual(Direction.UP));
		oiButton(1, 8).whileHeld(new TiltIntakeManual(Direction.DOWN));

		/* oiButton(1, 9).whileHeld(new ControlH(Direction.BACKWARD));
		oiButton(1, 10).whileHeld(new ControlH(Direction.FORWARD)); */
		oiButton(1, 9).whenPressed(new PushHatch(1.0));

		oiButton(1, 13).whenPressed(new ResetEncoders());
		oiButton(1, 14).whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));
	}
}