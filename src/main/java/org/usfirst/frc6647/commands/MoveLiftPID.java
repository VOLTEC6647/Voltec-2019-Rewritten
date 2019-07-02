/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.subsystems.Lift;
import org.usfirst.lib6647.subsystem.hypercomponents.HyperVictor;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for moving Lift with PID.
 */
public class MoveLiftPID extends Command {

	private Encoder liftEncoder;
	private HyperVictor liftMain;
	private String encoderName, victorName;

	/**
	 * Enum listing possible PID targets.
	 */
	public enum Target {
		CARGO, HATCH
	}

	/**
	 * Enum listing possible PID target heights.
	 */
	public enum Height {
		LOW, MID, HIGH, SHIP, FLOOR
	}

	private Target target;
	private Height height;

	/**
	 * Constructor for the command.
	 * 
	 * @param target
	 * @param height
	 * @param victorName
	 * @param encoderName
	 */
	public MoveLiftPID(Target target, Height height, String victorName, String encoderName) {
		requires(Lift.getInstance());

		this.target = target;
		this.height = height;
		this.victorName = victorName;
		this.encoderName = encoderName;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		liftEncoder = Lift.getInstance().getEncoder(encoderName);
		liftMain = Lift.getInstance().getVictor(victorName);

		switch (target) {
		case CARGO:
			switch (height) {
			case FLOOR:
				Lift.getInstance().setSetpoint(135000);
				break;
			case LOW:
				Lift.getInstance().setSetpoint(235000);
				break;
			case MID:
				Lift.getInstance().setSetpoint(260000);
				break;
			case SHIP:
				Lift.getInstance().setSetpoint(310000);
				break;
			case HIGH:
				Lift.getInstance().setSetpoint(385000);
				break;
			default:
				end();
				break;
			}
			break;
		case HATCH:
			switch (height) {
			case LOW:
				Lift.getInstance().setSetpoint(20000);
				break;
			case MID:
				Lift.getInstance().setSetpoint(230000);
				break;
			case SHIP:
				Lift.getInstance().setSetpoint(310000);
				break;
			case HIGH:
				Lift.getInstance().setSetpoint(350000);
				break;
			case FLOOR:
				Lift.getInstance().setSetpoint(liftEncoder.get() - 20000);
				break;
			default:
				end();
				break;
			}
			break;
		default:
			end();
			break;
		}

		Lift.getInstance().updatePIDValues();
		Lift.getInstance().enable();
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
		Lift.getInstance().disable();
		liftMain.stopVictor();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
