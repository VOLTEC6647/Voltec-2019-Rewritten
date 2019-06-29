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
	private boolean useAxes, startsAtZero;
	private Joystick joystick;
	private double speed;
	private HyperTalon hWheel;

	/**
	 * Constructor for the command. You must specify the name of the joystick and
	 * speed you wish to go.
	 * 
	 * @param direction
	 * @param joystickName
	 * @param speed
	 */
	public Slide(MoveDirection direction, String joystickName, double speed) {
		requires(ChassisH.getInstance());

		this.direction = direction;
		this.speed = speed;

		joystick = OI.getInstance().joysticks.get(joystickName);

		useAxes = false;
	}

	/**
	 * Constructor for the command. You must specify left and right axes, the value
	 * the axes start at, the name of the joystick, and the speed you wish to go.
	 * 
	 * @param direction
	 * @param leftAxis
	 * @param rightAxis
	 * @param startsAtZero
	 * @param joystickName
	 * @param speed
	 */
	public Slide(MoveDirection direction, int leftAxis, int rightAxis, boolean startsAtZero, String joystickName,
			double speed) {
		requires(ChassisH.getInstance());

		this.direction = direction;
		this.startsAtZero = startsAtZero;
		this.speed = speed;

		joystick = OI.getInstance().joysticks.get(joystickName);
		leftAxisId = leftAxis;
		rightAxisId = rightAxis;

		useAxes = true;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		switch (direction) {
		case LEFT:
			joystick.setRumble(RumbleType.kLeftRumble, 1);
			break;
		case RIGHT:
			joystick.setRumble(RumbleType.kRightRumble, 1);
			break;
		default:
			end();
		}
		
		hWheel = ChassisH.getInstance().getTalon("hWheel");

		if (useAxes) {
			leftAxis = joystick.getRawAxis(leftAxisId);
			rightAxis = joystick.getRawAxis(rightAxisId);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

		switch (direction) {
		case LEFT:
			if (!useAxes)
				hWheel.setTalon(speed);
			else if (!startsAtZero)
				hWheel.setTalon(((leftAxis + 1) / 2) * speed);
			else
				hWheel.setTalon(leftAxis * speed);
			break;
		case RIGHT:
			if (!useAxes)
				hWheel.setTalon(-speed);
			else if (!startsAtZero)
				hWheel.setTalon(((rightAxis + 1) / 2) * -speed);
			else
				hWheel.setTalon(rightAxis * -speed);
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
		hWheel.stopTalon();
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