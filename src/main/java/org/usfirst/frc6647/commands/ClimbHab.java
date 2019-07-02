/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Climb;
import org.usfirst.lib6647.util.MoveDirection;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;

public class ClimbHab extends Command {

	MoveDirection direction;
	private Solenoid frontSolenoidForward, frontSolenoidBackward, backSolenoidForward, backSolenoidBackward;
	private String frontSolenoidForwardName, frontSolenoidBackwardName, backSolenoidForwardName,
			backSolenoidBackwardName;

	public ClimbHab(MoveDirection direction, String frontSolenoidForward, String frontSolenoidBackward,
			String backSolenoidForward, String backSolenoidBackward) {
		this.direction = direction;

		this.frontSolenoidForwardName = frontSolenoidForward;
		this.frontSolenoidBackwardName = frontSolenoidBackward;

		this.backSolenoidForwardName = backSolenoidForward;
		this.backSolenoidBackwardName = backSolenoidBackward;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		this.frontSolenoidForward = Climb.getInstance().getSolenoid(frontSolenoidForwardName);
		this.frontSolenoidBackward = Climb.getInstance().getSolenoid(frontSolenoidBackwardName);

		this.backSolenoidForward = Climb.getInstance().getSolenoid(backSolenoidForwardName);
		this.backSolenoidBackward = Climb.getInstance().getSolenoid(backSolenoidBackwardName);

		switch (direction) {
		case FRONT:
			frontSolenoidForward.set(true);
			frontSolenoidBackward.set(false);
			break;
		case BACK:
			backSolenoidForward.set(true);
			backSolenoidBackward.set(false);
			break;
		default:
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
		switch (direction) {
		case FRONT:
			frontSolenoidForward.set(false);
			frontSolenoidBackward.set(true);
			break;
		case BACK:
			backSolenoidForward.set(false);
			backSolenoidBackward.set(true);
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
