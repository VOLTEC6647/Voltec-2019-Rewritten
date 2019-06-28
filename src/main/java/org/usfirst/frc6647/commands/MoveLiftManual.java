/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Lift;
import org.usfirst.lib6647.util.MoveDirection;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for manually moving Lift.
 */
public class MoveLiftManual extends Command {

	private MoveDirection direction;

	/**
	 * Constructor for the command.
	 * 
	 * @param direction
	 */
	public MoveLiftManual(MoveDirection direction) {
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
			Lift.getInstance().getVictor("liftMain").setVictor(0.6, false);
			break;
		case DOWN:
			if (Lift.getInstance().getDigitalInput("lowLimitLift").get())
				end();
			else
				Lift.getInstance().getVictor("liftMain").setVictor(-0.3, false);
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
		Lift.getInstance().getVictor("liftMain").stopVictor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
