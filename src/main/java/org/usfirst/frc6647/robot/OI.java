/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import java.util.HashMap;

import org.usfirst.frc6647.commands.ChangeVelocity;
import org.usfirst.frc6647.commands.GyroAlign;
import org.usfirst.frc6647.commands.GyroMove;
import org.usfirst.frc6647.commands.HabClimb;
import org.usfirst.frc6647.commands.MoveBall;
import org.usfirst.frc6647.commands.MoveLiftManual;
import org.usfirst.frc6647.commands.MoveLiftPID;
import org.usfirst.frc6647.commands.MoveLiftPID.Height;
import org.usfirst.frc6647.commands.MoveLiftPID.Target;
import org.usfirst.frc6647.commands.PushHatch;
import org.usfirst.frc6647.commands.ResetEncoders;
import org.usfirst.frc6647.commands.Rotate;
import org.usfirst.frc6647.commands.Slide;
import org.usfirst.frc6647.commands.TiltIntakeManual;
import org.usfirst.frc6647.commands.ToggleHatch;
import org.usfirst.frc6647.subsystems.Chassis;
import org.usfirst.lib6647.oi.JController;
import org.usfirst.lib6647.util.MoveDirection;

/**
 * Class for registering driver input.
 */
public class OI {
	/** HashMap holding initialized joysticks. */
	HashMap<String, JController> joysticks = new HashMap<String, JController>();

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

			ChangeVelocity changeVelSlow = new ChangeVelocity(0.6, 0.3);
			ChangeVelocity changeVelFast = new ChangeVelocity(0.75, 0.5);

			PushHatch pushHatch = new PushHatch();
			ToggleHatch toggleHatch = new ToggleHatch();
			GyroAlign gyroAlign = new GyroAlign();

			GyroMove dPadGyroFwd = new GyroMove(MoveDirection.FORWARD);
			GyroMove dPadGyroBwd = new GyroMove(MoveDirection.BACKWARD);
			Rotate rotateLeft = new Rotate(MoveDirection.LEFT, 0.35);
			Rotate rotateRight = new Rotate(MoveDirection.RIGHT, 0.35);

			HabClimb climbFront = new HabClimb("frontSolenoid");
			HabClimb climbBack = new HabClimb("backSolenoid");

			System.out.println("[!] Commands successfully initialized for Driver1!");

			if (driver1.getName().equals("Wireless Controller")) {

				driver1.get("Square").whenPressed(changeVelSlow);
				driver1.get("Triangle").whenPressed(changeVelFast);
				driver1.get("Circle").whileHeld(climbFront);
				driver1.get("X").whileHeld(climbBack);

				driver1.get("L1").whileHeld(pushHatch);
				driver1.get("R1").whileHeld(gyroAlign);

				driver1.get("L2").whileHeld(new Slide(MoveDirection.LEFT, 3, 4, false, 0.7));
				driver1.get("R2").whileHeld(new Slide(MoveDirection.RIGHT, 3, 4, false, 0.7));

				driver1.get("dPadUp").whileHeld(dPadGyroFwd);
				driver1.get("dPadDown").whileHeld(dPadGyroBwd);

				driver1.get("RStickLeft").whileHeld(rotateLeft);
				driver1.get("RStickRight").whileHeld(rotateRight);

				System.out.println("[!] Commands successfully registered for Driver1!");
				joysticks.put("driver1", driver1);
			} else if (driver1.getName().toLowerCase().contains("xbox")
					|| driver1.getName().equals("Controller (Gamepad F310)")) {

				driver1.get("Y").whenPressed(changeVelSlow);
				driver1.get("X").whenPressed(changeVelFast);
				driver1.get("B").whileHeld(climbFront);
				driver1.get("A").whileHeld(climbBack);

				driver1.get("LBumper").whileHeld(pushHatch);
				driver1.get("RBumper").whileHeld(gyroAlign);

				driver1.get("LTrigger").whileHeld(new Slide(MoveDirection.LEFT, 2, 3, true, 0.7));
				driver1.get("RTrigger").whileHeld(new Slide(MoveDirection.RIGHT, 2, 3, true, 0.7));

				driver1.get("dPadUp").whileHeld(dPadGyroFwd);
				driver1.get("dPadDown").whileHeld(dPadGyroBwd);

				driver1.get("RStickLeft").whileHeld(rotateLeft);
				driver1.get("RStickRight").whileHeld(rotateRight);

				System.out.println("[!] Commands successfully registered for Driver1!");
				joysticks.put("driver1", driver1);
			} else if (driver1.getName().equals("vJoy Device")) {
				driver1.get("1").whenPressed(changeVelSlow);
				driver1.get("2").whenPressed(changeVelFast);
				driver1.get("O").whileHeld(climbFront);
				driver1.get("P").whileHeld(climbBack);

				driver1.get("E").whenPressed(toggleHatch);
				driver1.get("Shift").whileHeld(gyroAlign);

				driver1.get("A").whileHeld(new Slide(MoveDirection.LEFT, 0.7));
				driver1.get("D").whileHeld(new Slide(MoveDirection.RIGHT, 0.7));

				driver1.get("W").whileHeld(dPadGyroFwd);
				driver1.get("S").whileHeld(dPadGyroBwd);

				driver1.get("MouseLeft").whileHeld(rotateLeft);
				driver1.get("MouseRight").whileHeld(rotateRight);

				Chassis.getInstance().setTankDrive(false);

				System.out.println("[!] Commands successfully registered for Driver1!");
				joysticks.put("driver1", driver1);
			}
		}

		JController driver2 = new JController(1);

		if (!driver2.getName().isEmpty()) {
			System.out.println("[!] Driver2 found!\n" + "[!] " + driver2.getName());

			MoveLiftPID liftCargoShip = new MoveLiftPID(Target.CARGO, Height.SHIP);
			MoveLiftPID liftCargoLow = new MoveLiftPID(Target.CARGO, Height.LOW);
			MoveLiftPID liftCargoMid = new MoveLiftPID(Target.CARGO, Height.MID);
			MoveLiftPID liftCargoHigh = new MoveLiftPID(Target.CARGO, Height.HIGH);

			MoveLiftManual moveLiftUp = new MoveLiftManual(MoveDirection.UP);
			MoveLiftManual moveLiftDown = new MoveLiftManual(MoveDirection.DOWN);

			TiltIntakeManual tiltIntakeUp = new TiltIntakeManual(MoveDirection.UP);
			TiltIntakeManual tiltIntakeDown = new TiltIntakeManual(MoveDirection.DOWN);

			ResetEncoders resetEncoders = new ResetEncoders();
			MoveLiftPID liftChaseCargo = new MoveLiftPID(Target.CARGO, Height.FLOOR);

			MoveLiftPID liftHatchShip = new MoveLiftPID(Target.HATCH, Height.SHIP);
			MoveLiftPID liftHatchLow = new MoveLiftPID(Target.HATCH, Height.LOW);
			MoveLiftPID liftHatchMid = new MoveLiftPID(Target.HATCH, Height.MID);
			MoveLiftPID liftHatchHigh = new MoveLiftPID(Target.HATCH, Height.HIGH);

			MoveBall ballOut = new MoveBall(MoveDirection.OUT, 0.7);
			MoveBall ballIn = new MoveBall(MoveDirection.IN, 0.7);

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
				joysticks.put("driver2", driver2);
			} else if (driver2.getName().toLowerCase().contains("xbox")
					|| driver2.getName().equals("Controller (Gamepad F310)")) {

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
				joysticks.put("driver2", driver2);
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
	public JController getJoystick(String joystickName) {
		return joysticks.get(joystickName);
	}
}