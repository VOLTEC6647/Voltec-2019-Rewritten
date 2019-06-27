package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.components.SuperSolenoid;
import org.usfirst.lib6647.subsystem.components.SuperTalon;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Servo;

/**
 * Subsystem for the Intake.
 */
public class Intake extends SuperSubsystem implements SuperTalon, SuperSolenoid {

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

		initTalons(robotMap, getName());
		initSolenoids(robotMap, getName());

		finishedJSONInit();
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Sets both Intake Talons to the given speed, in PercentOutput.
	 * 
	 * @param speedLeft
	 * @param speedRight
	 */
	public void setIntake(double speedLeft, double speedRight) {
		talons.get("intakeLeft").set(ControlMode.PercentOutput, speedLeft);
		talons.get("intakeRight").set(ControlMode.PercentOutput, speedRight);
	}

	/**
	 * Stops both Intake Talons dead in their tracks.
	 */
	public void stopIntake() {
		setIntake(0.0, 0.0);
	}

	/**
	 * Sets value of pushHatch solenoids.
	 * 
	 * @param boolean
	 * @param boolean
	 */
	public void setH(boolean value0, boolean value1) {
		solenoids.get("pushHatch0").set(value0);
		solenoids.get("pushHatch1").set(value1);
	}

	/**
	 * Toggles H.
	 */
	public void toggleH() {
		setH(!solenoids.get("pushHatch0").get(), !solenoids.get("pushHatch1").get());
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