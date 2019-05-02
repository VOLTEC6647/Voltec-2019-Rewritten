package org.usfirst.lib6647.oi;

import java.util.HashMap;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Helper class for initializing joysticks.
 */
public class Controller extends Joystick {

	public HashMap<String, Button> buttons;

	/**
	 * Constructor for the controller.
	 * 
	 * Initializes each and every button, axisButton, and axisAngleButton.
	 * 
	 * @param port
	 */
	public Controller(int port) {
		super(port);

		for (int i = 1; i <= this.getButtonCount(); i++) {
			buttons.put("Button" + i, new JoystickButton(this, i));
		}

		for (int i = 0; i < this.getPOVCount(); i++) {
			buttons.put("POV" + i, buttonFromPOV(this, i));
			for (int j = 0; j <= 315; j += 45) {
				buttons.put("POV" + i + "_" + j, buttonFromPOV(this, i, j));
			}
		}

		for (int i = 0; i < this.getAxisCount(); i++) {
			buttons.put("Axis" + i, buttonFromAxis(this, i));
			for (int j = 0; j <= 270; j += 90) {
				buttons.put("Axis" + i + "_" + j, buttonFromAxis(this, i, j));
			}
		}
	}

	/**
	 * Creates a new button for the specified POV.
	 * 
	 * @param controller
	 * @param pov
	 * @return povButton
	 */
	private Button buttonFromPOV(GenericHID controller, int pov) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getPOV(pov) > -1;
			}
		};
	}

	/**
	 * Creates a new button for the specified POV and angle.
	 * 
	 * @param controller
	 * @param pov
	 * @param angle
	 * @return povAngleButton
	 */
	private Button buttonFromPOV(GenericHID controller, int pov, int angle) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getPOV(pov) == angle;
			}
		};
	}

	/**
	 * Creates a new button for the specified axis.
	 * 
	 * @param controller
	 * @param axis
	 * @return axisButton
	 */
	private Button buttonFromAxis(GenericHID controller, int axis) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getRawAxis(axis) != 0;
			}
		};
	}

	/**
	 * Creates a new button for the specified axis and angle.
	 * 
	 * @param controller
	 * @param axis
	 * @param angle
	 * @return axisAngleButton
	 */
	private Button buttonFromAxis(GenericHID controller, int axis, int angle) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getRawAxis(axis) == angle;
			}
		};
	}

	/**
	 * Functional interface for Joystick mapping.
	 */
	public interface MapDoubleT {
		/**
		 * Abstract method for Joystick mapping.
		 * 
		 * @param rawAxis
		 * @param in_min
		 * @param in_max
		 * @param out_min
		 * @param out_max
		 * @return mapDoubleT
		 */
		abstract double mapDoubleT(double rawAxis, double in_min, double in_max, double out_min, double out_max);
	}
}