/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Climb;
import org.usfirst.lib6647.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

public class ClimbHab extends Command {

	Direction direction;

	public ClimbHab(Direction direction) {
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		switch (direction) {
		case FRONT:
			Climb.getInstance().setFront(true);
			break;
		case BACK:
			Climb.getInstance().setBack(true);
			break;
		default:
			end();
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
		switch (direction) {
		case FRONT:
			Climb.getInstance().setFront(false);
			break;
		case BACK:
			Climb.getInstance().setBack(false);
			break;
		default:
			break;
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
