/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Lift;
import org.usfirst.lib6647.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for manually moving Lift.
 */
public class MoveLiftManual extends Command {

	private Direction direction;

	/**
	 * Constructor for the command.
	 * 
	 * @param direction
	 */
	public MoveLiftManual(Direction direction) {
		requires(Lift.getInstance());

		this.direction = direction;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		switch (direction) {
		case UP:
			Lift.getInstance().setLift(0.6);
			break;
		case DOWN:
			Lift.getInstance().setLift(-0.3);
			break;
		default:
			end();
			break;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Lift.getInstance().stopLift();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
