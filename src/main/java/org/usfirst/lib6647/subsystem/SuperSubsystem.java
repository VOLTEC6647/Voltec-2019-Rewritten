package org.usfirst.lib6647.subsystem;

import java.io.FileReader;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class to allow usage of JSON files for Subsystem creation.
 */
public abstract class SuperSubsystem extends Subsystem {

	protected JSONObject robotMap;

	/**
	 * Constructor for the class.
	 * 
	 * @param name
	 * @param fileName (of JSON)
	 */
	public SuperSubsystem(String name, String fileName) {
		super(name);

		initJSON(fileName);
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
	 * Method to clear JSONObject.
	 */
	public void finishedJSONInit() {
		robotMap.clear();
	}
}