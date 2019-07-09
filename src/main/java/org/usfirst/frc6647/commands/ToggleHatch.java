/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for toggling the H solenoid.
 */
public class ToggleHatch extends Command {

	private String solenoidName;

	/**
	 * Constructor for the command.
	 * 
	 * @param solenoidName
	 */
	public ToggleHatch(String solenoidName) {
		this.solenoidName = solenoidName;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Intake.getInstance().getSolenoid(solenoidName).toggle();
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
