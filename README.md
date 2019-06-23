# voltec-2019-rewritten

<p align="left"><a href="https://github.com/pacoito123/voltec-2019-rewritten" target="_blank"><img src="https://repository-images.githubusercontent.com/184450787/736f4f80-80c8-11e9-975d-573e3fdaae6a"></a></p>

A complete rewrite of team 6647's FRC code from the ground up, implementing many tweaks and cleaning things up.

## To do (for now)

- [ ] Improve OI class.
	- [X] Simplify Button initialization and calling.
	- [X] Config file for controls through JSON.
	- [ ] Add different functionality for different controllers.
		- [ ] Add Joycon support (now easily done thanks to JSON functionality).
- [ ] Implement and improve upon other robot subsystems.
	- [X] General.
		- [X] Create interface for PID subsystems, for updating and outputting values.
	- [X] Chassis.
		- [X] Improve Talon building functionality.
		- [X] Merge Drive velocity-changing commands into one.
		- [X] Clean up the eyesore that is the mapDoubleT method.
	- [X] Midwheel.
		- [X] Move the midwheel to its own subsystem.
		- [X] Merge Slide commands into one.
	- [X] Gyro.
		- [X] Add command to align to closest left or right angle.
		- [X] Add command to align to next closest left or right angle.
	- [X] Elevator.
		- [X] Merge Lift PID commands into one.
		- [X] Merge Lift Manual commands into one.
	- [X] Intake.
		- [X] Merge H control commands into one.
		- [X] Merge Ball in/out commands into one.
		- [X] Remove Unused PID functionality.
	- [X] TiltIntake.
		- [X] Merge Tilt commands into one.
		- [X] Add functionality for non-PS4 controllers.
	- [ ] Climb.
	- [ ] Vision (RIP).
- [X] Generic subsystem object creation through JSON.
	- [X] SuperTalon
	- [X] SuperVictor
	- [X] SuperSolenoid
	- [X] SuperEncoder
	- [X] SuperDigitalInput
	- [X] SuperCompressor (Not really necessary).
	- [X] SuperPDP (Not really necessary).
- [X] Everything commented.
- [ ] Everything tested.

## License

This project is under the BSD License for WPILib code, see: [BSD_License_for_WPILib_code.txt](BSD_License_for_WPILib_code.txt).
