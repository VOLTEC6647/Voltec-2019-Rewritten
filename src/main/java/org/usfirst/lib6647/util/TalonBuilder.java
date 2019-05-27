/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.lib6647.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * Helper class for initializing talons.
 */
public class TalonBuilder extends WPI_TalonSRX {

	private WPI_VictorSPX victor;

	/**
	 * Creates and initializes a Talon with the given values.
	 * 
	 * @param port
	 * @param talonName
	 * @param neutralMode
	 * @param inverted
	 * @param RAMPDRIVE
	 * @param LOOP
	 * @param feedbackDevice
	 * @param fpidIdx
	 * @param ftimeoutMs
	 * @param phase
	 * @param sensorPos
	 * @param pidIdx
	 * @param timeoutMs
	 * @param slotIdx
	 * @param p
	 * @param i
	 * @param d
	 * @param f
	 */
	public TalonBuilder(int port, String talonName, NeutralMode neutralMode, boolean inverted, double RAMPDRIVE,
			int LOOP, FeedbackDevice feedbackDevice, int fpidIdx, int ftimeoutMs, boolean phase, int sensorPos,
			int pidIdx, int timeoutMs, int slotIdx, float p, float i, float d, float f) {
		super(port);

		createTalon(talonName, neutralMode, inverted, RAMPDRIVE, LOOP, feedbackDevice, fpidIdx, ftimeoutMs, phase,
				sensorPos, pidIdx, timeoutMs, slotIdx, p, i, d, f);
	}

	/**
	 * Creates and initializes a Talon and a follower Victor with the given values.
	 * 
	 * @param talonPort
	 * @param talonName
	 * @param victorPort
	 * @param victorName
	 * @param neutralMode
	 * @param inverted
	 * @param RAMPDRIVE
	 * @param LOOP
	 * @param feedbackDevice
	 * @param fpidIdx
	 * @param ftimeoutMs
	 * @param phase
	 * @param sensorPos
	 * @param pidIdx
	 * @param timeoutMs
	 * @param slotIdx
	 * @param p
	 * @param i
	 * @param d
	 * @param f
	 */
	public TalonBuilder(int talonPort, String talonName, int victorPort, String victorName, NeutralMode neutralMode,
			boolean inverted, double RAMPDRIVE, int LOOP, FeedbackDevice feedbackDevice, int fpidIdx, int ftimeoutMs,
			boolean phase, int sensorPos, int pidIdx, int timeoutMs, int slotIdx, float p, float i, float d, float f) {
		super(talonPort);

		createTalon(talonName, neutralMode, inverted, RAMPDRIVE, LOOP, feedbackDevice, fpidIdx, ftimeoutMs, phase,
				sensorPos, pidIdx, timeoutMs, slotIdx, p, i, d, f);

		victor = new WPI_VictorSPX(victorPort);
		victor.setName(victorName);
		victor.setInverted(inverted);
		victor.setNeutralMode(neutralMode);
		victor.follow(this);
	}

	/**
	 * Method to initialize a Talon with the given values.
	 * 
	 * @param talonName
	 * @param neutralMode
	 * @param inverted
	 * @param RAMPDRIVE
	 * @param LOOP
	 * @param feedbackDevice
	 * @param fpidIdx
	 * @param ftimeoutMs
	 * @param phase
	 * @param sensorPos
	 * @param pidIdx
	 * @param timeoutMs
	 * @param slotIdx
	 * @param p
	 * @param i
	 * @param d
	 * @param f
	 */
	private void createTalon(String talonName, NeutralMode neutralMode, boolean inverted, double RAMPDRIVE, int LOOP,
			FeedbackDevice feedbackDevice, int fpidIdx, int ftimeoutMs, boolean phase, int sensorPos, int pidIdx,
			int timeoutMs, int slotIdx, float p, float i, float d, float f) {
		setName(talonName);
		setNeutralMode(neutralMode);
		setInverted(inverted);

		configLoops(RAMPDRIVE, LOOP);
		configSensors(feedbackDevice, fpidIdx, ftimeoutMs, phase, sensorPos, pidIdx, timeoutMs);
		configPIDs(slotIdx, p, i, d, f);

		set(ControlMode.PercentOutput, 0);
	}

	/**
	 * Method to configure Talon loops.
	 * 
	 * @param RAMPDRIVE
	 * @param LOOP
	 */
	private void configLoops(double RAMPDRIVE, int LOOP) {
		configClosedloopRamp(RAMPDRIVE, LOOP);
		configOpenloopRamp(RAMPDRIVE, LOOP);
	}

	/**
	 * Method to configure Talon sensors.
	 * 
	 * @param feedbackDevice
	 * @param fpidIdx
	 * @param ftimeoutMs
	 * @param phase
	 * @param sensorPos
	 * @param pidIdx
	 * @param timeoutMs
	 */
	private void configSensors(FeedbackDevice feedbackDevice, int fpidIdx, int ftimeoutMs, boolean phase, int sensorPos,
			int pidIdx, int timeoutMs) {
		configSelectedFeedbackSensor(feedbackDevice, fpidIdx, ftimeoutMs);
		setSensorPhase(phase);
		setSelectedSensorPosition(sensorPos, pidIdx, timeoutMs);
	}

	/**
	 * Method to configure Talon PID values.
	 * 
	 * @param slotIdx
	 * @param p
	 * @param i
	 * @param d
	 * @param f
	 */
	private void configPIDs(int slotIdx, float p, float i, float d, float f) {
		config_kP(slotIdx, p);
		config_kI(slotIdx, i);
		config_kD(slotIdx, d);
		config_kF(slotIdx, f);
	}

	/**
	 * Returns follower Victor.
	 * 
	 * @return WPI_VictorSPX follower
	 */
	public WPI_VictorSPX getFollower() {
		return victor;
	}
}