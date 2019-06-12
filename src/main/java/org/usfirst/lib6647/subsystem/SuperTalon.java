package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usfirst.lib6647.util.MotorUtils;

/**
 * Interface to allow Talon initialization via JSON file. Subsystem needs to
 * extend SuperSubsystem.
 */
public interface SuperTalon extends MotorUtils {
	/**
	 * HashMap storing the SuperSubsystem's Talons.
	 */
	public HashMap<String, WPI_TalonSRX> talons = new HashMap<String, WPI_TalonSRX>();

	/**
	 * Method to initialize Talons declared in JSON file, and add them to the
	 * HashMap using its name as its key. ALWAYS declare and initialize masters
	 * before followers in the JSON file.
	 * 
	 * @param robotMap
	 * @param subsystemName
	 */
	default void initTalons(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray talonArray = (JSONArray) ((JSONObject) ((JSONObject) robotMap.get("subsystems"))
					.get(subsystemName)).get("talons");
			Arrays.stream(talonArray.toArray()).map(json -> (JSONObject) json).forEach(json -> {
				WPI_TalonSRX talon = new WPI_TalonSRX(Integer.parseInt(json.get("talonPort").toString()));

				setInverted(json, talon);
				setNeutralMode(json, talon);
				setLoopRamp(json, talon);
				setSensors(json, talon);
				setPIDValues(json, talon);

				talons.put(json.get("talonName").toString(), talon);
			});
		} catch (NullPointerException e) {
			System.out.println("[!] TALON INIT FAILED.");
			e.printStackTrace();
			System.exit(1);
		}
	}
}