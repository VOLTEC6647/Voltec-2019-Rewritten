/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Intake;
import org.usfirst.lib6647.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for pushing/retracting the H.
 */
public class ControlH extends Command {

	Direction direction;

	/**
	 * Constructor for the command.
	 * 
	 * @param direction
	 */
	public ControlH(Direction direction) {
		requires(Intake.getInstance());
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		switch (direction) {
		case BACKWARD:
			Intake.getInstance().setH(false);
			break;
		case FORWARD:
			Intake.getInstance().setH(true);
			break;
		case TOGGLE:
			Intake.getInstance().toggleH();
		default:
			end();
			break;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Intake.getInstance().stopH();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
