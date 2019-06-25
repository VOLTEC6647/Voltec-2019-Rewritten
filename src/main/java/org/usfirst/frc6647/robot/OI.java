/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import org.usfirst.frc6647.commands.AlignNext;
import org.usfirst.frc6647.commands.ChangeVelocity;
import org.usfirst.frc6647.commands.GyroAlign;
import org.usfirst.frc6647.commands.MoveBall;
import org.usfirst.frc6647.commands.MoveLiftManual;
import org.usfirst.frc6647.commands.MoveLiftPID;
import org.usfirst.frc6647.commands.MoveLiftPID.Height;
import org.usfirst.frc6647.commands.MoveLiftPID.Target;
import org.usfirst.frc6647.commands.PushHatch;
import org.usfirst.frc6647.commands.ResetEncoders;
import org.usfirst.frc6647.commands.Slide;
import org.usfirst.frc6647.commands.TiltIntakeManual;
import org.usfirst.frc6647.commands.ZeroYaw;
import org.usfirst.lib6647.oi.ButtonHelper;
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.util.Direction;

import edu.wpi.first.wpilibj.DriverStation;
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

		if (!joysticks.get(0).getName().equals(null)) {
			DriverStation.reportWarning("[!] Controller 1 found!", false);
			System.out.println("[!] Controller 1 found!");

			if (joysticks.get(0).getName().equals("Wireless Controller")) {

				oiButton(0, "Square").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton(0, "X").whileHeld(new PushHatch());
				oiButton(0, "Triangle").whenPressed(new ChangeVelocity(0.75, 0.75, false));

				oiButton(0, "L1").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton(0, "R1").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton(0, "L2").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "R2").whileHeld(new Slide(Direction.RIGHT));

				oiButton(0, "PS4Btn").whenPressed(new ZeroYaw());
				oiButton(0, "Touchpad").whileHeld(new GyroAlign());

				oiButton(0, "dPadUp").whileHeld(new GyroAlign());
				oiButton(0, "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
				oiButton(0, "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
				oiButton(0, "dPadDown").whileHeld(new GyroAlign());

			} else if (joysticks.get(0).getName().equals("Controller (Gamepad F310)")) {

				oiButton(0, "A").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton(0, "B").whileHeld(new PushHatch());
				oiButton(0, "Y").whenPressed(new ChangeVelocity(0.75, 0.75, false));

				oiButton(0, "LTrigger").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton(0, "RTrigger").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton(0, "Back").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "Start").whileHeld(new Slide(Direction.RIGHT));

				oiButton(0, "LStickBtn").whenPressed(new ZeroYaw());
				oiButton(0, "RStickBtn").toggleWhenPressed(new GyroAlign());

				oiButton(0, "dPadUp").whileHeld(new GyroAlign());
				oiButton(0, "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
				oiButton(0, "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
				oiButton(0, "dPadDown").whileHeld(new GyroAlign());

			} else if (joysticks.get(0).getName().equals("Generic   USB  Joystick")) {

				oiButton(0, "1").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton(0, "2").whileHeld(new PushHatch());
				oiButton(0, "4").whenPressed(new ChangeVelocity(0.75, 0.75, false));

				oiButton(0, "LTrigger").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton(0, "RTrigger").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton(0, "LBumper").whileHeld(new Slide(Direction.LEFT));
				oiButton(0, "RBumper").whileHeld(new Slide(Direction.RIGHT));

				oiButton(0, "Select").whenPressed(new ZeroYaw());
				oiButton(0, "Start").toggleWhenPressed(new GyroAlign());

				oiButton(0, "dPadUp").whileHeld(new GyroAlign());
				oiButton(0, "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
				oiButton(0, "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
				oiButton(0, "dPadDown").whileHeld(new GyroAlign());

				joysticks.get(0).setLeftRightAxis(1, 4);

			} else if (joysticks.get(0).getButtonCount() >= 10 && joysticks.get(0).getPOVCount() > 0) {
				loadUnsupportedController(0, joysticks.get(0).getName());
			} else {
				fullyUnsupportedController(joysticks.get(0).getName());
			}

			DriverStation.reportWarning(
					"[!] '" + joysticks.get(0).getName().toUpperCase() + "' loaded as Controller 1!", false);
			System.out.println("[!] '" + joysticks.get(0).getName().toUpperCase() + "' loaded as Controller 1!");
		}

		joysticks.add(new JController(1));

		if (!joysticks.get(1).getName().equals(null)) {
			DriverStation.reportWarning("[!] Controller 2 found!", false);
			System.out.println("[!] Controller 2 found!");

			if (joysticks.get(1).getName().equals("Wireless Controller")) {

				oiButton(1, "Square").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton(1, "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton(1, "Circle").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton(1, "Triangle").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton(1, "L1").whileHeld(new MoveLiftManual(Direction.DOWN));
				oiButton(1, "R1").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton(1, "L2").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton(1, "R2").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton(1, "PS4Btn").whenPressed(new ResetEncoders());
				oiButton(1, "Touchpad").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));

				oiButton(1, "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));
				oiButton(1, "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton(1, "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton(1, "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));

			} else if (joysticks.get(1).getName().equals("Controller (Gamepad F310)")) {

				oiButton(1, "A").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton(1, "B").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton(1, "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton(1, "Y").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton(1, "L1").whileHeld(new MoveLiftManual(Direction.DOWN));
				oiButton(1, "R1").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton(1, "L2").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton(1, "R2").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton(1, "PS4Btn").whenPressed(new ResetEncoders());
				oiButton(1, "Touchpad").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));

				oiButton(1, "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));
				oiButton(1, "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton(1, "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton(1, "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));

			} else if (joysticks.get(1).getName().equals("Generic   USB  Joystick")) {

				oiButton(1, "1").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton(1, "2").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton(1, "3").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton(1, "4").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton(1, "LTrigger").whileHeld(new MoveLiftManual(Direction.DOWN));
				oiButton(1, "RTrigger").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton(1, "LBumper").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton(1, "RBumper").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton(1, "Start").whenPressed(new ResetEncoders());
				oiButton(1, "Select").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));

				oiButton(1, "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));
				oiButton(1, "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton(1, "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton(1, "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));

				joysticks.get(0).setLeftRightAxis(1, 4);

			} else if (joysticks.get(1).getButtonCount() >= 10 && joysticks.get(1).getPOVCount() > 0) {
				loadUnsupportedController(1, joysticks.get(1).getName());
			} else {
				fullyUnsupportedController(joysticks.get(1).getName());
			}

			DriverStation.reportWarning(
					"[!] '" + joysticks.get(0).getName().toUpperCase() + "' loaded as Controller 2!", false);
			System.out.println("[!] '" + joysticks.get(0).getName().toUpperCase() + "' loaded as Controller 2!");
		}
	}

	/**
	 * Method to load an unsupported Controller with default settings.
	 * 
	 * @param port
	 * @param name
	 */
	private void loadUnsupportedController(int port, String name) {
		DriverStation.reportWarning(
				"[!] Unsupported Controller: '" + name.toUpperCase() + "'. Loading default settings...", false);
		System.out.println("[!] Unsupported Controller: '" + name.toUpperCase() + "'. Loading default settings...");

		oiButton(port, 1).whenPressed(new ChangeVelocity(0.6, 0.6, true));
		oiButton(port, 2).whileHeld(new PushHatch());
		oiButton(port, 4).whenPressed(new ChangeVelocity(0.75, 0.75, false));

		oiButton(port, 5).whileHeld(new MoveBall(Direction.IN, 0.7));
		oiButton(port, 6).whileHeld(new MoveBall(Direction.OUT, 0.7));

		oiButton(port, 7).whileHeld(new Slide(Direction.LEFT));
		oiButton(port, 8).whileHeld(new Slide(Direction.RIGHT));

		oiButton(port, 9).whenPressed(new ZeroYaw());
		oiButton(port, 10).toggleWhenPressed(new GyroAlign());

		oiButton(port, "dPad", 0, 0).whileHeld(new GyroAlign());
		oiButton(port, "dPad", 0, 270).whileHeld(new AlignNext(Direction.LEFT));
		oiButton(port, "dPad", 0, 90).whileHeld(new AlignNext(Direction.RIGHT));
		oiButton(port, "dPad", 0, 180).whileHeld(new GyroAlign());

		joysticks.get(port).setLeftRightAxis(1, 4);
	}

	/**
	 * Method to report a fully unsupported Controller.
	 * 
	 * @param name
	 */
	private void fullyUnsupportedController(String name) {
		DriverStation.reportWarning(
				"[!] Completely unsupported Controller: '" + name.toUpperCase() + "'. What are you trying to use?",
				false);
		System.out.println(
				"[!] Completely unsupported Controller: '" + name.toUpperCase() + "'. What are you trying to use?");
		System.exit(1);
	}
}