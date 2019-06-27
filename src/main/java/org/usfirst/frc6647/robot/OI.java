/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import org.usfirst.frc6647.commands.ChangeVelocity;
import org.usfirst.frc6647.commands.GyroAlign;
import org.usfirst.frc6647.commands.GyroMove;
import org.usfirst.frc6647.commands.MoveBall;
import org.usfirst.frc6647.commands.MoveLiftManual;
import org.usfirst.frc6647.commands.MoveLiftPID;
import org.usfirst.frc6647.commands.MoveLiftPID.Height;
import org.usfirst.frc6647.commands.MoveLiftPID.Target;
import org.usfirst.frc6647.commands.ResetEncoders;
import org.usfirst.frc6647.commands.Slide;
import org.usfirst.frc6647.commands.TiltIntakeManual;
import org.usfirst.frc6647.commands.ToggleHatch;
import org.usfirst.frc6647.commands.ZeroYaw;
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

		joysticks.put("Driver1", new JController(0));

		if (!joysticks.get("Driver1").getName().isEmpty()) {
			System.out.println("[!] Controller 1 found!");

			if (joysticks.get("Driver1").getName().equals("Wireless Controller")) {
				oiButton("Driver1", "Square").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton("Driver1", "Triangle").whenPressed(new ChangeVelocity(0.75, 0.9, false));

				oiButton("Driver1", "L1").whenPressed(new ToggleHatch());
				oiButton("Driver1", "R1").whileHeld(new GyroAlign());

				oiButton("Driver1", "L2").whileHeld(new Slide(Direction.LEFT, 3, 4, false, "Driver1", 0.7));
				oiButton("Driver1", "R2").whileHeld(new Slide(Direction.RIGHT, 3, 4, false, "Driver1", 0.7));

				oiButton("Driver1", "PS4Btn").whenPressed(new ZeroYaw());

				oiButton("Driver1", "dPadUp").whileHeld(new GyroMove());
				/*
				 * oiButton("Driver1", "dPadLeft").whenPressed(new AlignNext(Direction.LEFT));
				 * oiButton("Driver1", "dPadRight").whenPressed(new AlignNext(Direction.RIGHT));
				 */
				oiButton("Driver1", "dPadDown").whileHeld(new GyroMove());
			} else if (joysticks.get("Driver1").getName().equals("Controller (XBOX 360 For Windows)")
					|| joysticks.get("Driver2").getName().equals("Controller (Xbox One For Windows)")) {
				oiButton("Driver1", "Y").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton("Driver1", "X").whenPressed(new ChangeVelocity(0.75, 0.9, false));

				oiButton("Driver1", "LBumper").whenPressed(new ToggleHatch());
				oiButton("Driver1", "RBumper").whileHeld(new GyroAlign());

				oiButton("Driver1", "LTrigger").whileHeld(new Slide(Direction.LEFT, 3, 4, false, "Driver1", 0.7));
				oiButton("Driver1", "RTrigger").whileHeld(new Slide(Direction.RIGHT, 3, 4, false, "Driver1", 0.7));

				oiButton("Driver1", "B").whenPressed(new ZeroYaw());

				oiButton("Driver1", "dPadUp").whileHeld(new GyroMove());
				/*
				 * oiButton("Driver1", "dPadLeft").whenPressed(new AlignNext(Direction.LEFT));
				 * oiButton("Driver1", "dPadRight").whenPressed(new AlignNext(Direction.RIGHT));
				 */
				oiButton("Driver1", "dPadDown").whileHeld(new GyroMove());
			}
		}

		joysticks.put("Driver2", new JController(1));

		if (!joysticks.get("Driver2").getName().isEmpty()) {
			System.out.println("[!] Controller 2 found!");

			if (joysticks.get("Driver2").getName().equals("Wireless Controller")) {
				oiButton("Driver2", "Square").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton("Driver2", "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton("Driver2", "Circle").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));
				oiButton("Driver2", "Triangle").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton("Driver2", "L1").whileHeld(new MoveLiftManual(Direction.UP));
				oiButton("Driver2", "R1").whileHeld(new MoveLiftManual(Direction.DOWN));

				oiButton("Driver2", "L2").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton("Driver2", "R2").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton("Driver2", "RStickBtn").whenPressed(new ToggleHatch());

				oiButton("Driver2", "PS4Btn").whenPressed(new ResetEncoders());
				oiButton("Driver2", "Touchpad").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));

				oiButton("Driver2", "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));
				oiButton("Driver2", "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));
				oiButton("Driver2", "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));

				oiButton("Driver2", "LStickUp").whileHeld(new MoveBall(Direction.OUT, 0.7));
				oiButton("Driver2", "LStickDown").whileHeld(new MoveBall(Direction.IN, 0.7));
			} else if (joysticks.get("Driver2").getName().equals("Controller (XBOX 360 For Windows)")
					|| joysticks.get("Driver2").getName().equals("Controller (Xbox One For Windows)")) {
				oiButton("Driver2", "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton("Driver2", "A").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton("Driver2", "B").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));
				oiButton("Driver2", "Y").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton("Driver2", "LBumper").whileHeld(new MoveLiftManual(Direction.UP));
				oiButton("Driver2", "RBumper").whileHeld(new MoveLiftManual(Direction.DOWN));

				oiButton("Driver2", "LTrigger").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton("Driver2", "RTrigger").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton("Driver2", "RStickBtn").whenPressed(new ToggleHatch());

				oiButton("Driver2", "Back").whenPressed(new ResetEncoders());
				oiButton("Driver2", "Start").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));

				oiButton("Driver2", "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));
				oiButton("Driver2", "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));
				oiButton("Driver2", "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));

				oiButton("Driver2", "LStickUp").whileHeld(new MoveBall(Direction.OUT, 0.7));
				oiButton("Driver2", "LStickDown").whileHeld(new MoveBall(Direction.IN, 0.7));
			}
		}
	}
}