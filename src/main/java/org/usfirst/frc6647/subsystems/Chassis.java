/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.lib6647.util.TalonBuilder;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the chassis.
 */
public class Chassis extends Subsystem {

	private final int TALON_FRONT_LEFT_PORT = 4, TALON_FRONT_RIGHT_PORT = 8, VICTOR_BACK_LEFT_PORT = 2,
			VICTOR_BACK_RIGHT_PORT = 7;
	private TalonBuilder frontLeft, frontRight;

	private final double RAMPDRIVE = 0.2;
	private final int LOOP = 20, fpidIdx = 0, ftimeoutMs = 0, sensorPos = 0, pidIdx = 0, timeoutMs = 0, slotIdx = 0;
	private final boolean phase = true;
	private double p = .25, i = 0.0, d = 0.0, f = 1023 / 2200;

	private double TOLERANCE = 0.15, LIMITER = 0.75;
	private final int direction = 1;
	private double leftStickY, rightStickY;

	private static Chassis m_instance = null;

	/**
	 * Creates static Chassis instance.
	 */
	public static void createInstance() {
		m_instance = new Chassis();
	}

	/**
	 * Gets static Chassis instance. If there is none, creates one.
	 * 
	 * @return static Chassis instance
	 */
	public static Chassis getInstance() {
		if (m_instance == null) {
			createInstance();
		}
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 * 
	 * Initializes both talons and victors with the values defined at the top of
	 * this class.
	 */
	public Chassis() {
		frontLeft = new TalonBuilder(TALON_FRONT_LEFT_PORT, VICTOR_BACK_LEFT_PORT, NeutralMode.Coast, true, RAMPDRIVE,
				LOOP, FeedbackDevice.QuadEncoder, fpidIdx, ftimeoutMs, phase, sensorPos, pidIdx, timeoutMs, slotIdx, p,
				i, d, f);
		frontRight = new TalonBuilder(TALON_FRONT_RIGHT_PORT, VICTOR_BACK_RIGHT_PORT, NeutralMode.Coast, false,
				RAMPDRIVE, LOOP, FeedbackDevice.QuadEncoder, fpidIdx, ftimeoutMs, phase, sensorPos, pidIdx, timeoutMs,
				slotIdx, p, i, d, f);
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		leftStickY = joystickMap.mapDoubleT(OI.getInstance().joysticks.get(1).getRawAxis(1), TOLERANCE, 1, 0, 1)
				* direction;
		rightStickY = joystickMap.mapDoubleT(OI.getInstance().joysticks.get(1).getRawAxis(5), TOLERANCE, 1, 0, 1)
				* direction;

		if (!SmartDashboard.getBoolean("Gyro", true))
			Chassis.getInstance().setBothTalons(leftStickY, rightStickY);
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Gets frontLeft Talon, with backLeft Victor as follower.
	 * 
	 * @return frontLeft Talon
	 */
	public TalonBuilder getLeftTalon() {
		return frontLeft;
	}

	/**
	 * Gets frontRight Talon, with backRight Victor as follower.
	 * 
	 * @return frontRight Talon
	 */
	public TalonBuilder getRightTalon() {
		return frontRight;
	}

	/**
	 * Sets leftTalon to the given speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void setLeftTalon(double speed) {
		frontLeft.set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Sets rightTalon to the given speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void setRightTalon(double speed) {
		frontRight.set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Sets both talons to the given speed, in PercentOutput.
	 * 
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public void setBothTalons(double leftSpeed, double rightSpeed) {
		setLeftTalon(leftSpeed * LIMITER);
		setRightTalon(rightSpeed * LIMITER);
	}

	/**
	 * Stops both talons dead in their tracks.
	 */
	public void stopTalons() {
		setBothTalons(0, 0);
	}

	/**
	 * Functional interface for Joystick mapping.
	 */
	private interface MapDoubleT {
		/**
		 * Abstract method for Joystick mapping.
		 * 
		 * @param rawAxis
		 * @param in_min
		 * @param in_max
		 * @param out_min
		 * @param out_max
		 * @return mapDoubleT
		 */
		double mapDoubleT(double rawAxis, double in_min, double in_max, double out_min, double out_max);
	}

	/**
	 * Lambda declaration for mapping joystick input.
	 */
	private MapDoubleT joystickMap = (x, in_min, in_max, out_min, out_max) -> Math.abs(x) < in_min ? 0
			: x < 0 ? (x + in_min) * (-out_max + out_min) / (-in_max + in_min) - out_min
					: (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}