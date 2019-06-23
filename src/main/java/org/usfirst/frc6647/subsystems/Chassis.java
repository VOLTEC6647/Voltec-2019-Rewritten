/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import java.util.function.Function;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.components.SuperCompressor;
import org.usfirst.lib6647.subsystem.components.SuperPDP;
import org.usfirst.lib6647.subsystem.components.SuperTalon;
import org.usfirst.lib6647.subsystem.components.SuperUltrasonic;
import org.usfirst.lib6647.subsystem.components.SuperVictor;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Subsystem for the Chassis.
 */
public class Chassis extends SuperSubsystem
		implements SuperTalon, SuperVictor, SuperUltrasonic, SuperCompressor, SuperPDP {

	private double joyTolerance = 0.15, driveLimiter = 0.75;

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
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem. Initializes Talons and Victors, and sets the
	 * latter to follow its respective Talon.
	 */
	public Chassis() {
		super("chassis", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initTalons(robotMap, getName());
		initVictors(robotMap, getName());
		initUltrasonics(robotMap, getName());
		initCompressors(robotMap, getName());
		initPDPs(robotMap, getName());

		victors.get("backLeft").follow(talons.get("frontLeft"));
		victors.get("backRight").follow(talons.get("frontRight"));
	}

	/**
	 * Lambda for joystick mapping.
	 */
	private Function<Double, Double> mapDoubleT = x -> Math.abs(x) < joyTolerance ? 0
			: x < 0 ? (x + joyTolerance) / (1 - joyTolerance) : (x - joyTolerance) / (1 - joyTolerance);

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		if (!NavX.getInstance().getPIDController().isEnabled()) {
			double leftStickY = mapDoubleT.apply(OI.getInstance().joysticks.get(0).getRawAxis(1)),
					rightStickY = mapDoubleT.apply(OI.getInstance().joysticks.get(0).getRawAxis(5));
			Chassis.getInstance().setBothTalons(leftStickY, rightStickY);
		}
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Gets frontLeft Talon, with backLeft Victor as follower.
	 * 
	 * @return frontLeft
	 */
	public WPI_TalonSRX getLeftTalon() {
		return talons.get("frontLeft");
	}

	/**
	 * Gets frontRight Talon, with backRight Victor as follower.
	 * 
	 * @return frontRight
	 */
	public WPI_TalonSRX getRightTalon() {
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
	 * Sets both Talons to the given speed, in PercentOutput.
	 * 
	 * @param leftSpeed
	 * @param rightSpeed
	 */
	public void setBothTalons(double leftSpeed, double rightSpeed) {
		setLeftTalon(leftSpeed * driveLimiter);
		setRightTalon(rightSpeed * driveLimiter);
	}

	/**
	 * Stops both Talons dead in their tracks.
	 */
	public void stopTalons() {
		setBothTalons(0, 0);
	}

	/**
	 * Set driveLimiter.
	 * 
	 * @param driveLimiter
	 */
	public void setDriveLimiter(double driveLimiter) {
		this.driveLimiter = driveLimiter;
	}
}