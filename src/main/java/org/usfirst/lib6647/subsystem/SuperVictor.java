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
				if (json.get("victorName") != null && json.get("victorPort") != null) {
					WPI_VictorSPX victor = new WPI_VictorSPX((int) json.get("victorPort"));

					setInverted(json, victor);
					setNeutralMode(json, victor);
					setLoopRamp(json, victor);

					victors.put((String) json.get("victorName"), victor);
				}
			});
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}