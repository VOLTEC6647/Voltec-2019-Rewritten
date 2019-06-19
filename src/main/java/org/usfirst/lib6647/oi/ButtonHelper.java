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
	String fileName;

	/**
	 * Constructor for the class.
	 * 
	 * @param fileName
	 */
	public ButtonHelper(String fileName) {
		this.fileName = fileName;

		joysticks = new ArrayList<JController>();
	}

	/**
	 * Method for getting a button with a JSON name from a given joystick. Returns
	 * null if no button is found.
	 * 
	 * @param joystick
	 * @param buttonName
	 * @return button from the given joystick
	 * @throws IOException
	 * @throws ParseException
	 * @throws NullPointerException
	 */
	public Button oiButton(int joystick, String buttonName) throws IOException, ParseException, NullPointerException {
		JSONParser parser = new JSONParser();
		try (Reader file = new FileReader(fileName)) {
			JSONObject jsonJoystick = (JSONObject) ((JSONObject) parser.parse(file))
					.get(joysticks.get(joystick).getName());
			return joysticks.get(joystick).buttons.get(jsonJoystick.get(buttonName).toString());
		} catch (IOException e) {
			System.out.println("[!] OIBUTTON " + buttonName + " IO ERROR: " + e.getMessage());
			throw e;
		} catch (ParseException e) {
			System.out.println("[!] OIBUTTON " + buttonName + " PARSE ERROR: " + e.getMessage());
			throw e;
		} catch (NullPointerException e) {
			System.out.println("[!] OIBUTTON " + buttonName + " ERROR: " + e.getMessage());
			throw e;
		}
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