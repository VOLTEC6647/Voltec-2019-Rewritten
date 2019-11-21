/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Climb;
import org.usfirst.lib6647.subsystem.hypercomponents.HyperDoubleSolenoid;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for extending/retracting the back piston.
 */
public class HabClimb extends Command {

	private HyperDoubleSolenoid solenoid;

	/**
	 * Constructor for the command.
	 * 
	 * @param solenoidName
	 */
	public HabClimb(String solenoidName) {
		solenoid = Climb.getInstance().getDoubleSolenoid(solenoidName);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		solenoid.set(true);
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
		solenoid.set(false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
