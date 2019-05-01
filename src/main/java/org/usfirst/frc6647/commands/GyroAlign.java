/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import org.usfirst.frc6647.robot.OI;
import org.usfirst.frc6647.subsystems.Chassis;
import org.usfirst.frc6647.subsystems.NavX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroAlign extends Command {

	private Chassis chassis = Chassis.getInstance();
	private OI oi = OI.getInstance();
	private NavX navX = NavX.getInstance();

	public GyroAlign() {
		requires(chassis);
		requires(navX);
		double yaw = navX.getYaw();

		if (-165.625 >= yaw)
			navX.setSetpoint(-180);
		else if (-120.625 >= yaw && yaw > -165.625)
			navX.setSetpoint(-151.25);
		else if (-59.375 >= yaw && yaw > -120.625)
			navX.setSetpoint(-90);
		else if (-14.375 >= yaw && yaw > -59.375)
			navX.setSetpoint(-28.75);
		else if (-14.375 < yaw && yaw < 14.375)
			navX.setSetpoint(0);
		else if (14.375 <= yaw && yaw < 59.375)
			navX.setSetpoint(28.75);
		else if (59.375 <= yaw && yaw < 120.625)
			navX.setSetpoint(90);
		else if (120.625 <= yaw && yaw < 165.625)
			navX.setSetpoint(151.25);
		else if (165.625 <= yaw)
			navX.setSetpoint(180);
	}

	public GyroAlign(double angle) {
		requires(chassis);
		requires(navX);
		navX.setSetpoint(angle);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		SmartDashboard.putBoolean("Gyro", true);
		navX.acceleration = 0.0;
		navX.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Math.abs(oi.joysticks.get(1).getRawAxis(1)) > 0.1 && Math.abs(oi.joysticks.get(1).getRawAxis(5)) > 0.1)
			end();

		navX.acceleration += 0.0035;
		navX.updatePIDValues();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		SmartDashboard.putBoolean("Gyro", false);
		navX.acceleration = 0.0;
		navX.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}