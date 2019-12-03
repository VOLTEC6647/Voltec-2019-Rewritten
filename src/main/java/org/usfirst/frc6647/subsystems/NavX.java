/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;

import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the NavX sensor.
 */
public class NavX extends PIDSuperSubsystem {

	private AHRS ahrs;
	private double padLimiter = 0.4;

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
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 * 
	 * Initializes PID subsystem and resets NavX sensor.
	 */
	public NavX() {
		super("navX");

		ahrs = new AHRS(Port.kUSB);
		ahrs.reset();
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		SmartDashboard.putNumber("NavXYaw", getYaw());
		SmartDashboard.putNumber("Goal", getPIDController().getSetpoint());

		if (!getPIDController().isEnabled())
			pidOutput = 0.0;
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
	 * 
	 * @param PIDoutput
	 */
	@Override
	protected void usePIDOutput(double output) {
		SmartDashboard.putNumber(getName() + "Output", output);
		pidOutput = output;
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
	 * Method to zero the NavX's yaw.
	 */
	public void zeroYaw() {
		ahrs.zeroYaw();
	}

	/**
	 * Sets padLimiter value.
	 * 
	 * @param padLimiter
	 */
	public void setPadLimiter(double padLimiter) {
		this.padLimiter = padLimiter;
	}

	/**
	 * Gets padLimiter value.
	 * 
	 * @return
	 */
	public double getPadLimiter() {
		return padLimiter;
	}
}