package org.usfirst.lib6647.oi;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * Helper class for getting buttons.
 */
public class ButtonHelper {

	public ArrayList<Controller> joysticks;

	/**
	 * Functional interface for getting a button.
	 */
	public interface OIButton {
		/**
		 * Abstract method for getting a button.
		 * 
		 * @param joystick
		 * @param button
		 * @return Button
		 */
		abstract Button get(int joystick, int button);
	}

	/**
	 * Functional interface for getting a button with an angle.
	 */
	public interface OIAngleButton {
		/**
		 * Abstract method for getting a button at a specific angle.
		 * 
		 * @param joystick
		 * @param axis
		 * @param angle
		 * @return Button
		 */
		abstract Button get(int joystick, int axis, int angle);
	}

	/**
	 * Lambda declaration for getting a joystick button.
	 * 
	 * e.g. oiButton.get(1, 7) gets button 7 from joystick 1.
	 */
	public OIButton oiButton = (joystick, button) -> joysticks.get(joystick).buttons.get("Button" + button);

	/**
	 * Lambda declaration for getting a joystick axis button at any angle.
	 * 
	 * e.g. oiAxisButton.get(1, 2) gets button for axis 2 from joystick 1.
	 */
	public OIButton oiAxisButton = (joystick, axis) -> joysticks.get(joystick).buttons.get("Axis" + axis);

	/**
	 * Lambda declaration for getting a joystick POV button at any angle.
	 * 
	 * e.g. oiPOVButton.get(1, 2) gets button for POV 2 from joystick 1.
	 */
	public OIButton oiPOVButton = (joystick, pov) -> joysticks.get(joystick).buttons.get("POV" + pov);

	/**
	 * Lambda declaration for getting a joystick axis button at a specific angle.
	 * (Only angles 0, 90, 180, and 270 exist).
	 * 
	 * e.g. oiAxis.get(1, 2, 90) gets angle 90 button from axis 2 from joystick 1.
	 */
	public OIAngleButton oiAxis = (joystick, axis, angle) -> joysticks.get(joystick).buttons
			.get("Axis" + axis + "_" + angle);

	/**
	 * Lambda declaration for getting a joystick POV button at a specific angle.
	 * (Only angles 0, 45, 90, 135, 180, 225, 270, and 315 exist).
	 * 
	 * e.g. oiPOV.get(1, 2, 45) gets angle 45 button from POV 2 from joystick 1.
	 */
	public OIAngleButton oiPOV = (joystick, pov, angle) -> joysticks.get(joystick).buttons
			.get("POV" + pov + "_" + angle);
}