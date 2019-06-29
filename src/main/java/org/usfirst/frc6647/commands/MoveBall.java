/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Intake;
import org.usfirst.lib6647.subsystem.hypercomponents.HyperVictor;
import org.usfirst.lib6647.util.MoveDirection;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for grabbing/releasing Cargo balls.
 */
public class MoveBall extends Command {

	private double speed;
	private MoveDirection direction;
	private HyperVictor intakeLeft, intakeRight;

	/**
	 * Constructor for the command.
	 * 
	 * @param direction
	 * @param speed
	 */
	public MoveBall(MoveDirection direction, double speed, String leftName, String rightName) {
		requires(Intake.getInstance());

		intakeLeft = Intake.getInstance().getVictor(leftName);
		intakeRight = Intake.getInstance().getVictor(rightName);

		this.direction = direction;
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		switch (direction) {
		case IN:
			intakeLeft.setVictor(speed);
			intakeRight.setVictor(speed);
			break;
		case OUT:
			intakeLeft.setVictor(-speed);
			intakeRight.setVictor(-speed);
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
		intakeLeft.stopVictor();
		intakeRight.stopVictor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
