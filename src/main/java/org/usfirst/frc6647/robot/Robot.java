/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import org.usfirst.frc6647.commands.ResetEncoders;
import org.usfirst.frc6647.commands.ZeroYaw;
import org.usfirst.frc6647.subsystems.Chassis;
import org.usfirst.frc6647.subsystems.ChassisH;
import org.usfirst.frc6647.subsystems.Climb;
import org.usfirst.frc6647.subsystems.Intake;
import org.usfirst.frc6647.subsystems.Lift;
import org.usfirst.frc6647.subsystems.NavX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		Chassis.createInstance();
		ChassisH.createInstance();
		NavX.createInstance();
		Intake.createInstance();
		Lift.createInstance();
		Climb.createInstance();

		OI.createInstance();
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void disabledInit() {
		Climb.getInstance().getDoubleSolenoid("frontSolenoid").stop();
		Climb.getInstance().getDoubleSolenoid("backSolenoid").stop();
	}

	@Override
	public void autonomousInit() {
		NavX.getInstance().zeroYaw();
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		// Debug commands.
		SmartDashboard.putData("ResetEncoders", new ResetEncoders("liftEncoder"));
		SmartDashboard.putData("ZeroYaw", new ZeroYaw());
	}

	@Override
	public void teleopPeriodic() {
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}
}