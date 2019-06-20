/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.robot;

import org.usfirst.frc6647.commands.*;
import org.usfirst.frc6647.commands.MoveLiftPID.Height;
import org.usfirst.frc6647.commands.MoveLiftPID.Target;
import org.usfirst.frc6647.subsystems.Chassis;
import org.usfirst.frc6647.subsystems.ChassisH;
import org.usfirst.frc6647.subsystems.Intake;
import org.usfirst.frc6647.subsystems.Lift;
import org.usfirst.frc6647.subsystems.NavX;
import org.usfirst.frc6647.subsystems.TiltIntake;
import org.usfirst.lib6647.util.Direction;

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
		TiltIntake.createInstance();
		Lift.createInstance();

		OI.createInstance();
	}

	@Override
	public void robotPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		// Debug commands.
		SmartDashboard.putData("LiftBallLow", new MoveLiftPID(Target.CARGO, Height.LOW));
		SmartDashboard.putData("LiftBallShip", new MoveLiftPID(Target.CARGO, Height.SHIP));
		SmartDashboard.putData("LiftBallFloor", new MoveLiftPID(Target.CARGO, Height.FLOOR));
		SmartDashboard.putData("LiftBallMid", new MoveLiftPID(Target.CARGO, Height.MID));
		SmartDashboard.putData("LiftBallHigh", new MoveLiftPID(Target.CARGO, Height.HIGH));

		SmartDashboard.putData("LiftDownManual", new MoveLiftManual(Direction.DOWN));
		SmartDashboard.putData("TiltIntakeUpManual", new TiltIntakeManual(Direction.UP));
		SmartDashboard.putData("TiltIntakeDownManual", new TiltIntakeManual(Direction.DOWN));

		SmartDashboard.putData("PushHatch", new PushHatch(1.0));
		SmartDashboard.putData("ResetEncoders", new ResetEncoders());
		SmartDashboard.putData("LowerHatch", new MoveLiftPID(Target.HATCH, Height.FLOOR));
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