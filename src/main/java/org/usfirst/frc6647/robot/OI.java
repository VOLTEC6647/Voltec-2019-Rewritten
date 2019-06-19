/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import java.io.IOException;

import org.json.simple.parser.ParseException;
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
		joysticks.add(new JController(1));

		try {
			switch (joysticks.get(0).getName()) {
			case "Generic   USB  Joystick":
				oiButton(0, "4").whenPressed(new ChangeVelocity(0.75, 0.9, false));
				oiButton(0, "3").toggleWhenPressed(new ControlH(Direction.FORWARD));
				oiButton(0, "2").whenPressed(new PushHatch(1.0));
				oiButton(0, "1").whenPressed(new ChangeVelocity(0.6, 0.6, true));

				oiButton(0, "LTrigger").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton(0, "RTrigger").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton(0, "LBumper").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "RBumper").whileHeld(new Slide(Direction.RIGHT));

				oiButton(0, "LStickBtn").whenPressed(new ZeroYaw());
				oiButton(0, "RStickBtn").toggleWhenPressed(new GyroAlign());
				break;
			case "Controller (Gamepad F310)":
				oiButton(0, "Y").whenPressed(new ChangeVelocity(0.75, 0.9, false));
				oiButton(0, "X").toggleWhenPressed(new ControlH(Direction.FORWARD));
				oiButton(0, "B").whenPressed(new PushHatch(1.0));
				oiButton(0, "A").whenPressed(new ChangeVelocity(0.6, 0.6, true));

				oiButton(0, "LTrigger").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton(0, "RTrigger").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton(0, "LBumper").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "RBumper").whileHeld(new Slide(Direction.RIGHT));

				oiButton(0, "LStickBtn").whenPressed(new ZeroYaw());
				oiButton(0, "RStickBtn").toggleWhenPressed(new GyroAlign());
				break;
			case "Wireless Controller":
				oiButton(0, "Square").whenPressed(new ChangeVelocity(0.75, 0.9, false));
				oiButton(0, "X").toggleWhenPressed(new ControlH(Direction.FORWARD));
				oiButton(0, "Circle").whenPressed(new PushHatch(1.0));
				oiButton(0, "Triangle").whenPressed(new ChangeVelocity(0.6, 0.6, true));

				oiButton(0, "L1").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton(0, "R1").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton(0, "L2").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "R2").whileHeld(new Slide(Direction.RIGHT));

				oiButton(0, "PS4Btn").whenPressed(new ZeroYaw());
				oiButton(0, "Touchpad").toggleWhenPressed(new GyroAlign());
				break;
			}
			oiButton(0, "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
			oiButton(0, "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));

			switch (joysticks.get(1).getName()) {
			case "Generic   USB  Joystick":
				oiButton(1, "4").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton(1, "3").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton(1, "2").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton(1, "1").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton(1, "LTrigger").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));
				oiButton(1, "RTrigger").whileHeld(new MoveLiftManual(Direction.DOWN));

				oiButton(1, "LBumper").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton(1, "RBumper").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton(1, "Select").whileHeld(new ControlH(Direction.BACKWARD));
				oiButton(1, "Start").whileHeld(new ControlH(Direction.FORWARD));

				oiButton(1, "LStickBtn").whenPressed(new ResetEncoders());
				oiButton(1, "RStickBtn").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));
				break;
			case "Controller (Gamepad F310)":
				oiButton(1, "Y").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton(1, "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton(1, "B").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton(1, "A").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton(1, "LTrigger").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));
				oiButton(1, "RTrigger").whileHeld(new MoveLiftManual(Direction.DOWN));

				oiButton(1, "LBumper").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton(1, "RBumper").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton(1, "Back").whileHeld(new ControlH(Direction.BACKWARD));
				oiButton(1, "Start").whileHeld(new ControlH(Direction.FORWARD));

				oiButton(1, "LStickBtn").whenPressed(new ResetEncoders());
				oiButton(1, "RStickBtn").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));
				break;
			case "Wireless Controller":
				oiButton(1, "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton(1, "Square").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton(1, "Circle").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton(1, "Triangle").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton(1, "L1").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));
				oiButton(1, "R1").whileHeld(new MoveLiftManual(Direction.DOWN));

				oiButton(1, "L2").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton(1, "R2").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton(1, "Share").whileHeld(new ControlH(Direction.BACKWARD));
				oiButton(1, "Options").whileHeld(new ControlH(Direction.FORWARD));

				oiButton(1, "PS4Btn").whenPressed(new ResetEncoders());
				oiButton(1, "Touchpad").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));
				break;
			}
			oiButton(1, "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));
		} catch (NullPointerException | IOException | ParseException e) {
			System.out.print("[!] PROFILES FILE ERROR: " + e.getMessage());
			System.exit(1);
		}
	}
}