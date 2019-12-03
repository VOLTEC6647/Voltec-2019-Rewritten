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
 * Command to change Chassis velocity limiters.
 */
public class ChangeVelocity extends Command {

	private HyperTalon frontLeft, frontRight;
	private double driveLimiter, padLimiter;

	/**
	 * Constructor for the command.
	 * 
	 * @param driveLimiter
	 * @param padLimiter
	 */
	public ChangeVelocity(double driveLimiter, double padLimiter) {
		this.driveLimiter = driveLimiter;
		this.padLimiter = padLimiter;

		frontLeft = Chassis.getInstance().getTalon("frontLeft");
		frontRight = Chassis.getInstance().getTalon("frontRight");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		frontLeft.setLimiter(driveLimiter);
		frontRight.setLimiter(driveLimiter);

		NavX.getInstance().setPadLimiter(padLimiter);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("Changed driveLimiter to: " + driveLimiter);
		System.out.println("Changed padLimiter to: " + padLimiter);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
