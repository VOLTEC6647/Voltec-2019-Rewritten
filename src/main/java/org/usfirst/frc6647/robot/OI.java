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

		joysticks.put("Driver1", new JController(0));

		if (!joysticks.get("Driver1").getName().equals(null)) {
			DriverStation.reportWarning("[!] Controller 1 found!", false);
			System.out.println("[!] Controller 1 found!");

			if (joysticks.get("Driver1").getName().equals("Wireless Controller")) {

				oiButton("Driver1", "Square").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton("Driver1", "X").whileHeld(new PushHatch());
				oiButton("Driver1", "Triangle").whenPressed(new ChangeVelocity(0.75, 0.75, false));

				oiButton("Driver1", "L1").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton("Driver1", "R1").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton("Driver1", "L2").whileHeld(new Slide(Direction.LEFT));
				oiButton("Driver1", "R2").whileHeld(new Slide(Direction.RIGHT));

				oiButton("Driver1", "PS4Btn").whenPressed(new ZeroYaw());
				oiButton("Driver1", "Touchpad").whileHeld(new GyroAlign());

				oiButton("Driver1", "dPadUp").whileHeld(new GyroAlign());
				oiButton("Driver1", "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
				oiButton("Driver1", "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
				oiButton("Driver1", "dPadDown").whileHeld(new GyroAlign());

			} else if (joysticks.get("Driver1").getName().equals("Controller (Gamepad F310)")) {

				oiButton("Driver1", "A").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton("Driver1", "B").whileHeld(new PushHatch());
				oiButton("Driver1", "Y").whenPressed(new ChangeVelocity(0.75, 0.75, false));

				oiButton("Driver1", "LTrigger").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton("Driver1", "RTrigger").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton("Driver1", "Back").whileHeld(new Slide(Direction.LEFT));
				oiButton("Driver1", "Start").whileHeld(new Slide(Direction.RIGHT));

				oiButton("Driver1", "LStickBtn").whenPressed(new ZeroYaw());
				oiButton("Driver1", "RStickBtn").toggleWhenPressed(new GyroAlign());

				oiButton("Driver1", "dPadUp").whileHeld(new GyroAlign());
				oiButton("Driver1", "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
				oiButton("Driver1", "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
				oiButton("Driver1", "dPadDown").whileHeld(new GyroAlign());

			} else if (joysticks.get("Driver1").getName().equals("Generic   USB  Joystick")) {

				oiButton("Driver1", "1").whenPressed(new ChangeVelocity(0.6, 0.6, true));
				oiButton("Driver1", "2").whileHeld(new PushHatch());
				oiButton("Driver1", "4").whenPressed(new ChangeVelocity(0.75, 0.75, false));

				oiButton("Driver1", "LTrigger").whileHeld(new MoveBall(Direction.IN, 0.7));
				oiButton("Driver1", "RTrigger").whileHeld(new MoveBall(Direction.OUT, 0.7));

				oiButton("Driver1", "LBumper").whileHeld(new Slide(Direction.LEFT));
				oiButton("Driver1", "RBumper").whileHeld(new Slide(Direction.RIGHT));

				oiButton("Driver1", "Select").whenPressed(new ZeroYaw());
				oiButton("Driver1", "Start").toggleWhenPressed(new GyroAlign());

				oiButton("Driver1", "dPadUp").whileHeld(new GyroAlign());
				oiButton("Driver1", "dPadLeft").whileHeld(new AlignNext(Direction.LEFT));
				oiButton("Driver1", "dPadRight").whileHeld(new AlignNext(Direction.RIGHT));
				oiButton("Driver1", "dPadDown").whileHeld(new GyroAlign());

				joysticks.get("Driver1").setLeftRightAxis(1, 4);

			} else if (joysticks.get("Driver1").getButtonCount() >= 10 && joysticks.get("Driver1").getPOVCount() > 0) {
				loadUnsupportedController("Driver1", joysticks.get("Driver1").getName());
			} else {
				fullyUnsupportedController(joysticks.get("Driver1").getName());
			}

			DriverStation.reportWarning(
					"[!] '" + joysticks.get("Driver1").getName().toUpperCase() + "' loaded as Controller 1!", false);
			System.out.println("[!] '" + joysticks.get("Driver1").getName().toUpperCase() + "' loaded as Controller 1!");
		}

		joysticks.put("Driver2", new JController(1));

		if (!joysticks.get("Driver2").getName().equals(null)) {
			DriverStation.reportWarning("[!] Controller 2 found!", false);
			System.out.println("[!] Controller 2 found!");

			if (joysticks.get("Driver2").getName().equals("Wireless Controller")) {

				oiButton("Driver2", "Square").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton("Driver2", "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton("Driver2", "Circle").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton("Driver2", "Triangle").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton("Driver2", "L1").whileHeld(new MoveLiftManual(Direction.DOWN));
				oiButton("Driver2", "R1").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton("Driver2", "L2").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton("Driver2", "R2").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton("Driver2", "PS4Btn").whenPressed(new ResetEncoders());
				oiButton("Driver2", "Touchpad").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));

				oiButton("Driver2", "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));
				oiButton("Driver2", "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));

			} else if (joysticks.get("Driver2").getName().equals("Controller (Gamepad F310)")) {

				oiButton("Driver2", "A").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton("Driver2", "B").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton("Driver2", "X").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton("Driver2", "Y").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton("Driver2", "L1").whileHeld(new MoveLiftManual(Direction.DOWN));
				oiButton("Driver2", "R1").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton("Driver2", "L2").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton("Driver2", "R2").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton("Driver2", "PS4Btn").whenPressed(new ResetEncoders());
				oiButton("Driver2", "Touchpad").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));

				oiButton("Driver2", "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));
				oiButton("Driver2", "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));

			} else if (joysticks.get("Driver2").getName().equals("Generic   USB  Joystick")) {

				oiButton("Driver2", "1").whileHeld(new MoveLiftPID(Target.CARGO, Height.SHIP));
				oiButton("Driver2", "2").whileHeld(new MoveLiftPID(Target.CARGO, Height.LOW));
				oiButton("Driver2", "3").whileHeld(new MoveLiftPID(Target.CARGO, Height.FLOOR));
				oiButton("Driver2", "4").whileHeld(new MoveLiftPID(Target.CARGO, Height.MID));

				oiButton("Driver2", "LTrigger").whileHeld(new MoveLiftManual(Direction.DOWN));
				oiButton("Driver2", "RTrigger").whileHeld(new MoveLiftPID(Target.CARGO, Height.HIGH));

				oiButton("Driver2", "LBumper").whileHeld(new TiltIntakeManual(Direction.UP));
				oiButton("Driver2", "RBumper").whileHeld(new TiltIntakeManual(Direction.DOWN));

				oiButton("Driver2", "Start").whenPressed(new ResetEncoders());
				oiButton("Driver2", "Select").whileHeld(new MoveLiftPID(Target.HATCH, Height.FLOOR));

				oiButton("Driver2", "dPadUp").whileHeld(new MoveLiftPID(Target.HATCH, Height.LOW));
				oiButton("Driver2", "dPadLeft").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadRight").whileHeld(new MoveLiftPID(Target.HATCH, Height.MID));
				oiButton("Driver2", "dPadDown").whileHeld(new MoveLiftPID(Target.HATCH, Height.HIGH));

				joysticks.get("Driver2").setLeftRightAxis(1, 4);

			} else if (joysticks.get("Driver2").getButtonCount() >= 10 && joysticks.get("Driver2").getPOVCount() > 0) {
				loadUnsupportedController("Driver2", joysticks.get("Driver2").getName());
			} else {
				fullyUnsupportedController(joysticks.get("Driver2").getName());
			}

			DriverStation.reportWarning(
					"[!] '" + joysticks.get("Driver2").getName().toUpperCase() + "' loaded as Controller 2!", false);
			System.out.println("[!] '" + joysticks.get("Driver2").getName().toUpperCase() + "' loaded as Controller 2!");
		}
	}

	/**
	 * Method to load an unsupported Controller with default settings.
	 * 
	 * @param port
	 * @param name
	 */
	private void loadUnsupportedController(String joystickName, String name) {
		DriverStation.reportWarning(
				"[!] Unsupported Controller: '" + name.toUpperCase() + "'. Loading default settings...", false);
		System.out.println("[!] Unsupported Controller: '" + name.toUpperCase() + "'. Loading default settings...");

		oiButton(joystickName, 1).whenPressed(new ChangeVelocity(0.6, 0.6, true));
		oiButton(joystickName, 2).whileHeld(new PushHatch());
		oiButton(joystickName, 4).whenPressed(new ChangeVelocity(0.75, 0.75, false));

		oiButton(joystickName, 5).whileHeld(new MoveBall(Direction.IN, 0.7));
		oiButton(joystickName, 6).whileHeld(new MoveBall(Direction.OUT, 0.7));

		oiButton(joystickName, 7).whileHeld(new Slide(Direction.LEFT));
		oiButton(joystickName, 8).whileHeld(new Slide(Direction.RIGHT));

		oiButton(joystickName, 9).whenPressed(new ZeroYaw());
		oiButton(joystickName, 10).toggleWhenPressed(new GyroAlign());

		oiButton(joystickName, "dPad", 0, 0).whileHeld(new GyroAlign());
		oiButton(joystickName, "dPad", 0, 270).whileHeld(new AlignNext(Direction.LEFT));
		oiButton(joystickName, "dPad", 0, 90).whileHeld(new AlignNext(Direction.RIGHT));
		oiButton(joystickName, "dPad", 0, 180).whileHeld(new GyroAlign());

		joysticks.get(joystickName).setLeftRightAxis(1, 4);
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