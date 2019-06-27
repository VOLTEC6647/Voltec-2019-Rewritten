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
	private int leftAxis = 1, rightAxis = 5;

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
			buttons.put("dPad" + i, buttonFromPOV(this, i));
			for (int j = 0; j <= 315; j += 45) {
				buttons.put("dPad" + i + "_" + j, buttonFromPOV(this, i, j));
			}
		}

		for (int i = 0; i < this.getAxisCount(); i++) {
			buttons.put("Stick" + i + "_1", buttonFromAxisPositive(this, i));
			buttons.put("Stick" + i + "_-1", buttonFromAxisNegative(this, i));
		}
	}

	/**
	 * Method to set left and right axis.
	 * 
	 * @param leftAxis
	 * @param rightAxis
	 */
	public void setLeftRightAxis(int leftAxis, int rightAxis) {
		this.leftAxis = leftAxis;
		this.rightAxis = rightAxis;
	}

	/**
	 * Method to get left-most Stick raw value.
	 * 
	 * @return leftAxis
	 */
	public double getLeftAxis() {
		return getRawAxis(leftAxis);
	}

	/**
	 * Method to get right-most Stick raw value.
	 * 
	 * @return rightAxis
	 */
	public double getRightAxis() {
		return getRawAxis(rightAxis);
	}

	/**
	 * Method for getting a pov button at any angle.
	 * 
	 * @param controller
	 * @param pov
	 * @return axisButton
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
	 * Method for getting a pov button at a specific angle.
	 * 
	 * @param controller
	 * @param pov
	 * @param angle
	 * @return povButton
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
	 * Method for getting a negative input from an axis.
	 * 
	 * @param controller
	 * @param axis
	 * @return axisButton
	 */
	private Button buttonFromAxisNegative(GenericHID controller, int axis) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getRawAxis(axis) < -0.15;
			}
		};
	}

	/**
	 * Method for getting a positive input from an axis.
	 * 
	 * @param controller
	 * @param axis
	 * @return axisButton
	 */
	private Button buttonFromAxisPositive(GenericHID controller, int axis) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getRawAxis(axis) > 0.15;
			}
		};
	}
}