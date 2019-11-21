/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Chassis;
import org.usfirst.frc6647.subsystems.NavX;
import org.usfirst.lib6647.subsystem.hypercomponents.HyperTalon;
import org.usfirst.lib6647.util.MoveDirection;

import edu.wpi.first.wpilibj.command.Command;

public class Rotate extends Command {

	private MoveDirection direction;
	private HyperTalon frontLeft, frontRight;
	private boolean wasEnabled;
	private double speed;

	public Rotate(MoveDirection direction, double speed) {
		this.direction = direction;
		this.speed = speed;

		frontLeft = Chassis.getInstance().getTalon("frontLeft");
		frontRight = Chassis.getInstance().getTalon("frontRight");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		wasEnabled = false;

		if (!NavX.getInstance().getPIDController().isEnabled())
			cancel();
		else {
			NavX.getInstance().disable();
			wasEnabled = true;

			switch (direction) {
			case LEFT:
				frontLeft.add(speed);
				frontRight.add(-speed);
				break;
			case RIGHT:
				frontLeft.add(-speed);
				frontRight.add(speed);
				break;
			default:
				end();
			}
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Sets motors to their current value so that 'added' speed is actually applied.
		frontLeft.set(frontLeft.get());
		frontRight.set(frontRight.get());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		// Subtract/add added speed.
		if (wasEnabled) {
			switch (direction) {
			case LEFT:
				frontLeft.add(-speed);
				frontRight.add(speed);
				break;
			case RIGHT:
				frontLeft.add(speed);
				frontRight.add(-speed);
				break;
			default:
			}

			// Wait 1 second for robot to stabilize.
			setTimeout(1);

			// Set PID setpoint to new (current) yaw.
			if (isTimedOut()) {
				double yaw = NavX.getInstance().getYaw();
				NavX.getInstance().getPIDController().setSetpoint(yaw);
			}

			frontLeft.stopMotor();
			frontRight.stopMotor();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}