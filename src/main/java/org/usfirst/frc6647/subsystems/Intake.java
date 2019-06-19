package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.SuperDigitalInput;
import org.usfirst.lib6647.subsystem.SuperSolenoid;
import org.usfirst.lib6647.subsystem.SuperSubsystem;
import org.usfirst.lib6647.subsystem.SuperVictor;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Subsystem for the Intake.
 */
public class Intake extends SuperSubsystem implements SuperVictor, SuperSolenoid, SuperDigitalInput {

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
		if (m_instance == null) {
			createInstance();
		}
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public Intake() {
		super("intake", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initVictors(robotMap, getName());
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
	 * Sets value of H solenoids.
	 * 
	 * @param value
	 */
	public void setH(boolean value) {
		setH(value, !value);
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
	 * Sets both H solenoids to false.
	 */
	public void stopH() {
		setH(false, false);
	}

	/**
	 * Sets value of pushHatch solenoid.
	 * 
	 * @param value
	 */
	public void pushHatch(boolean value) {
		solenoids.get("pushHatch").set(value);
	}
}