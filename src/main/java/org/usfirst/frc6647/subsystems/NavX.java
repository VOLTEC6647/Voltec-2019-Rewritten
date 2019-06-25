/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import com.kauailabs.navx.frc.AHRS;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the NavX sensor.
 */
public class NavX extends PIDSuperSubsystem {
	private AHRS ahrs;
	private double accel = 0.0, accelMult = 1.0, padLimiter = 0.6;

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
		super("navX", Filesystem.getDeployDirectory() + "/RobotMap.json");
		finishedJSONInit();

		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		SmartDashboard.putNumber("NavXYaw", ahrs.getYaw());
		SmartDashboard.putNumber("Goal", getPIDController().getSetpoint());
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
		double speed = (0.35 + (accel * accelMult)) * padLimiter;

		SmartDashboard.putNumber(getName() + "Output", output);

		if (OI.getInstance().oiButton(0, "dPadUp").get())
			Chassis.getInstance().setBothTalons(-speed + output, -speed - output);
		else if (OI.getInstance().oiButton(0, "dPadDown").get())
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
	 * Method to zero the NavX's yaw.
	 */
	public void zeroYaw() {
		ahrs.zeroYaw();
	}

	/**
	 * Sets padLimiter and acceleration.
	 * 
	 * @param padLimiter
	 * @param accel
	 */
	public void setPadLimiter(double padLimiter, boolean accel) {
		if (accel)
			accelMult = 1;
		else
			accelMult = 0;
		this.padLimiter = padLimiter;
	}

	/**
	 * Increases Gyro acceleration by a specified amount.
	 * 
	 * @param accel
	 */
	public void increaseAccel(double accel) {
		this.accel += accel;
	}

	/**
	 * Resets Gyro acceleration amount.
	 */
	public void resetAccel() {
		this.accel = 0;
	}
}