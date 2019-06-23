package org.usfirst.lib6647.oi;

import java.util.HashMap;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Joystick wrapper for initializing buttons.
 */
public class JController extends Joystick {

	public HashMap<String, Button> buttons = new HashMap<String, Button>();

	/**
	 * Constructor for the controller.
	 * 
	 * Initializes each and every button from the joystick found at the given port.
	 * Also initializes buttons for certain angles for each of the axis buttons.
	 * 
	 * @param port
	 */
	public JController(int port) {
		super(port);

		for (int i = 1; i <= this.getButtonCount(); i++) {
			buttons.put("Button" + i, new JoystickButton(this, i));
		}

		for (int i = 0; i < this.getPOVCount(); i++) {
			buttons.put("dPad" + i, axisButton(this, AxisType.dPad, i));
			for (int j = 0; j <= 315; j += 45) {
				buttons.put("dPad" + i + "_" + j, axisButton(this, AxisType.dPad, i, j));
			}
		}

		for (int i = 0; i < this.getAxisCount(); i++) {
			buttons.put("Stick" + i, axisButton(this, AxisType.Stick, i));
			buttons.put("Stick" + i + "_1", axisButton(this, AxisType.Stick, 1));
			buttons.put("Stick" + i + "_-1", axisButton(this, AxisType.Stick, -1));
		}
	}

	/**
	 * Method to get left-most Stick raw value.
	 * 
	 * @return leftAxis
	 */
	public double getLeftAxis() {
		return getRawAxis(1);
	}

	/**
	 * Method to get right-most Stick raw value.
	 * 
	 * @return rightAxis
	 */
	public double getRightAxis() {
		return getRawAxis(getAxisCount());
	}

	/**
	 * Enum listing possible axis types.
	 */
	private enum AxisType {
		dPad, Stick
	}

	/**
	 * Method for creating a button out of Joystick axes.
	 * 
	 * @param controller
	 * @param type
	 * @param axis
	 * @return Button from the given axis
	 */
	private Button axisButton(GenericHID controller, AxisType type, int axis) {
		switch (type) {
		case dPad:
			return new Button() {
				@Override
				public boolean get() {
					return controller.getPOV(axis) > -1;
				}
			};
		case Stick:
			return new Button() {
				@Override
				public boolean get() {
					return controller.getRawAxis(axis) != 0;
				}
			};
		}
		return null;
	}

	/**
	 * Method for creating a button out of Joystick axes, at a specific angle.
	 * 
	 * @param controller
	 * @param type
	 * @param axis
	 * @param angle
	 * @return Button from the given axis, at the given angle
	 */
	private Button axisButton(GenericHID controller, AxisType type, int axis, int angle) {
		switch (type) {
		case dPad:
			return new Button() {
				@Override
				public boolean get() {
					return controller.getPOV(axis) == angle;
				}
			};
		case Stick:
			return new Button() {
				@Override
				public boolean get() {
					return controller.getRawAxis(axis) == angle;
				}
			};
		}
		return null;
	}
}