package org.usfirst.lib6647.subsystem;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class to allow usage of JSON files for PIDSubsystem creation.
 */
public abstract class PIDSuperSubsystem extends PIDSubsystem {

	protected JSONObject robotMap;
	protected float p = 0.0f, i = 0.0f, d = 0.0f;

	/**
	 * Constructor for the class.
	 * 
	 * @param name
	 * @param fileName (of JSON)
	 */
	public PIDSuperSubsystem(String name, String fileName) {
		super(name, 0.0f, 0.0f, 0.0f);

		initJSON(fileName);
		initPID();
		outputPIDValues(getName(), p, i, d);
	}

	/**
	 * Method to initialize JSON at the given path.
	 * 
	 * @param fileName
	 */
	private void initJSON(String fileName) {
		try {
			JSONParser parser = new JSONParser();
			Reader file = new FileReader(fileName);
			robotMap = (JSONObject) parser.parse(file);
			file.close();
		} catch (Exception e) {
			DriverStation.reportError(
					"[!] SUBSYSTEM '" + getName().toUpperCase() + "' JSON INIT ERROR: " + e.getMessage(), false);
			System.out.println("[!] SUBSYSTEM '" + getName().toUpperCase() + "' JSON INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Method to initialize subsystem PID values and configuration.
	 */
	private void initPID() {
		try {
			JSONObject pid = (JSONObject) ((JSONObject) ((JSONObject) robotMap.get("subsystems")).get(getName()))
					.get("pid");

			p = Float.parseFloat(pid.get("p").toString());
			i = Float.parseFloat(pid.get("i").toString());
			d = Float.parseFloat(pid.get("d").toString());

			getPIDController().setPID(p, i, d);
			setInputRange(Double.parseDouble(pid.get("inputMin").toString()),
					Double.parseDouble(pid.get("inputMax").toString()));
			setOutputRange(Double.parseDouble(pid.get("outputMin").toString()),
					Double.parseDouble(pid.get("outputMax").toString()));
			setAbsoluteTolerance(Double.parseDouble(pid.get("absoluteTolerance").toString()));
			getPIDController().setContinuous(Boolean.parseBoolean(pid.get("continuous").toString()));

			pid.clear();
		} catch (Exception e) {
			DriverStation.reportError(
					"[!] SUBSYSTEM '" + getName().toUpperCase() + "' PID INIT ERROR: " + e.getMessage(), false);
			System.out.println("[!] SUBSYSTEM '" + getName().toUpperCase() + "' PID INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}

	/**
	 * Method to clear JSONObject.
	 */
	public void finishedJSONInit() {
		robotMap.clear();
	}

	/**
	 * Method to output PID values to the SmartDashboard.
	 * 
	 * @param subsystemName
	 * @param p
	 * @param i
	 * @param d
	 */
	private void outputPIDValues(String subsystemName, float p, float i, float d) {
		SmartDashboard.putString(subsystemName + "P", p + "");
		SmartDashboard.putString(subsystemName + "I", i + "");
		SmartDashboard.putString(subsystemName + "D", d + "");
	}

	/**
	 * Method to update PID values from the SmartDashboard.
	 */
	public void updatePIDValues() {
		p = Float.parseFloat(SmartDashboard.getString(getName() + "P", p + ""));
		i = Float.parseFloat(SmartDashboard.getString(getName() + "I", i + ""));
		d = Float.parseFloat(SmartDashboard.getString(getName() + "D", d + ""));

		getPIDController().setPID(p, i, d);
	}
}