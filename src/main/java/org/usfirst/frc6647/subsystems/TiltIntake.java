package org.usfirst.frc6647.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import org.usfirst.lib6647.subsystem.PIDSuperSubsystem;
import org.usfirst.lib6647.subsystem.SuperDigitalInput;
import org.usfirst.lib6647.subsystem.SuperVictor;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Subsystem for PID control of Intake pivot.
 */
public class TiltIntake extends PIDSuperSubsystem implements SuperVictor, SuperDigitalInput {

	private double p = 2.95, i = 0.3, d = 0.22;

	private AnalogPotentiometer analogPOT; // What are these two for?
	private Servo camServo;

	private static TiltIntake m_instance = null;

	/**
	 * Creates static TiltIntake instance.
	 */
	public static void createInstance() {
		m_instance = new TiltIntake();
	}

	/**
	 * Gets static TiltIntake instance. If there is none, creates one.
	 * 
	 * @return static TiltIntake instance
	 */
	public static TiltIntake getInstance() {
		if (m_instance == null) {
			createInstance();
		}
		return m_instance;
	}

	/**
	 * Constructor for the subsystem.
	 */
	public TiltIntake() {
		super("tiltIntake", Filesystem.getDeployDirectory() + "/RobotMap.json");

		initVictors(robotMap, getName());

		getPIDController().setPID(p, i, d);
		setInputRange(.920, 1);
		setOutputRange(-1, 1);
		setAbsoluteTolerance(.00005);
		getPIDController().setContinuous(true);

		analogPOT = new AnalogPotentiometer(1, 3600, -8);
		camServo = new Servo(0);
	}

	/**
	 * Runs every time Scheduler.getInstance().run() is called.
	 */
	@Override
	public void periodic() {
		updatePIDValues();
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Set tiltIntake Victor speed.
	 * 
	 * @param speed
	 */
	public void setTiltIntake(double speed) {
		victors.get("tiltIntake").set(ControlMode.PercentOutput, speed);
	}

	/**
	 * Stop tiltIntake Victor dead in its tracks.
	 */
	public void stopTiltIntake() {
		setTiltIntake(0.0);
	}

	/**
	 * Gets input for PID.
	 * 
	 * @return pidInput
	 */
	@Override
	protected double returnPIDInput() {
		return analogPOT.pidGet() - .920;
	}

	/**
	 * Sets tiltIntake Victor to the PID's calculated speed.
	 */
	@Override
	protected void usePIDOutput(double output) {
		setTiltIntake(output);
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