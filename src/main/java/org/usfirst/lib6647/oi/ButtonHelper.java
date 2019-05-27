package org.usfirst.lib6647.oi;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Helper class for registering button input.
 */
public class ButtonHelper {
	public ArrayList<JController> joysticks;

	/**
	 * Method for getting a button with a JSON name from a given joystick. Returns
	 * null if no button is found.
	 * 
	 * @param joystick
	 * @param buttonName
	 * @return button from the given joystick
	 */
	public Button oiButton(int joystick, String buttonName) {
		JSONParser parser = new JSONParser();
		try (Reader file = new FileReader("src\\main\\java\\org\\usfirst\\lib6647\\oi\\ControllerProfiles.json")) {
			JSONObject profiles = (JSONObject) parser.parse(file);
			String key = (String) profiles.get(joysticks.get(joystick).getName() + "." + buttonName);
			return joysticks.get(joystick).buttons.get(key);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method for getting a button from a given joystick.
	 * 
	 * @param joystick
	 * @param button
	 * @return button from the given joystick
	 */
	public Button oiButton(int joystick, int button) {
		return joysticks.get(joystick).buttons.get("Button" + button);
	}

	/**
	 * Method for getting an axisButton from a given joystick.
	 * 
	 * @param joystick
	 * @param type
	 * @param axis
	 * @return axisButton from the given joystick, for the given axis
	 */
	public Button oiButton(int joystick, String type, int axis) {
		return joysticks.get(joystick).buttons.get(type + axis);
	}

	/**
	 * Method for getting an axisButton from a given joystick, at a specific angle.
	 * 
	 * @param joystick
	 * @param type
	 * @param axis
	 * @param angle
	 * @return axisButton from the given joystick, for the given axis, for the given
	 *         angle
	 */
	public Button oiButton(int joystick, String type, int axis, int angle) {
		return joysticks.get(joystick).buttons.get(type + axis + "_" + angle);
	}
}