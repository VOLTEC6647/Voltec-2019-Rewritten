# voltec-2019-rewritten

<p align="left"><a href="https://github.com/pacoito123/voltec-2019-rewritten" target="_blank"><img src="https://repository-images.githubusercontent.com/184450787/736f4f80-80c8-11e9-975d-573e3fdaae6a"></a></p>

A complete rewrite of team 6647's FRC code from the ground up, implementing many tweaks and cleaning things up.

## To do (for now)

- [ ] Improve OI class.
	- [X] Simplify Button initialization and calling.
	- [ ] Add different functionality for different controllers.
		- [X] Config file for controls through JSON.
		- [ ] Add PS4 axis bumper acceleration to other controllers.
		- [ ] Add Joycon support (now easily done thanks to JSON functionality).
- [ ] Implement and improve upon other robot subsystems.
	- [X] General.
		- [X] Create interface for PID subsystems, for updating and outputting values.
	- [ ] Chassis.
		- [X] Improve Talon building functionality.
		- [ ] Clean up the eyesore that is the mapDoubleT method.
	- [X] Midwheel.
		- [X] Move the midwheel to its own subsystem.
		- [X] Merge Slide commands into one.
	- [X] Gyro.
		- [X] Add command to align to closest left or right angle.
		- [X] Add command to align to next closest left or right angle.
	- [ ] Elevator.
	- [ ] Intake.
	- [ ] Climb.
	- [ ] Pneumatics.
	- [ ] Vision (RIP).
- [ ] Generic subsystem object creation through JSON.
	- [X] SuperTalon
	- [X] SuperVictor
	- [X] SuperSolenoid
	- [ ] SuperEncoder
	- ...
- [X] Everything commented (keep it that way!).
- [X] Everything tested.

## License

This project is under the BSD License for WPILib code, see: [BSD_License_for_WPILib_code.txt](BSD_License_for_WPILib_code.txt).
