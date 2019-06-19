package org.usfirst.lib6647.subsystem;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class to allow usage of JSON files for Subsystem creation.
 */
public abstract class SuperSubsystem extends Subsystem {

	public JSONObject robotMap;

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