package org.usfirst.lib6647.subsystem;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.usfirst.lib6647.util.PID;

import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Class to allow usage of JSON files for PIDSubsystem creation.
 */
public abstract class PIDSuperSubsystem extends PIDSubsystem implements PID {

	public JSONObject robotMap;

	/**
	 * Constructor for the class.
	 * 
	 * @param name
	 * @param fileName (of JSON)
	 */
	public PIDSuperSubsystem(String name, String fileName) {
		super(name, 0, 0, 0);

		initJSON(fileName);
	}

	/**
	 * Method to initialize JSON at the given path.
	 * 
	 * @param fileName
	 */
	public void initJSON(String fileName) {
		JSONParser parser = new JSONParser();
		try (Reader file = new FileReader(fileName)) {
			robotMap = (JSONObject) parser.parse(file);
		} catch (IOException | ParseException | NullPointerException e) {
			System.out.println("[!] ROBOTMAP FILE ERROR: " + e.getMessage());
			System.exit(1);
		}
	}
}