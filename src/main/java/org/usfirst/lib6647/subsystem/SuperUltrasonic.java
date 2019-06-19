package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Interface to allow Ultrasonic initialization via JSON file. Subsystem needs
 * to extend SuperSubsystem.
 */
public interface SuperUltrasonic {
	/**
	 * HashMap storing the SuperSubsystem's Ultrasonics.
	 */
	public HashMap<String, Ultrasonic> ultrasonics = new HashMap<String, Ultrasonic>();

	/**
	 * Method to initialize Ultrasonics declared in JSON file, and add them to the
	 * HashMap using its name as its key.
	 * 
	 * @param robotMap
	 * @param subsystemName
	 */
	default void initUltrasonics(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray ultrasonicArray = (JSONArray) ((JSONObject) ((JSONObject) robotMap.get("subsystems"))
					.get(subsystemName)).get("ultrasonics");
			Arrays.stream(ultrasonicArray.toArray()).map(json -> (JSONObject) json).forEach(json -> {
				try {
					Ultrasonic ultrasonic = new Ultrasonic(Integer.parseInt(json.get("pingChannel").toString()),
							Integer.parseInt(json.get("echoChannel").toString()));
					ultrasonics.put(json.get("name").toString(), ultrasonic);
				} catch (Exception e) {
					System.out.println("[!] ULTRASONIC INIT ERROR: " + e.getMessage());
					System.exit(1);
				}
			});
		} catch (Exception e) {
			System.out.println("[!] ULTRASONIC INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}
}