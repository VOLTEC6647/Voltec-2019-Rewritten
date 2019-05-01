/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc6647.subsystems.NavX;
import org.usfirst.lib6647.util.Direction;

/**
 * Command for changing the Gyro alignment to the next desired angle.
 */
public class AlignNext extends Command {

	private Direction direction;
	private double yaw;

	private NavX navX = NavX.getInstance();

	/**
	 * Constructor for the command.
	 * 
	 * @param direction
	 */
	public AlignNext(Direction direction) {
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (!navX.getPIDController().isEnabled())
			end();

		yaw = navX.getPIDController().getSetpoint();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		switch (direction) {
		case LEFT:
			if (yaw == -180 || yaw == 180)
				navX.setSetpoint(151.25);
			else if (yaw == -151.25)
				navX.setSetpoint(-180);
			else if (yaw == -90)
				navX.setSetpoint(-151.25);
			else if (yaw == -28.75)
				navX.setSetpoint(-90);
			else if (yaw == 0)
				navX.setSetpoint(-28.75);
			else if (yaw == 28.75)
				navX.setSetpoint(0);
			else if (yaw == 90)
				navX.setSetpoint(28.75);
			else if (yaw == 151.25)
				navX.setSetpoint(90);
			break;
		case RIGHT:
			if (yaw == -180 || yaw == 180)
				navX.setSetpoint(-151.25);
			else if (yaw == -151.25)
				navX.setSetpoint(-90);
			else if (yaw == -90)
				navX.setSetpoint(-28.75);
			else if (yaw == -28.75)
				navX.setSetpoint(0);
			else if (yaw == 0)
				navX.setSetpoint(28.75);
			else if (yaw == 28.75)
				navX.setSetpoint(90);
			else if (yaw == 90)
				navX.setSetpoint(151.25);
			else if (yaw == 151.25)
				navX.setSetpoint(180);
			break;
		default:
			end();
		}
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