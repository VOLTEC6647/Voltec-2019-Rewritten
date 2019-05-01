package org.usfirst.lib6647.oi;

import java.util.HashMap;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Controller extends Joystick {

	public HashMap<String, Button> buttons;

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

	private Button buttonFromPOV(GenericHID controller, int pov) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getPOV(pov) > -1;
			}
		};
	}

	private Button buttonFromPOV(GenericHID controller, int pov, int angle) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getPOV(pov) == angle;
			}
		};
	}

	private Button buttonFromAxis(GenericHID controller, int axis) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getRawAxis(axis) != 0;
			}
		};
	}

	private Button buttonFromAxis(GenericHID controller, int axis, int angle) {
		return new Button() {
			@Override
			public boolean get() {
				return controller.getRawAxis(axis) == angle;
			}
		};
	}

	public interface OIButton {
		abstract Button get(int joystick, int button);
	}

	public interface OIAxisButton {
		abstract Button get(int joystick, int axis);
	}

	public interface OIAngleButton {
		abstract Button get(int joystick, int axis, int angle);
	}

	public interface MapDoubleT {
		abstract double mapDoubleT(double x, double in_min, double in_max, double out_min, double out_max);
	}
}