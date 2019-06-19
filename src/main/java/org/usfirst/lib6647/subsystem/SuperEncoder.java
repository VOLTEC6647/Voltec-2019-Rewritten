package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

/**
 * Interface to allow Encoder initialization via JSON file. Subsystem needs to
 * extend SuperSubsystem.
 */
public interface SuperEncoder {
	/**
	 * HashMap storing the SuperSubsystem's Encoders.
	 */
	public HashMap<String, Encoder> encoders = new HashMap<String, Encoder>();

	/**
	 * Method to initialize Encoders declared in JSON file, and add them to the
	 * HashMap using its name as its key.
	 * 
	 * @param robotMap
	 * @param subsystemName
	 */
	default void initEncoders(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray encoderArray = (JSONArray) ((JSONObject) ((JSONObject) robotMap.get("subsystems"))
					.get(subsystemName)).get("encoders");
			Arrays.stream(encoderArray.toArray()).map(json -> (JSONObject) json).forEach(json -> {
				try {
					Encoder encoder = new Encoder(Integer.parseInt(json.get("channelA").toString()),
							Integer.parseInt(json.get("channelB").toString()),
							Boolean.parseBoolean(json.get("reverse").toString()),
							getEncodingType(json.get("encodingType").toString()));
					encoder.reset();

					encoders.put(json.get("name").toString(), encoder);
				} catch (Exception e) {
					System.out.println("[!] ENCODER INIT ERROR: " + e.getMessage());
					System.exit(1);
				}
			});
		} catch (Exception e) {
			System.out.println("[!] ENCODER INIT ERROR: " + e.getMessage());
			System.exit(1);
		}
	}

	private EncodingType getEncodingType(String encodingType) {
		switch (encodingType) {
		case "k1X":
			return EncodingType.k1X;
		case "k2X":
			return EncodingType.k2X;
		case "k4X":
			return EncodingType.k4X;
		default:
			return null;
		}
	}
}