/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.SuperTalon;
import org.usfirst.lib6647.util.TalonBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the chassis.
 */
public class Chassis extends SuperSubsystem implements SuperTalon {

	private double TOLERANCE = 0.15, LIMITER = 0.75;
	private final int direction = 1;
	private double leftStickY, rightStickY;

	private static Chassis m_instance = null;

	/**
	 * Creates static Chassis instance.
	 */
	public static void createInstance() {
		m_instance = new Chassis("chassis");
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
	 */
	public Chassis(String name) {
		super(name, "src\\main\\java\\org\\usfirst\\frc6647\\robot\\RobotMap.json");

		initTalons(robotMap, getName());
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
		return talons.get("frontLeft");
	}

	/**
	 * Gets frontRight Talon, with backRight Victor as follower.
	 * 
	 * @return frontRight Talon
	 */
	public TalonBuilder getRightTalon() {
		return talons.get("frontRight");
	}

	/**
	 * Sets leftTalon to the given speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void setLeftTalon(double speed) {
		talons.get("frontLeft").set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Sets rightTalon to the given speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void setRightTalon(double speed) {
		talons.get("frontRight").set(ControlMode.PercentOutput, speed);
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