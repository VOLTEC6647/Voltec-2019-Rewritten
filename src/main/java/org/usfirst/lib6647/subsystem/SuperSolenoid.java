package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Interface to allow Solenoid initialization via JSON file. Subsystem needs to
 * extend SuperSubsystem.
 */
public interface SuperSolenoid {
	/**
	 * HashMap storing the SuperSubsystem's Solenoids.
	 */
	public HashMap<String, Solenoid> solenoids = new HashMap<String, Solenoid>();

	/**
	 * Method to initialize Solenoids declared in JSON file, and add them to the
	 * HashMap using its name as its key.
	 * 
	 * @param robotMap
	 * @param subsystemName
	 */
	default void initSolenoids(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray solenoidArray = (JSONArray) ((JSONObject) ((JSONObject) robotMap.get("subsystems"))
					.get(subsystemName)).get("solenoids");
			Arrays.stream(solenoidArray.toArray()).map(json -> (JSONObject) json).forEach(json -> {
				try {
					Solenoid solenoid = new Solenoid(Integer.parseInt(json.get("channel").toString()));

					if (json.containsKey("initialValue"))
						solenoid.set(Boolean.parseBoolean(json.get("initialValue").toString()));

					solenoids.put(json.get("name").toString(), solenoid);
				} catch (Exception e) {
					System.out.println("[!] SOLENOID INIT ERROR: " + e.getMessage());
					System.exit(1);
				}
			});
		} catch (Exception e) {
			System.out.println("[!] SOLENOID INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}
}