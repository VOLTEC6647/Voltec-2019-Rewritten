/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc6647.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.command.Command;

public class GeneratePIDData extends Command {

	private PIDSuperSubsystem subsystem;
	private CSVWriter writer;
	private double start;

	public GeneratePIDData(PIDSuperSubsystem subsystem) {
		this.subsystem = subsystem;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		try {
			writer = new CSVWriter(new FileWriter(
					new File(Filesystem.getOperatingDirectory() + "/" + subsystem.getName() + "PIDData.csv")));

			writer.writeNext(new String[] { "Time", "Input", "Output" });
			start = (System.nanoTime() / 1000000);
		} catch (IOException e) {
			e.printStackTrace();
			end();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		writer.writeNext(new String[] { ((System.nanoTime() / 1000000) - start) + "", subsystem.getSetpoint() + "",
				subsystem.getPIDOutput() + "" });
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
