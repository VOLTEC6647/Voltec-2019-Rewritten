package org.usfirst.lib6647.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Interface for PID outputting and updating.
 */
public interface PID {

	/**
	 * Method to output PID values to the SmartDashboard.
	 * 
	 * @param subsystemName
	 * @param p
	 * @param i
	 * @param d
	 */
	default void outputPIDValues(String subsystemName, double p, double i, double d) {
		SmartDashboard.putNumber(subsystemName + "P", p);
		SmartDashboard.putNumber(subsystemName + "I", i);
		SmartDashboard.putNumber(subsystemName + "D", d);
	}

	/**
	 * Abstract method to update PID values from the SmartDashboard.
	 */
	abstract void updatePIDValues();
}