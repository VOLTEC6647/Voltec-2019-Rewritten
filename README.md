# voltec-2019-rewritten

<p align="left"><a  href="https://github.com/pacoito123/voltec-2019-rewritten"  target="_blank"><img  src="https://i.imgur.com/F4focyC.png"></a></p>

A complete rewrite of team 6647's FRC code from the ground up, implementing many tweaks and cleaning things up.

## To do (for now)

- [ ] Improve OI class.
	- [X] Simplify Button initialization and calling.
	- [X] Allow config file for controls through JSON.
	- [ ] Add different functionality for non-PS4 controllers.
		- [ ] Add Joycon support (now easily done thanks to JSON functionality).
		- [ ] Add Guitar Hero controller support.
- [ ] Implement and improve upon other robot subsystems.
	- [x] Chassis.
		- [x] Improve Talon building functionality.
		- [x] Merge Drive velocity-changing commands into one.
		- [x] Clean up the eyesore that is the mapDoubleT method.
	- [x] Midwheel.
		- [x] Move the midwheel to its own subsystem.
		- [x] Merge Slide commands into one.
		- [x] Add axis speed functionality for non-PS4 controllers.
	- [x] Gyro.
		- [x] Add command to align to closest left or right angle.
		- [ ] Add command to align to next closest left or right angle (Needs PID adjustments).
	- [x] Elevator.
		- [x] Merge Lift PID commands into one.
		- [x] Merge Lift Manual commands into one.
	- [x] Intake.
		- [x] Merge H control commands into one.
		- [x] Merge Ball in/out commands into one.
		- [x] Remove Unused PID functionality.
	- [x] TiltIntake.
		- [x] Merge Tilt commands into one.
		- [x] Add tilt functionality for non-PS4 controllers.
	- [ ] Climb.
		 - [ ] Merge Climb commands into one.
		 - [ ] Remove need of arcade thingy.
	- [ ] Vision (RIP).
- [ ] Overall code optimization.
	- [X] Restrict PID update loops to run only when enabled.
	- [X] Remove any and all instances of memory leaks.
	- [ ] Do proper Exception handling.
- [X] Add comments.
- [ ] Test everything.

## License

This project is under the BSD License for WPILib code, see: [BSD_License_for_WPILib_code.txt](BSD_License_for_WPILib_code.txt).
