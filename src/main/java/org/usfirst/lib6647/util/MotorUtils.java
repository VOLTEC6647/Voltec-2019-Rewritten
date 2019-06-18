package org.usfirst.lib6647.util;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.json.simple.JSONObject;

/**
 * Interface for converting String values to CTRE Objects.
 */
public interface MotorUtils {

	/**
	 * Sets a given Talon's inverted status from the JSON configuration.
	 * 
	 * @param json
	 * @param talon
	 * @throws Exception
	 */
	default void setInverted(JSONObject json, WPI_TalonSRX talon) throws Exception {
		talon.setInverted(Boolean.parseBoolean(json.get("inverted").toString()));
	}

	/**
	 * Sets a given Victor's inverted status from the JSON configuration.
	 * 
	 * @param json
	 * @param victor
	 * @throws Exception
	 */
	default void setInverted(JSONObject json, WPI_VictorSPX victor) throws Exception {
		victor.setInverted(Boolean.parseBoolean(json.get("inverted").toString()));
	}

	/**
	 * Method to convert a String to its respective NeutralMode.
	 * 
	 * @param neutralMode
	 * @return NeutralMode
	 */
	default NeutralMode getNeutralMode(String neutralMode) {
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
	 * Sets a given Talon's inverted status from the JSON configuration.
	 * 
	 * @param json
	 * @param talon
	 * @throws Exception
	 */
	default void setNeutralMode(JSONObject json, WPI_TalonSRX talon) throws Exception {
		talon.setNeutralMode(getNeutralMode(json.get("neutralMode").toString()));
	}

	/**
	 * Sets a given Victor's inverted status from the JSON configuration.
	 * 
	 * @param json
	 * @param victor
	 * @throws Exception
	 */
	default void setNeutralMode(JSONObject json, WPI_VictorSPX victor) throws Exception {
		victor.setNeutralMode(getNeutralMode(json.get("neutralMode").toString()));
	}

	/**
	 * Sets a given Talon's loopramp from the JSON configuration.
	 * 
	 * @param json
	 * @param talon
	 * @throws Exception
	 */
	default void setLoopRamp(JSONObject json, WPI_TalonSRX talon) throws Exception {
		JSONObject closed = (JSONObject) ((JSONObject) json.get("loopRamp")).get("closed"),
				open = (JSONObject) ((JSONObject) json.get("loopRamp")).get("open");

		if (closed.containsKey("timeoutMs") && open.containsKey("timeoutMs")) {
			talon.configClosedloopRamp(Double.parseDouble(closed.get("secondsFromNeutralToFull").toString()),
					Integer.parseInt(closed.get("timeoutMs").toString()));
			talon.configOpenloopRamp(Double.parseDouble(open.get("secondsFromNeutralToFull").toString()),
					Integer.parseInt(open.get("timeoutMs").toString()));
		} else {
			talon.configClosedloopRamp(Double.parseDouble(closed.get("secondsFromNeutralToFull").toString()));
			talon.configOpenloopRamp(Double.parseDouble(open.get("secondsFromNeutralToFull").toString()));
		}
	}

	/**
	 * Sets a given Victor's loopramp from the JSON configuration.
	 * 
	 * @param json
	 * @param victor
	 * @throws Exception
	 */
	default void setLoopRamp(JSONObject json, WPI_VictorSPX victor) throws Exception {
		JSONObject closed = (JSONObject) ((JSONObject) json.get("loopRamp")).get("closed"),
				open = (JSONObject) ((JSONObject) json.get("loopRamp")).get("open");

		if (closed.containsKey("timeoutMs") && open.containsKey("timeoutMs")) {
			victor.configClosedloopRamp(Double.parseDouble(closed.get("secondsFromNeutralToFull").toString()),
					Integer.parseInt(closed.get("timeoutMs").toString()));
			victor.configOpenloopRamp(Double.parseDouble(open.get("secondsFromNeutralToFull").toString()),
					Integer.parseInt(open.get("timeoutMs").toString()));
		} else {
			victor.configClosedloopRamp(Double.parseDouble(closed.get("secondsFromNeutralToFull").toString()));
			victor.configOpenloopRamp(Double.parseDouble(open.get("secondsFromNeutralToFull").toString()));
		}
	}

	/**
	 * Method to convert a String to its respective FeedbackDevice.
	 * 
	 * @param feedbackDevice
	 * @return FeedbackDevice
	 */
	default FeedbackDevice getFeedbackDevice(String feedbackDevice) {
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

	/**
	 * Sets a given Talon's sensors from the JSON configuration (fairly limited in
	 * terms of customizability at the moment).
	 * 
	 * @param json
	 * @param talon
	 * @throws Exception
	 */
	default void setSensors(JSONObject json, WPI_TalonSRX talon) throws Exception {
		JSONObject sensor = (JSONObject) json.get("sensor");
		JSONObject feedback = (JSONObject) sensor.get("feedback");

		talon.configSelectedFeedbackSensor(getFeedbackDevice(feedback.get("feedbackDevice").toString()),
				Integer.parseInt(feedback.get("pidIdx").toString()),
				Integer.parseInt(feedback.get("timeoutMs").toString()));

		talon.setSensorPhase(Boolean.parseBoolean(sensor.get("phase").toString()));

		talon.setSelectedSensorPosition(Integer.parseInt(sensor.get("sensorPos").toString()),
				Integer.parseInt(sensor.get("pidIdx").toString()),
				Integer.parseInt(sensor.get("timeoutMs").toString()));
	}

	/**
	 * Sets a given Talon's PID values from the JSON configuration.
	 * 
	 * @param json
	 * @param talon
	 * @throws Exception
	 */
	default void setPIDValues(JSONObject json, WPI_TalonSRX talon) throws Exception {
		JSONObject pid = (JSONObject) json.get("pid");

		talon.config_kP(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("p").toString()));
		talon.config_kI(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("i").toString()));
		talon.config_kD(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("d").toString()));
		talon.config_kF(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("f").toString()));
	}
}