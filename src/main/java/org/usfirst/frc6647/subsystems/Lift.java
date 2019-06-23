package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;
import org.usfirst.lib6647.subsystem.components.SuperDigitalInput;
import org.usfirst.lib6647.subsystem.components.SuperEncoder;
import org.usfirst.lib6647.subsystem.components.SuperVictor;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for the Lift.
 */
public class Lift extends PIDSuperSubsystem implements SuperVictor, SuperEncoder, SuperDigitalInput {

	private double p = 0.00004, i = 0.0000009015, d = 0.000132;

	private static Lift m_instance = null;

	/**
	 * Creates static Lift instance.
	 */
	public static void createInstance() {
		m_instance = new Lift();
	}

	/**
	 * Gets static Lift instance. If there is none, creates one.
	 * 
	 * @return static Lift instance
	 */
	public static Lift getInstance() {
		if (m_instance == null)
			createInstance();
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public Lift() {
		super("lift", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initVictors(robotMap, getName());
		initEncoders(robotMap, getName());
		initDigitalInputs(robotMap, getName());

		victors.get("liftFollower").follow(victors.get("liftMain"));

		getPIDController().setPID(p, i, d);
		setInputRange(-3600000, 360000);
		setOutputRange(-0.8, 0.8);
		setAbsoluteTolerance(200);
		getPIDController().setContinuous(true);

		outputPIDValues(getName(), p, i, d);
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		SmartDashboard.putNumber("Lift Encoder Value", encoders.get("liftEncoder").get());
		SmartDashboard.putBoolean("Encoder Limit Down", digitalInputs.get("lowLimitLift").get());
		if (!digitalInputs.get("lowLimitLift").get())
			encoders.get("liftEncoder").reset();
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Gets input for PID.
	 * 
	 * @return pidInput
	 */
	@Override
	protected double returnPIDInput() {
		return encoders.get("liftEncoder").get();
	}

	/**
	 * Sets liftMain Victor to the PID's calculated speed.
	 */
	@Override
	protected void usePIDOutput(double output) {
		setLift(output);
	}

	/**
	 * Method to update PID values from the SmartDashboard.
	 */
	@Override
	public void updatePIDValues() {
		p = SmartDashboard.getNumber(getName() + "P", p);
		i = SmartDashboard.getNumber(getName() + "I", i);
		d = SmartDashboard.getNumber(getName() + "D", d);

		getPIDController().setPID(p, i, d);

		SmartDashboard.putNumber("debug" + getName() + "P", getPIDController().getP());
		SmartDashboard.putNumber("debug" + getName() + "I", getPIDController().getI());
		SmartDashboard.putNumber("debug" + getName() + "D", getPIDController().getD());
	}

	/**
	 * Sets liftMain Victor to a specific speed, in PercentOutput.
	 * 
	 * @param speed
	 */
	public void setLift(double speed) {
		victors.get("liftMain").set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Stops the liftMain Victor dead in its tracks.
	 */
	public void stopLift() {
		setLift(0.0);
	}

	/**
	 * Gets lowLimitLift DigitalInput value, aka downLimit.
	 * 
	 * @return downLimit
	 */
	public boolean getDownLimitValue() {
		return digitalInputs.get("lowLimitLift").get();
	}

	/**
	 * Gets Lift Encoder value.
	 * 
	 * @return liftEncoder
	 */
	public int getEncoderValue() {
		return encoders.get("liftEncoder").get();
	}

	/**
	 * Resets Lift Encoder.
	 */
	public void resetEncoder() {
		encoders.get("liftEncoder").reset();
	}
}