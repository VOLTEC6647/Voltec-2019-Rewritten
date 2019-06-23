/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.components.SuperTalon;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Subsystem for the hWheel.
 */
public class ChassisH extends SuperSubsystem implements SuperTalon {

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
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public ChassisH() {
		super("chassisH", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initTalons(robotMap, getName());

		finishedJSONInit();
	}

	@Override
	public void initDefaultCommand() {
	}

	/**
	 * Sets the hWheel Talon to the given speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void moveHWheel(double speed) {
		talons.get("hWheel").set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Stops the hWheel Talon dead in its tracks.
	 */
	public void stopHWheel() {
		moveHWheel(0);
	}
}