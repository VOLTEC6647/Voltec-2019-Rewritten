/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.frc6647.subsystems.Intake;
import org.usfirst.lib6647.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for manually controlling Intake tilt.
 */
public class TiltIntakeManual extends Command {

	private Direction direction;

	/**
	 * Constructor for the command.
	 * 
	 * @param direction
	 */
	public TiltIntakeManual(Direction direction) {
		requires(Intake.getInstance());

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
			Intake.getInstance().setTiltIntake(((OI.getInstance().joysticks.get(1).getRawAxis(4) + 1) / 2) * 0.8);
			break;
		case DOWN:
			Intake.getInstance().setTiltIntake(((OI.getInstance().joysticks.get(1).getRawAxis(3) + 1) / 2) * -0.6);
			break;
		case NONE:
			if (OI.getInstance().joysticks.get(1).getRawAxis(4) > 0)
				Intake.getInstance().setTiltIntake(OI.getInstance().joysticks.get(1).getRawAxis(4) * 0.8);
			else if (OI.getInstance().joysticks.get(1).getRawAxis(4) < 0)
				Intake.getInstance().setTiltIntake(OI.getInstance().joysticks.get(1).getRawAxis(4) * 0.6);
			else
				Intake.getInstance().stopTiltIntake();
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
		Intake.getInstance().stopTiltIntake();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
