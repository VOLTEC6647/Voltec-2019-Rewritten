package org.usfirst.lib6647.oi;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Helper class for registering button input.
 */
public class ButtonHelper {
	public HashMap<String, JController> joysticks;
	String fileName;

	/**
	 * Constructor for the class.
	 * 
	 * @param fileName
	 */
	public ButtonHelper(String fileName) {
		this.fileName = fileName;

		joysticks = new HashMap<String, JController>();
	}

	/**
	 * Method for getting a button with a JSON name from a given joystick. Returns
	 * null if no button is found.
	 * 
	 * @param joystick
	 * @param buttonName
	 * @return button from the given joystick
	 */
	public Button oiButton(String joystickName, String buttonName) {
		try {
			JSONParser parser = new JSONParser();
			Reader file = new FileReader(fileName);
			JSONObject jsonJoystick = (JSONObject) ((JSONObject) parser.parse(file))
					.get(joysticks.get(joystickName).getName());

			Button button = joysticks.get(joystickName).buttons.get(jsonJoystick.get(buttonName).toString());

			jsonJoystick.clear();
			file.close();
			parser.reset();

			return button;
		} catch (IOException e) {
			DriverStation.reportError("[!] OIBUTTON " + buttonName + " IO ERROR: " + e.getMessage(), false);
			System.out.println("[!] OIBUTTON " + buttonName + " IO ERROR: " + e.getMessage());
			System.exit(1);
		} catch (ParseException e) {
			DriverStation.reportError("[!] OIBUTTON " + buttonName + " PARSE ERROR: " + e.getMessage(), false);
			System.out.println("[!] OIBUTTON " + buttonName + " PARSE ERROR: " + e.getMessage());
			System.exit(1);
		} catch (Exception e) {
			DriverStation.reportError("[!] OIBUTTON " + buttonName + " ERROR: " + e.getMessage(), false);
			System.out.println("[!] OIBUTTON " + buttonName + " ERROR: " + e.getMessage());
			System.exit(1);
		}
		return null;
	}

	/**
	 * Method for getting a button from a given joystick.
	 * 
	 * @param joystickName
	 * @param button
	 * @return button from the given joystick
	 */
	public Button oiButton(String joystickName, int button) {
		return joysticks.get(joystickName).buttons.get("Button" + button);
	}

	/**
	 * Method for getting an axisButton from a given joystick.
	 * 
	 * @param joystickName
	 * @param type
	 * @param axis
	 * @return axisButton from the given joystick, for the given axis
	 */
	public Button oiButton(String joystickName, String type, int axis) {
		return joysticks.get(joystickName).buttons.get(type + axis);
	}

	/**
	 * Method for getting an axisButton from a given joystick, at a specific angle.
	 * 
	 * @param joystickName
	 * @param type
	 * @param axis
	 * @param angle
	 * @return axisButton from the given joystick, for the given axis, for the given
	 *         angle
	 */
	public Button oiButton(String joystickName, String type, int axis, int angle) {
		return joysticks.get(joystickName).buttons.get(type + axis + "_" + angle);
	}
}