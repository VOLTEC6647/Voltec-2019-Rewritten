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

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for Robot alignment.
 */
public class GyroAlign extends Command {

	private HyperTalon frontLeft, frontRight;

	/**
	 * Constructor for the command.
	 * 
	 * Aligns the robot to the closest desired angle.
	 */
	public GyroAlign() {
		frontLeft = Chassis.getInstance().getTalon("frontLeft");
		frontRight = Chassis.getInstance().getTalon("frontRight");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		double yaw = NavX.getInstance().getYaw();

		if (-165.625 >= yaw)
			NavX.getInstance().setSetpoint(-180);
		else if (-120.625 >= yaw && yaw > -165.625)
			NavX.getInstance().setSetpoint(-151.25);
		else if (-59.375 >= yaw && yaw > -120.625)
			NavX.getInstance().setSetpoint(-90);
		else if (-14.375 >= yaw && yaw > -59.375)
			NavX.getInstance().setSetpoint(-28.75);
		else if (-14.375 < yaw && yaw < 14.375)
			NavX.getInstance().setSetpoint(0);
		else if (14.375 <= yaw && yaw < 59.375)
			NavX.getInstance().setSetpoint(28.75);
		else if (59.375 <= yaw && yaw < 120.625)
			NavX.getInstance().setSetpoint(90);
		else if (120.625 <= yaw && yaw < 165.625)
			NavX.getInstance().setSetpoint(151.25);
		else if (165.625 <= yaw)
			NavX.getInstance().setSetpoint(180);

		NavX.getInstance().updatePIDValues();

		if (!NavX.getInstance().getPIDController().isEnabled())
			NavX.getInstance().enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double pidOutput = NavX.getInstance().getPIDOutput();

		frontLeft.set(pidOutput);
		frontRight.set(pidOutput);
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