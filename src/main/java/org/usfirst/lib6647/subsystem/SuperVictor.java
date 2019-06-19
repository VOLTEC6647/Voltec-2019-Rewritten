package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usfirst.lib6647.util.MotorUtils;

/**
 * Interface to allow Victor initialization via JSON file. Subsystem needs to
 * extend SuperSubsystem.
 */
public interface SuperVictor extends MotorUtils {
	/**
	 * HashMap storing the SuperSubsystem's Victors.
	 */
	public HashMap<String, WPI_VictorSPX> victors = new HashMap<String, WPI_VictorSPX>();

	/**
	 * Method to initialize Victors declared in JSON file, and add them to the
	 * HashMap using its name as its key. ALWAYS declare and initialize masters
	 * before followers!
	 * 
	 * @param robotMap
	 * @param subsystemName
	 */
	default void initVictors(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray victorArray = (JSONArray) ((JSONObject) ((JSONObject) robotMap.get("subsystems"))
					.get(subsystemName)).get("victors");
			Arrays.stream(victorArray.toArray()).map(json -> (JSONObject) json).forEach(json -> {
				try {
					WPI_VictorSPX victor = new WPI_VictorSPX(Integer.parseInt(json.get("port").toString()));

					if (json.containsKey("inverted"))
						setInverted(json, victor);

					if (json.containsKey("neutralMode"))
						setNeutralMode(json, victor);

					if (json.containsKey("loopRamp"))
						setLoopRamp(json, victor);

					victors.put(json.get("name").toString(), victor);
				} catch (Exception e) {
					System.out.println("[!] VICTOR '" + json.get("name").toString().toUpperCase() + "' INIT ERROR: "
							+ e.getMessage());
					System.exit(1);
				}
			});
		} catch (Exception e) {
			System.out.println("[!] VICTOR INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}
}