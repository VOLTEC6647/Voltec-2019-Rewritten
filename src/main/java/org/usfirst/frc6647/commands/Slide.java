/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.frc6647.subsystems.ChassisH;
import org.usfirst.lib6647.subsystem.hypercomponents.HyperTalon;
import org.usfirst.lib6647.util.MoveDirection;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for horizontal movement of the robot.
 */
public class Slide extends Command {

	private MoveDirection direction;
	private int leftAxisId, rightAxisId;
	private double leftAxis, rightAxis;
	private Joystick joystick;
	private boolean useAxes, startsAtZero;

	private double speed;
	private HyperTalon hWheel;

	/**
	 * Constructor for the command. You must specify the name of the joystick and
	 * speed you wish to go.
	 * 
	 * @param direction
	 * @param speed
	 */
	public Slide(MoveDirection direction, double speed) {
		requires(ChassisH.getInstance());

		this.direction = direction;
		this.speed = speed;

		useAxes = false;

		hWheel = ChassisH.getInstance().getTalon("hWheel");
	}

	/**
	 * Constructor for the command. You must specify left and right axes, the value
	 * the axes start at, and the speed you wish to go.
	 * 
	 * @param direction
	 * @param leftAxis
	 * @param rightAxis
	 * @param startsAtZero
	 * @param speed
	 */
	public Slide(MoveDirection direction, int leftAxis, int rightAxis, boolean startsAtZero, double speed) {
		requires(ChassisH.getInstance());

		this.direction = direction;
		this.startsAtZero = startsAtZero;
		this.speed = speed;

		leftAxisId = leftAxis;
		rightAxisId = rightAxis;

		useAxes = true;

		hWheel = ChassisH.getInstance().getTalon("hWheel");
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		joystick = OI.getInstance().getJoystick("driver1");

		switch (direction) {
		case LEFT:
			joystick.setRumble(RumbleType.kLeftRumble, 1);
			break;
		case RIGHT:
			joystick.setRumble(RumbleType.kRightRumble, 1);
			break;
		default:
			end();
			break;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (useAxes) {
			leftAxis = joystick.getRawAxis(leftAxisId);
			rightAxis = joystick.getRawAxis(rightAxisId);
		}

		switch (direction) {
		case LEFT:
			if (!useAxes)
				hWheel.set(speed);
			else if (!startsAtZero)
				hWheel.set(((leftAxis + 1) / 2) * speed);
			else
				hWheel.set(leftAxis * speed);
			break;
		case RIGHT:
			if (!useAxes)
				hWheel.set(-speed);
			else if (!startsAtZero)
				hWheel.set(((rightAxis + 1) / 2) * -speed);
			else
				hWheel.set(rightAxis * -speed);
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
		hWheel.stopMotor();
		joystick.setRumble(RumbleType.kLeftRumble, 0);
		joystick.setRumble(RumbleType.kRightRumble, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}