package org.usfirst.lib6647.subsystem;

import java.util.Arrays;
import java.util.HashMap;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.usfirst.lib6647.util.TalonBuilder;

/**
 * Interface to allow Talon initialization via JSON file. Subsystem needs to
 * extend SuperSubsystem.
 */
public interface SuperTalon {
	/**
	 * HashMap storing the SuperSubsystem's talons.
	 */
	public HashMap<String, TalonBuilder> talons = new HashMap<String, TalonBuilder>();

	/**
	 * Method to initialize talons declared in JSON file, and add them to the
	 * HashMap using its name as its key.
	 * 
	 * @param robotMap
	 * @param subsystemName
	 */
	default void initTalons(JSONObject robotMap, String subsystemName) {
		try {
			JSONArray talonArray = (JSONArray) robotMap.get("subsystems." + subsystemName + ".talons");

			Arrays.stream(talonArray.toArray()).map(obj -> (JSONObject) obj).forEach(talon -> {
				if ((boolean) talon.get("victor")) {
					talons.put((String) talon.get("talonName"), new TalonBuilder((int) talon.get("talonPort"),
							(String) talon.get("talonName"), (int) talon.get("victorPort"),
							(String) talon.get("victorName"), getNeutralMode((String) talon.get("neutralMode")),
							(boolean) talon.get("inverted"), (double) talon.get("rampDrive"), (int) talon.get("loop"),
							getFeedbackDevice((String) talon.get("feedbackDevice")), (int) talon.get("feedbackPidIdx"),
							(int) talon.get("feedbackTimeoutMs"), (boolean) talon.get("phase"),
							(int) talon.get("sensorPos"), (int) talon.get("pidIdx"), (int) talon.get("timeoutMs"),
							(int) talon.get("slotIdx"), (float) talon.get("p"), (float) talon.get("i"),
							(float) talon.get("d"), (float) talon.get("f")));
				} else {
					talons.put((String) talon.get("talonName"), new TalonBuilder((int) talon.get("talonPort"),
							(String) talon.get("talonName"), getNeutralMode((String) talon.get("neutralMode")),
							(boolean) talon.get("inverted"), (double) talon.get("rampDrive"), (int) talon.get("loop"),
							getFeedbackDevice((String) talon.get("feedbackDevice")), (int) talon.get("feedbackPidIdx"),
							(int) talon.get("feedbackTimeoutMs"), (boolean) talon.get("phase"),
							(int) talon.get("sensorPos"), (int) talon.get("pidIdx"), (int) talon.get("timeoutMs"),
							(int) talon.get("slotIdx"), (float) talon.get("p"), (float) talon.get("i"),
							(float) talon.get("d"), (float) talon.get("f")));
				}
			});
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to convert a String to its respective NeutralMode.
	 * 
	 * @param neutralMode
	 * @return NeutralMode
	 */
	private NeutralMode getNeutralMode(String neutralMode) {
		switch (neutralMode) {
		case "Coast":
			return NeutralMode.Coast;
		case "Brake":
			return NeutralMode.Brake;
		case "EEPROMSetting":
			return NeutralMode.EEPROMSetting;
		default:
			return null;
		}
	}

	/**
	 * Method to convert a String to its respective FeedbackDevice.
	 * 
	 * @param feedbackDevice
	 * @return FeedbackDevice
	 */
	private FeedbackDevice getFeedbackDevice(String feedbackDevice) {
		switch (feedbackDevice) {
		case "QuadEncoder":
			return FeedbackDevice.QuadEncoder;
		case "Analog":
			return FeedbackDevice.Analog;
		case "Tachometer":
			return FeedbackDevice.Tachometer;
		case "PulseWidthEncodedPosition":
			return FeedbackDevice.PulseWidthEncodedPosition;
		case "SensorSum":
			return FeedbackDevice.SensorSum;
		case "SensorDifference":
			return FeedbackDevice.SensorDifference;
		case "RemoteSensor0":
			return FeedbackDevice.RemoteSensor0;
		case "RemoteSensor1":
			return FeedbackDevice.RemoteSensor1;
		case "SoftwareEmulatedSensor":
			return FeedbackDevice.SoftwareEmulatedSensor;
		case "CTRE_MagEncoder_Absolute":
			return FeedbackDevice.CTRE_MagEncoder_Absolute;
		case "CTRE_MagEncoder_Relative":
			return FeedbackDevice.CTRE_MagEncoder_Relative;
		default:
			return null;
		}
	}
}