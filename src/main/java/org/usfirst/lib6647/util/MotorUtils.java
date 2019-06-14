package org.usfirst.lib6647.util;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import org.json.simple.JSONObject;

/**
 * Interface for converting String values to CTRE Phoenix Objects.
 */
public interface MotorUtils {

	/**
	 * Sets a given motor's inverted status from the JSON configuration.
	 * 
	 * @param json
	 * @param motor
	 * @throws Exception
	 */
	default void setInverted(JSONObject json, BaseMotorController motor) throws Exception {
		motor.setInverted(Boolean.parseBoolean(json.get("inverted").toString()));
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
	 * Sets a given motor's inverted status from the JSON configuration.
	 * 
	 * @param json
	 * @param motor
	 * @throws Exception
	 */
	default void setNeutralMode(JSONObject json, BaseMotorController motor) throws Exception {
		motor.setNeutralMode(getNeutralMode(json.get("neutralMode").toString()));
	}

	/**
	 * Sets a given motor's loop ramp from the JSON configuration.
	 * 
	 * @param json
	 * @param motor
	 * @throws Exception
	 */
	default void setLoopRamp(JSONObject json, BaseMotorController motor) throws Exception {
		JSONObject closed = (JSONObject) ((JSONObject) json.get("loopRamp")).get("closed"),
				open = (JSONObject) ((JSONObject) json.get("loopRamp")).get("open");

		if (closed.containsKey("timeoutMs") && open.containsKey("timeoutMs")) {
			motor.configClosedloopRamp(Double.parseDouble(closed.get("secondsFromNeutralToFull").toString()),
					Integer.parseInt(closed.get("timeoutMs").toString()));
			motor.configOpenloopRamp(Double.parseDouble(open.get("secondsFromNeutralToFull").toString()),
					Integer.parseInt(open.get("timeoutMs").toString()));
		} else {
			motor.configClosedloopRamp(Double.parseDouble(closed.get("secondsFromNeutralToFull").toString()));
			motor.configOpenloopRamp(Double.parseDouble(open.get("secondsFromNeutralToFull").toString()));
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
	 * Sets a given motor's sensors from the JSON configuration (fairly limited in
	 * terms of customizability at the moment).
	 * 
	 * @param json
	 * @param motor
	 * @throws Exception
	 */
	default void setSensors(JSONObject json, BaseMotorController motor) throws Exception {
		JSONObject sensor = (JSONObject) json.get("sensor");
		JSONObject feedback = (JSONObject) sensor.get("feedback");

		motor.configSelectedFeedbackSensor(getFeedbackDevice(feedback.get("feedbackDevice").toString()),
				Integer.parseInt(feedback.get("pidIdx").toString()),
				Integer.parseInt(feedback.get("timeoutMs").toString()));

		motor.setSensorPhase(Boolean.parseBoolean(sensor.get("phase").toString()));

		motor.setSelectedSensorPosition(Integer.parseInt(sensor.get("sensorPos").toString()),
				Integer.parseInt(sensor.get("pidIdx").toString()),
				Integer.parseInt(sensor.get("timeoutMs").toString()));
	}

	/**
	 * Sets a given motor's PID values from the JSON configuration.
	 * 
	 * @param json
	 * @param motor
	 * @throws Exception
	 */
	default void setPIDValues(JSONObject json, BaseMotorController motor) throws Exception{
		JSONObject pid = (JSONObject) json.get("pid");

		motor.config_kP(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("p").toString()));
		motor.config_kI(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("i").toString()));
		motor.config_kD(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("d").toString()));
		motor.config_kF(Integer.parseInt(pid.get("slotIdx").toString()), Double.parseDouble(pid.get("f").toString()));
	}
}