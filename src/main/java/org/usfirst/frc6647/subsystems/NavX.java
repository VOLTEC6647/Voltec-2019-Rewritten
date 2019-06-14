/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.lib6647.util.PID;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the NavX sensor.
 */
public class NavX extends PIDSubsystem implements PID {

	private double p = 0.0, i = 0.0, d = 0.0;
	private AHRS ahrs;

	public double acceleration, accelerationMultiplier = 1.0, padLimiter = 0.6;

	private static NavX m_instance = null;

	/**
	 * Creates static NavX instance.
	 */
	public static void createInstance() {
		m_instance = new NavX();
	}

	/**
	 * Gets static NavX instance. If there is none, creates one.
	 * 
	 * @return static NavX instance
	 */
	public static NavX getInstance() {
		if (m_instance == null) {
			createInstance();
		}
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 * 
	 * Initializes PID subsystem and resets NavX sensor.
	 */
	public NavX() {
		super("NavX", 0, 0, 0);

		getPIDController().setPID(p, i, d);

		setInputRange(-180, 180);
		setOutputRange(-0.70, 0.70);
		setAbsoluteTolerance(0.5);
		getPIDController().setContinuous(true);

		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();

		outputPIDValues(getName(), p, i, d);
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		updatePIDValues();
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Gets input for PID.
	 * 
	 * @return pidInput
	 */
	@Override
	protected double returnPIDInput() {
		return getYaw();
	}

	/**
	 * Adds or substracts calculated speed to its respective Talon.
	 */
	@Override
	protected void usePIDOutput(double output) {
		int angle = OI.getInstance().joysticks.get(0).getPOV(0);
		double speed = 0.5 + (acceleration * accelerationMultiplier) * padLimiter;

		if (angle == 0)
			Chassis.getInstance().setBothTalons(-speed + output, -speed - output);
		else if (angle == 180)
			Chassis.getInstance().setBothTalons(speed + output, speed - output);
		else
			Chassis.getInstance().setBothTalons(output, -output);
	}

	/**
	 * Gets current yaw from NavX.
	 * 
	 * @return currentYaw
	 */
	public double getYaw() {
		return ahrs.getYaw();
	}

	/**
	 * Method to update PID values from the SmartDashboard.
	 */
	public void updatePIDValues() {
		p = SmartDashboard.getNumber(getName() + "P", p);
		i = SmartDashboard.getNumber(getName() + "I", i);
		d = SmartDashboard.getNumber(getName() + "D", d);
	}
}