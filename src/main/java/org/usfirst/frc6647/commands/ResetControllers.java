/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.lib6647.oi.JController;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Creates a new {@link OI} instance, effectively refreshing {@link JController
 * Controllers} without having to reset the entire robot.
 */
public class ResetControllers extends Command {
	
	public ResetControllers() {
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.exit(1);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
