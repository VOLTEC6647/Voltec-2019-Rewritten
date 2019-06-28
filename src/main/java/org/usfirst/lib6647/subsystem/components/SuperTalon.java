package org.usfirst.lib6647.subsystem.components;

import java.util.Arrays;
import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usfirst.lib6647.util.MotorUtils;

import edu.wpi.first.wpilibj.DriverStation;

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
				try {
					WPI_TalonSRX talon = new WPI_TalonSRX(Integer.parseInt(json.get("port").toString()));

					if (json.containsKey("inverted"))
						setInverted(json, talon);

					if (json.containsKey("neutralMode"))
						setNeutralMode(json, talon);

					if (json.containsKey("loopRamp"))
						setLoopRamp(json, talon);

					if (json.containsKey("sensor"))
						setSensors(json, talon);

					if (json.containsKey("pid"))
						setPIDValues(json, talon);

					talons.put(json.get("name").toString(), talon);
				} catch (Exception e) {
					DriverStation.reportError(
							"[!] SUBSYSTEM '" + subsystemName.toUpperCase() + "' TALON INIT ERROR: " + e.getMessage(),
							false);
					System.out.println(
							"[!] SUBSYSTEM '" + subsystemName.toUpperCase() + "' TALON INIT ERROR: " + e.getMessage());
					System.exit(1);
				} finally {
					json.clear();
				}
			});
			talonArray.clear();
		} catch (Exception e) {
			DriverStation.reportError(
					"[!] SUBSYSTEM '" + subsystemName.toUpperCase() + "' TALON INIT ERROR: " + e.getMessage(), false);
			System.out
					.println("[!] SUBSYSTEM '" + subsystemName.toUpperCase() + "' TALON INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}
}