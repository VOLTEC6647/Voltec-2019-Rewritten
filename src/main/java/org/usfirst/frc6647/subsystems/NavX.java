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
import org.usfirst.lib6647.subsystem.hypercomponents.HyperTalon;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the NavX sensor.
 */
public class NavX extends PIDSuperSubsystem {
	private AHRS ahrs;
	private double padLimiter = 0.4, rotate = 0;

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

		ahrs = new AHRS(SPI.Port.kMXP);
		ahrs.reset();
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		SmartDashboard.putNumber("NavXYaw", getYaw());
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
		SmartDashboard.putNumber(getName() + "Output", output);

		HyperTalon frontLeft = Chassis.getInstance().getTalon("frontLeft"),
				frontRight = Chassis.getInstance().getTalon("frontRight");

		if (OI.getInstance().getJoystick("driver1").get("dPadUp").get()) {
			frontLeft.set(-padLimiter + (rotate == 0 ? output : rotate));
			frontRight.set(-padLimiter - (rotate == 0 ? output : rotate));
		} else if (OI.getInstance().getJoystick("driver1").get("dPadDown").get()) {
			frontLeft.set(padLimiter + (rotate == 0 ? output : rotate));
			frontRight.set(padLimiter - (rotate == 0 ? output : rotate));
		} else {
			frontLeft.set(rotate == 0 ? output : rotate);
			frontRight.set(rotate == 0 ? -output : -rotate);
		}

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
	 * @param accel
	 */
	public void setPadLimiter(double padLimiter) {
		this.padLimiter = padLimiter;
	}

	/**
	 * Sets the speed at which the robot rotates.
	 * 
	 * @param speed
	 */
	public void setRotation(double speed) {
		this.rotate = speed;
	}
}