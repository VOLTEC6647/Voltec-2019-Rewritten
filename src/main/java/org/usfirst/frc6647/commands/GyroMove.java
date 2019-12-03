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
import org.usfirst.lib6647.subsystem.hypercomponents.HyperVictor;
import org.usfirst.lib6647.util.MoveDirection;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for moving straight with the gyro.
 */
public class GyroMove extends Command {

	private double yaw;
	private MoveDirection direction;
	private HyperVictor frontLeft;
	private HyperTalon frontRight;

	/**
	 * Constructor for the command.
	 * 
	 * @param direction
	 */
	public GyroMove(MoveDirection direction) {
		this.direction = direction;

		frontLeft = Chassis.getInstance().getVictor("frontLeft");
		frontRight = Chassis.getInstance().getTalon("frontRight");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		yaw = NavX.getInstance().getYaw();
		NavX.getInstance().getPIDController().setSetpoint(yaw);

		if (!NavX.getInstance().getPIDController().isEnabled())
			NavX.getInstance().enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double padLimiter = NavX.getInstance().getPadLimiter(), pidOutput = NavX.getInstance().getPIDOutput();

		switch (direction) {
		case FORWARD:
			frontLeft.set(-padLimiter + pidOutput);
			frontRight.set(-padLimiter - pidOutput);
			break;
		case BACKWARD:
			frontLeft.set(padLimiter + pidOutput);
			frontRight.set(padLimiter - pidOutput);
			break;
		default:
			end();
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
		NavX.getInstance().disable();
		frontLeft.stopMotor();
		frontRight.stopMotor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
