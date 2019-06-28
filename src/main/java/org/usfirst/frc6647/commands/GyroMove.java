/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.NavX;

import edu.wpi.first.wpilibj.command.Command;

public class GyroMove extends Command {

	private double yaw;

	public GyroMove() {
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		yaw = NavX.getInstance().getYaw();
		NavX.getInstance().getPIDController().setSetpoint(yaw);

		NavX.getInstance().resetAccel();

		if (!NavX.getInstance().getPIDController().isEnabled())
			NavX.getInstance().enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		NavX.getInstance().increaseAccel(0.0035);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		NavX.getInstance().resetAccel();
		NavX.getInstance().disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
