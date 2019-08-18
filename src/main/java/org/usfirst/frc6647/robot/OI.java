/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import java.util.ArrayList;

import org.usfirst.frc6647.commands.AlignNext;
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
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.util.MoveDirection;

/**
 * Class for registering driver input.
 */
public class OI {
	/** ArrayList holding initialized joysticks. */
	ArrayList<JController> joysticks = new ArrayList<JController>();

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

		JController driver1 = new JController(0);

		if (!driver1.getName().isEmpty()) {
			System.out.println("[!] Driver1 found!\n" + "[!] " + driver1.getName());

			ChangeVelocity changeVelSlow = new ChangeVelocity(0.6, 0.6, true, "frontLeft", "frontRight");
			ChangeVelocity changeVelFast = new ChangeVelocity(0.75, 0.9, false, "frontLeft", "frontRight");

			ToggleHatch toggleHatch = new ToggleHatch();
			GyroAlign gyroAlign = new GyroAlign();

			Slide slideLeft = new Slide(MoveDirection.LEFT, 0.7);
			Slide slideRight = new Slide(MoveDirection.RIGHT, 0.7);

			GyroMove dPadGyro = new GyroMove();
			AlignNext alignLeft = new AlignNext(MoveDirection.LEFT);
			AlignNext alignRight = new AlignNext(MoveDirection.RIGHT);

			System.out.println("[!] Commands successfully initialized for Driver1!");

			if (driver1.getName().equals("Wireless Controller")) {

				driver1.get("Square").whenPressed(changeVelSlow);
				driver1.get("Triangle").whenPressed(changeVelFast);

				driver1.get("L1").whenPressed(toggleHatch);
				driver1.get("R1").whileHeld(gyroAlign);

				driver1.get("L2").whileHeld(slideLeft);
				driver1.get("R2").whileHeld(slideRight);

				driver1.get("dPadUp").whileHeld(dPadGyro);
				driver1.get("dPadLeft").whenPressed(alignLeft);
				driver1.get("dPadRight").whenPressed(alignRight);
				driver1.get("dPadDown").whileHeld(dPadGyro);

				System.out.println("[!] Commands successfully registered for Driver1!");
				joysticks.add(driver1);
			} else if (driver1.getName().equals("Controller (XBOX 360 For Windows)")
					|| driver1.getName().equals("Controller (Xbox One For Windows)")) {

				driver1.get("Y").whenPressed(changeVelSlow);
				driver1.get("X").whenPressed(changeVelFast);

				driver1.get("LBumper").whenPressed(toggleHatch);
				driver1.get("RBumper").whileHeld(gyroAlign);

				driver1.get("LTrigger").whileHeld(slideLeft);
				driver1.get("RTrigger").whileHeld(slideRight);

				driver1.get("dPadUp").whileHeld(dPadGyro);
				driver1.get("dPadLeft").whenPressed(alignLeft);
				driver1.get("dPadRight").whenPressed(alignRight);
				driver1.get("dPadDown").whileHeld(dPadGyro);

				System.out.println("[!] Commands successfully registered for Driver1!");
				joysticks.add(driver1);
			}
		}

		JController driver2 = new JController(1);

		if (!driver2.getName().isEmpty()) {
			System.out.println("[!] Driver2 found!\n" + "[!] " + driver2.getName());

			MoveLiftPID liftCargoShip = new MoveLiftPID(Target.CARGO, Height.SHIP, "liftMain", "liftEncoder");
			MoveLiftPID liftCargoLow = new MoveLiftPID(Target.CARGO, Height.LOW, "liftMain", "liftEncoder");
			MoveLiftPID liftCargoMid = new MoveLiftPID(Target.CARGO, Height.MID, "liftMain", "liftEncoder");
			MoveLiftPID liftCargoHigh = new MoveLiftPID(Target.CARGO, Height.HIGH, "liftMain", "liftEncoder");

			MoveLiftManual moveLiftUp = new MoveLiftManual(MoveDirection.UP, "liftMain");
			MoveLiftManual moveLiftDown = new MoveLiftManual(MoveDirection.DOWN, "liftMain");

			TiltIntakeManual tiltIntakeUp = new TiltIntakeManual(MoveDirection.UP, "tiltIntake");
			TiltIntakeManual tiltIntakeDown = new TiltIntakeManual(MoveDirection.DOWN, "tiltIntake");

			ResetEncoders resetEncoders = new ResetEncoders("liftEncoder");
			MoveLiftPID liftChaseCargo = new MoveLiftPID(Target.CARGO, Height.FLOOR, "liftMain", "liftEncoder");

			MoveLiftPID liftHatchShip = new MoveLiftPID(Target.HATCH, Height.SHIP, "liftMain", "liftEncoder");
			MoveLiftPID liftHatchLow = new MoveLiftPID(Target.HATCH, Height.LOW, "liftMain", "liftEncoder");
			MoveLiftPID liftHatchMid = new MoveLiftPID(Target.HATCH, Height.MID, "liftMain", "liftEncoder");
			MoveLiftPID liftHatchHigh = new MoveLiftPID(Target.HATCH, Height.HIGH, "liftMain", "liftEncoder");

			MoveBall ballOut = new MoveBall(MoveDirection.OUT, 0.7, "intakeLeft", "intakeRight");
			MoveBall ballIn = new MoveBall(MoveDirection.IN, 0.7, "intakeLeft", "intakeRight");

			System.out.println("[!] Commands successfully initialized for Driver2!");

			if (driver2.getName().equals("Wireless Controller")) {

				driver2.get("Square").whileHeld(liftCargoShip);
				driver2.get("X").whileHeld(liftCargoLow);
				driver2.get("Circle").whileHeld(liftCargoMid);
				driver2.get("Triangle").whileHeld(liftCargoHigh);

				driver2.get("L1").whileHeld(moveLiftUp);
				driver2.get("R1").whileHeld(moveLiftDown);

				driver2.get("L2").whileHeld(tiltIntakeUp);
				driver2.get("R2").whileHeld(tiltIntakeDown);

				driver2.get("PS4Btn").whenPressed(resetEncoders);
				driver2.get("Touchpad").whileHeld(liftChaseCargo);

				driver2.get("dPadUp").whileHeld(liftHatchHigh);
				driver2.get("dPadLeft").whileHeld(liftHatchMid);
				driver2.get("dPadRight").whileHeld(liftHatchShip);
				driver2.get("dPadDown").whileHeld(liftHatchLow);

				driver2.get("LStickUp").whileHeld(ballOut);
				driver2.get("LStickDown").whileHeld(ballIn);

				System.out.println("[!] Commands successfully registered for Driver2!");
				joysticks.add(driver2);
			} else if (driver2.getName().equals("Controller (XBOX 360 For Windows)")
					|| driver2.getName().equals("Controller (Xbox One For Windows)")) {

				driver2.get("X").whileHeld(liftCargoShip);
				driver2.get("A").whileHeld(liftCargoLow);
				driver2.get("B").whileHeld(liftCargoMid);
				driver2.get("Y").whileHeld(liftCargoHigh);

				driver2.get("LBumper").whileHeld(moveLiftUp);
				driver2.get("RBumper").whileHeld(moveLiftDown);

				driver2.get("LTrigger").whileHeld(tiltIntakeUp);
				driver2.get("RTrigger").whileHeld(tiltIntakeDown);

				driver2.get("Back").whenPressed(resetEncoders);
				driver2.get("Start").whileHeld(liftChaseCargo);

				driver2.get("dPadUp").whileHeld(liftHatchHigh);
				driver2.get("dPadLeft").whileHeld(liftHatchMid);
				driver2.get("dPadRight").whileHeld(liftHatchShip);
				driver2.get("dPadDown").whileHeld(liftHatchLow);

				driver2.get("LStickUp").whileHeld(ballOut);
				driver2.get("LStickDown").whileHeld(ballIn);

				System.out.println("[!] Commands successfully registered for Driver2!");
				joysticks.add(driver2);
			}
		}
	}

	/**
	 * Method to get a {@link JController} out of the {@link #joysticks HashMap}
	 * using its name.
	 * 
	 * @param joystickName
	 * @return JController
	 */
	public JController getJoystick(int joystick) {
		return joysticks.get(joystick);
	}
}