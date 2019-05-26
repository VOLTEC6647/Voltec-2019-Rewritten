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

import org.usfirst.lib6647.util.TalonBuilder;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem for the midwheel.
 */
public class ChassisH extends Subsystem {

	private final int TALON_HWHEEL_PORT = 3;
	private TalonBuilder hWheel;

	private final double RAMPDRIVE = 0.2;
	private final int LOOP = 1000, fpidIdx = 0, ftimeoutMs = 0, sensorPos = 0, pidIdx = 0, timeoutMs = 0, slotIdx = 0;
	private final boolean phase = true;
	private double p = 0.0, i = 0.0, d = 0.0, f = 0.0;

	private static ChassisH m_instance = null;

	/**
	 * Creates static ChassisH instance.
	 */
	public static void createInstance() {
		m_instance = new ChassisH();
	}

	/**
	 * Gets static ChassisH instance. If there is none, creates one.
	 * 
	 * @return static ChassisH instance
	 */
	public static ChassisH getInstance() {
		if (m_instance == null) {
			createInstance();
		}
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 * 
	 * Initializes the midwheel's talon with the values defined at the top of this
	 * class.
	 */
	public ChassisH() {
		hWheel = new TalonBuilder(TALON_HWHEEL_PORT, NeutralMode.Brake, true, RAMPDRIVE, LOOP,
				FeedbackDevice.QuadEncoder, fpidIdx, ftimeoutMs, phase, sensorPos, pidIdx, timeoutMs, slotIdx, p, i, d,
				f);
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Sets the midwheel to the given speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void moveHWheel(double speed) {
		hWheel.set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Stops the midwheel dead in its tracks.
	 */
	public void stopHWheel() {
		moveHWheel(0);
	}
}