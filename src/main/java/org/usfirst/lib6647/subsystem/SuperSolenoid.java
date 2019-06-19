package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.wpi.first.wpilibj.Solenoid;

public interface SuperSolenoid {
	public HashMap<String, Solenoid> solenoids = new HashMap<String, Solenoid>();

	default void initSolenoids(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray solenoidArray = (JSONArray) ((JSONObject) ((JSONObject) robotMap.get("subsystems"))
					.get(subsystemName)).get("solenoids");
			Arrays.stream(solenoidArray.toArray()).map(json -> (JSONObject) json).forEach(json -> {
				try {
					Solenoid solenoid = new Solenoid(Integer.parseInt(json.get("solenoidPort").toString()));

					if(json.containsKey("initialValue"))
						solenoid.set(Boolean.parseBoolean(json.get("initialValue").toString()));

					solenoids.put(json.get("solenoidName").toString(), solenoid);
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