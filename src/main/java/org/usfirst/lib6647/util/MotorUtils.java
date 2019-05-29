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
	 */
	default void setInverted(JSONObject json, BaseMotorController motor) {
		if (json.get("inverted") != null)
			motor.setInverted((boolean) json.get("inverted"));
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
	 */
	default void setNeutralMode(JSONObject json, BaseMotorController motor) {
		if (json.get("neutralMode") != null)
			motor.setNeutralMode(getNeutralMode((String) json.get("neutralMode")));
	}

	/**
	 * Sets a given motor's loop ramp from the JSON configuration.
	 * 
	 * @param json
	 * @param motor
	 */
	default void setLoopRamp(JSONObject json, BaseMotorController motor) {
		if (json.get("loopRamp") != null) {
			JSONObject closed = (JSONObject) ((JSONObject) json.get("loopRamp")).get("closed"),
					open = (JSONObject) ((JSONObject) json.get("loopRamp")).get("open");

			if (closed.get("timeoutMs") != null && open.get("timeoutMs") != null) {
				motor.configClosedloopRamp((double) closed.get("secondsFromNeutralToFull"),
						(int) closed.get("timeoutMs"));
				motor.configOpenloopRamp((double) open.get("secondsFromNeutralToFull"), (int) open.get("timeoutMs"));
			} else {
				motor.configClosedloopRamp((double) closed.get("secondsFromNeutralToFull"));
				motor.configOpenloopRamp((double) open.get("secondsFromNeutralToFull"));
			}
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
	 */
	default void setSensors(JSONObject json, BaseMotorController motor) {
		if (json.get("sensor") != null) {
			JSONObject sensor = (JSONObject) json.get("sensor");

			if (sensor.get("feedback") != null) {
				JSONObject feedback = (JSONObject) sensor.get("feedback");

				if (feedback.get("feedbackDevice") != null && feedback.get("pidIdx") != null
						&& feedback.get("timeoutMs") != null)
					motor.configSelectedFeedbackSensor(getFeedbackDevice((String) feedback.get("feedbackDevice")),
							(int) feedback.get("pidIdx"), (int) feedback.get("timeoutMs"));
			}

			if (sensor.get("phase") != null)
				motor.setSensorPhase((boolean) sensor.get("phase"));

			if (sensor.get("sensorPos") != null && sensor.get("pidIdx") != null && sensor.get("timeoutMs") != null)
				motor.setSelectedSensorPosition((int) sensor.get("sensorPos"), (int) sensor.get("pidIdx"),
						(int) sensor.get("timeoutMs"));
		}
	}

	/**
	 * Sets a given motor's PID values from the JSON configuration.
	 * 
	 * @param json
	 * @param motor
	 */
	default void setPIDValues(JSONObject json, BaseMotorController motor) {
		if (json.get("pid") != null) {
			JSONObject pid = (JSONObject) json.get("pid");

			if (pid.get("slotIdx") != null && pid.get("p") != null && pid.get("i") != null && pid.get("d") != null) {
				motor.config_kP((int) pid.get("slotIdx"), (double) pid.get("p"));
				motor.config_kI((int) pid.get("slotIdx"), (double) pid.get("i"));
				motor.config_kD((int) pid.get("slotIdx"), (double) pid.get("d"));
				motor.config_kF((int) pid.get("slotIdx"), (double) pid.get("f"));
			}
		}
	}
}