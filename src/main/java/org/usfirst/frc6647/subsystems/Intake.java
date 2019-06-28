package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.components.SuperCompressor;
import org.usfirst.lib6647.subsystem.components.SuperDigitalInput;
import org.usfirst.lib6647.subsystem.components.SuperSolenoid;
import org.usfirst.lib6647.subsystem.components.SuperVictor;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Servo;

/**
 * Subsystem for the Intake.
 */
public class Intake extends SuperSubsystem implements SuperVictor, SuperCompressor, SuperSolenoid, SuperDigitalInput {

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
		initCompressors(robotMap, getName());
		initSolenoids(robotMap, getName());
		initDigitalInputs(robotMap, getName());

		finishedJSONInit();
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
	 * Sets value of pushHatch solenoid.
	 * 
	 * @param boolean
	 */
	public void setH(boolean value) {
		solenoids.get("pushHatch").set(value);
	}

	/**
	 * Toggles H.
	 */
	public void toggleH() {
		setH(!solenoids.get("pushHatch").get());
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