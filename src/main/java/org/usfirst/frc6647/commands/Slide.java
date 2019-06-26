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
	private int leftAxis, rightAxis;
	private String joystickName;
	private boolean useAxes, startsAtZero;
	private double speed;

	/**
	 * Constructor for the command. You must specify the name of the joystick and
	 * speed you wish to go.
	 * 
	 * @param direction
	 * @param joystickName
	 * @param speed
	 */
	public Slide(Direction direction, String joystickName, double speed) {
		requires(ChassisH.getInstance());

		this.direction = direction;
		this.joystickName = joystickName;
		this.speed = speed;

		useAxes = false;
	}

	/**
	 * Constructor for the command. You must specify left and right axes, the value
	 * the axes start at, the name of the joystick, and the speed you wish to go.
	 * 
	 * @param direction
	 * @param leftAxis
	 * @param rightAxis
	 * @param startsAt
	 * @param joystickName
	 * @param speed
	 */
	public Slide(Direction direction, int leftAxis, int rightAxis, boolean startsAtZero, String joystickName,
			double speed) {
		requires(ChassisH.getInstance());

		this.direction = direction;
		this.leftAxis = leftAxis;
		this.rightAxis = rightAxis;
		this.startsAtZero = startsAtZero;
		this.joystickName = joystickName;
		this.speed = speed;

		useAxes = true;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		switch (direction) {
		case LEFT:
			if (!useAxes) {
				ChassisH.getInstance().moveHWheel(speed);
			} else if (!startsAtZero) {
				ChassisH.getInstance().moveHWheel(
						((OI.getInstance().joysticks.get(joystickName).getRawAxis(leftAxis) + 1) / 2) * speed);
			} else {
				ChassisH.getInstance()
						.moveHWheel(OI.getInstance().joysticks.get(joystickName).getRawAxis(leftAxis) * speed);
			}
			break;
		case RIGHT:
			if (!useAxes) {
				ChassisH.getInstance().moveHWheel(-speed);
			} else if (!startsAtZero) {
				ChassisH.getInstance().moveHWheel(
						((OI.getInstance().joysticks.get(joystickName).getRawAxis(rightAxis) + 1) / 2) * -speed);
			} else {
				ChassisH.getInstance()
						.moveHWheel(OI.getInstance().joysticks.get(joystickName).getRawAxis(rightAxis) * -speed);
			}
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
		ChassisH.getInstance().stopHWheel();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}