/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.frc6647.subsystems.ChassisH;
import org.usfirst.lib6647.util.Direction;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for horizontal movement of the robot.
 */
public class Slide extends Command {

	private Direction direction;
	private double analogLT, analogRT;

	private OI oi = OI.getInstance();
	private ChassisH chassisH = ChassisH.getInstance();

	/**
	 * Constructor for the command. Requires the chassisH subsystem.
	 * 
	 * @param direction
	 */
	public Slide(Direction direction) {
		requires(chassisH);
		this.direction = direction;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		analogLT = (oi.joysticks.get(1).getRawAxis(3) + 1) / 2;
		analogRT = (oi.joysticks.get(1).getRawAxis(4) + 1) / 2;

		switch (direction) {
		case LEFT:
			chassisH.moveHWheel(analogLT * 0.7);
			break;
		case RIGHT:
			chassisH.moveHWheel(-analogRT * 0.7);
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
		chassisH.stopHWheel();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}