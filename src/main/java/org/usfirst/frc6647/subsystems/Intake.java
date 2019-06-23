package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.components.SuperDigitalInput;
import org.usfirst.lib6647.subsystem.components.SuperSolenoid;
import org.usfirst.lib6647.subsystem.components.SuperVictor;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Servo;

/**
 * Subsystem for the Intake.
 */
public class Intake extends SuperSubsystem implements SuperVictor, SuperSolenoid, SuperDigitalInput {

	private Servo camServo;

	private static Intake m_instance = null;

	/**
	 * Creates static Intake instance.
	 */
	public static void createInstance() {
		m_instance = new Intake();
	}

	/**
	 * Gets static Intake instance. If there is none, creates one.
	 * 
	 * @return static Intake instance
	 */
	public static Intake getInstance() {
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public Intake() {
		super("intake", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initVictors(robotMap, getName());
		initSolenoids(robotMap, getName());
		initDigitalInputs(robotMap, getName());
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Sets both Intake Victors to the given speed, in PercentOutput.
	 * 
	 * @param speedLeft
	 * @param speedRight
	 */
	public void setIntake(double speedLeft, double speedRight) {
		victors.get("intakeLeft").set(ControlMode.PercentOutput, speedLeft);
		victors.get("intakeRight").set(ControlMode.PercentOutput, speedRight);
	}

	/**
	 * Stops both Intake Victors dead in their tracks.
	 */
	public void stopIntake() {
		setIntake(0.0, 0.0);
	}

	/**
	 * Set Intake tilt Victor speed.
	 * 
	 * @param speedTilt
	 */
	public void setTiltIntake(double speedTilt) {
		victors.get("intakeTilt").set(ControlMode.PercentOutput, speedTilt);
	}

	/**
	 * Stop Intake tilt Victor dead in its tracks.
	 */
	public void stopTiltIntake() {
		setTiltIntake(0.0);
	}

	/**
	 * Sets value of each H solenoid.
	 * 
	 * @param cylinderH
	 * @param cylinderHReverse
	 */
	public void setH(boolean cylinderH, boolean cylinderHReverse) {
		solenoids.get("cylinderH").set(cylinderH);
		solenoids.get("cylinderHReverse").set(cylinderHReverse);
	}

	/**
	 * Sets value of H solenoids.
	 * 
	 * @param boolean
	 */
	public void setH(boolean value) {
		setH(value, !value);
	}

	/**
	 * Toggles H.
	 */
	public void toggleH() {
		setH(!solenoids.get("cylinderH").get());
	}

	/**
	 * Sets both H solenoids to false.
	 */
	public void stopH() {
		setH(false, false);
	}

	/**
	 * Gets camServo object.
	 * 
	 * @return camServo
	 */
	public Servo getCamServo() {
		return camServo;
	}
}