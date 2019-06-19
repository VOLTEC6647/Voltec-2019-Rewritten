package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Interface to allow DigitalInput initialization via JSON file. Subsystem needs
 * to extend SuperSubsystem.
 */
public interface SuperDigitalInput {
	/**
	 * HashMap storing the SuperSubsystem's DigitalInputs.
	 */
	public HashMap<String, DigitalInput> digitalInputs = new HashMap<String, DigitalInput>();

	/**
	 * Method to initialize DigitalInputs declared in JSON file, and add them to the
	 * HashMap using its name as its key.
	 * 
	 * @param robotMap
	 * @param subsystemName
	 */
	default void initDigitalInputs(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray digitalInputArray = (JSONArray) ((JSONObject) ((JSONObject) robotMap.get("subsystems"))
					.get(subsystemName)).get("digitalInputs");
			Arrays.stream(digitalInputArray.toArray()).map(json -> (JSONObject) json).forEach(json -> {
				try {
					DigitalInput digitalInput = new DigitalInput(Integer.parseInt(json.get("port").toString()));
					digitalInputs.put(json.get("name").toString(), digitalInput);
				} catch (Exception e) {
					System.out.println("[!] DIGITAL INPUT INIT ERROR: " + e.getMessage());
					System.exit(1);
				}
			});
		} catch (Exception e) {
			System.out.println("[!] DIGITAL INPUT INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}
}