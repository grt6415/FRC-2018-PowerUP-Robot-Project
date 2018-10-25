/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6415.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	public int osolm_pwm = 1;
	public int osagm_pwm = 3;
	public int asolm_pwm = 2;
	public int asagm_pwm = 4;
	public int djoystickport = 0;
	public int cjoystickport = 1;
	public int asansor_pwm = 5;
	public int tirmanma_pwm = 6;
	public int kol_pwm = 0;
	VictorSP osolm = new VictorSP(osolm_pwm);
	VictorSP osagm = new VictorSP(osagm_pwm);
	VictorSP asolm = new VictorSP(asolm_pwm);
	VictorSP asagm = new VictorSP(asagm_pwm);
	SpeedControllerGroup leftm = new SpeedControllerGroup(osolm,asolm);
	SpeedControllerGroup rightm = new SpeedControllerGroup(osagm,asagm);
	DifferentialDrive drive = new DifferentialDrive(leftm,rightm);
	Joystick djoystick = new Joystick(djoystickport);
	Joystick cjoystick = new Joystick(cjoystickport);
	Solenoid kol = new Solenoid(kol_pwm);
	VictorSP asansor = new VictorSP(asansor_pwm);
	VictorSP tirmanma = new VictorSP(tirmanma_pwm);
	DigitalInput man_sensor = new DigitalInput(1);
	private static final String bosotonom = "Bos Otonom";
	private static final String solswitch = "Sol Switch";
	private static final String solcizgiotonom = "Sol Cizgi Otonom";
	private static final String sagcizgiotonom = "Sag Cizgi Otonom";
	private static final String sagswitch = "Sag Switch";
	private static final String solswitchorscale = "Sol Switch yada Scale";
	private static final String sagswitchorscale = "Sag Switch yada Scale";
	private static final String solscaleorswitch = "Sol Scale yada Switch";
	private static final String sagscaleorswitch = "Sag Scale yada Switch";
	private static final String solscale = "Sol Scale";
	private static final String sagscale = "Sag Scale";
	
	private SendableChooser<String> otonom = new SendableChooser<>();

	@Override
	public void robotInit() {
		otonom.addDefault("Bos Otonom",bosotonom);
		otonom.addObject("Sol Cizgi Otonom", solcizgiotonom);
		otonom.addObject("Sag Cizgi Otonom", sagcizgiotonom);
		otonom.addObject("Sol Switch", solswitch);
		otonom.addObject("Sag Switch", sagswitch);
		otonom.addObject("Sol Scale", solscale);
		otonom.addObject("Sag Scale",  sagscale);
		otonom.addObject("Sol Switch yada Scale", solswitchorscale);
		otonom.addObject("Sag Switch yada Scale", sagswitchorscale);
		otonom.addObject("Sol Scale yada Switch", solscaleorswitch);
		otonom.addObject("Sag Scale yada Switch", sagscaleorswitch);
		
		SmartDashboard.putData("Otonom", otonom);
	}
	String gameData;
	char gameSwitch,gameScale;
	public boolean otonoms;
	public void autonomousInit() {
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		gameSwitch = gameData.charAt(0);
		gameScale = gameData.charAt(1);
		otonoms = false;
	}
	public void autonomousPeriodic() {
		String autoSelected = otonom.getSelected();
		System.out.println("Otonom : " + autoSelected);
		drive.setSafetyEnabled(false);
		if(!otonoms) {
			switch (autoSelected) {
				case bosotonom:
					otonoms = true;
					break;
				case solcizgiotonom:
					Timer.delay(1);
					drive.tankDrive(0.7,-0.3); // duzel
					Timer.delay(1.2);
					drive.tankDrive(-1,-1);
					Timer.delay(4);
					drive.tankDrive(0,0);
					otonoms = true;
					break;
				case sagcizgiotonom:
					Timer.delay(1);
					drive.tankDrive(-0.3,0.7); // duzel
					Timer.delay(1.2);
					drive.tankDrive(-1,-1);
					Timer.delay(4);
					drive.tankDrive(0,0);
					otonoms = true;
					break;	
				case solswitch:
					Timer.delay(1);
					if(gameSwitch == 'L') {
						// switch
						drive.tankDrive(-0.7, -0.7);
						Timer.delay(2.3);
						drive.tankDrive(0, 0);
						asansor.set(1);
						Timer.delay(1);
						asansor.set(0.2);
						drive.tankDrive(-0.7, -0.7);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						kol.set(true);
						Timer.delay(1);
						kol.set(false);
						otonoms = true; // tek kup ise okey
						/* 2.kup 
						drive.tankDrive(0.4,0.4);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						asansor.set(-0.5);
						Timer.delay(1);
						drive.tankDrive(-0.7, 0.7);
						Timer.delay(0.5);
						drive.tankDrive(0, 0);
						kol.set(true);
						drive.tankDrive(-0.6, -0.6);
						Timer.delay(1);
						kol.set(false);
						drive.tankDrive(0, 0);
						Timer.delay(0.1);
						drive.tankDrive(0.6, 0.6);
						Timer.delay(1);
						drive.tankDrive(0.7, -0.7);
						Timer.delay(0.5);
						drive.tankDrive(0, 0);
						asansor.set(1);
						Timer.delay(0.7);
						drive.tankDrive(-0.6, -0.6);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						kol.set(true);
						Timer.delay(0.5);
						kol.set(false);
						otonoms = true; */
					}else {
						// cizgi
						Timer.delay(1);
						drive.tankDrive(0.7,-0.3); // duzel
						Timer.delay(1.2);
						drive.tankDrive(-1,-1);
						Timer.delay(4);
						drive.tankDrive(0,0);
						otonoms = true;
					}
					break;
				case sagswitch:
					Timer.delay(1);
					if(gameSwitch == 'R') {
						// switch
						drive.tankDrive(-0.7, -0.7);
						Timer.delay(2.3);
						drive.tankDrive(0, 0);
						asansor.set(1);
						Timer.delay(1);
						asansor.set(0.2);
						drive.tankDrive(-0.7, -0.7);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						kol.set(true);
						Timer.delay(1);
						kol.set(false);
						otonoms = true; // tek kup ise okey
						/* 2.kup 
						drive.tankDrive(0.4,0.4);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						asansor.set(-0.5);
						Timer.delay(1);
						drive.tankDrive(0.7, -0.7);
						Timer.delay(0.5);
						drive.tankDrive(0, 0);
						kol.set(true);
						drive.tankDrive(-0.6, -0.6);
						Timer.delay(1);
						kol.set(false);
						drive.tankDrive(0, 0);
						Timer.delay(0.1);
						drive.tankDrive(0.6, 0.6);
						Timer.delay(1);
						drive.tankDrive(-0.7, 0.7);
						Timer.delay(0.5);
						drive.tankDrive(0, 0);
						asansor.set(1);
						Timer.delay(0.7);
						drive.tankDrive(-0.6, -0.6);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						kol.set(true);
						Timer.delay(0.5);
						kol.set(false);
						otonoms = true; */
					}else {
						// cizgi
						Timer.delay(1);
						drive.tankDrive(-0.3,0.7); // duzel
						Timer.delay(1.2);
						drive.tankDrive(-1,-1);
						Timer.delay(4);
						drive.tankDrive(0,0);
						otonoms = true;
					}
					break;
				case solscale:
					Timer.delay(1);
					if(gameScale == 'L') {
						// scale
						drive.tankDrive(0.7,-0.3); // duzel
						Timer.delay(1.2);
						drive.tankDrive(-0.7, -0.7);
						Timer.delay(3);
						drive.tankDrive(0, 0);
						asansor.set(1);
						Timer.delay(1);
						drive.tankDrive(-0.5, -0.5);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						kol.set(true);
						Timer.delay(1);
						kol.set(false);
						otonoms = true; 
						
					}else {
						// cizgi
						Timer.delay(1);
						drive.tankDrive(0.7,-0.3); // duzel
						Timer.delay(1.2);
						drive.tankDrive(-1,-1);
						Timer.delay(4);
						drive.tankDrive(0,0);
						otonoms = true;
					}
					break;
				case sagscale:
					Timer.delay(1);
					if(gameScale == 'R') {
						// scale
						drive.tankDrive(0.3,0.7); // duzel
						Timer.delay(1.2);
						drive.tankDrive(-0.7, -0.7);
						Timer.delay(3);
						drive.tankDrive(0, 0);
						asansor.set(1);
						Timer.delay(1);
						drive.tankDrive(-0.5, -0.5);
						Timer.delay(1);
						drive.tankDrive(0, 0);
						kol.set(true);
						Timer.delay(1);
						kol.set(false);
						otonoms = true; 
						
					}else {
						// cizgi
						Timer.delay(1);
						drive.tankDrive(0.3,0.7); // duzel
						Timer.delay(1.2);
						drive.tankDrive(-1,-1);
						Timer.delay(4);
						drive.tankDrive(0,0);
						otonoms = true;
					}
					break;
				default:
					// Stop robot
					drive.arcadeDrive(0.0, 0.0);
					break;
			}
		}
	}
	/*Timer timer = new Timer();
	
	public void autonomousInit() {
	    timer.reset();
	    timer.start();
	}

	public void autonomousPeriodic() {
	    if (timer.get() < 2.0) {
	        drive.arcadeDrive(-0.5, 0.0); 
	    } else if (timer.get() < 5.0) {
	        drive.arcadeDrive(-1.0, 0.0); 
	    } else {
	       drive.arcadeDrive(0.0, 0.0); 
	    }
	}*/
	int koltuspwm = 4;
	int tirmanmatuspwm = 2;
	int asansorkaldirtuspwm = 1;
	int asansorindirtuspwm = 3;
	int hiztuspwm = 5;
	int yavtuspwm = 6;
	double SABIT_asagivalue = -0.5;
	double SABIT_yukarivalue = 1.0;
	double SABIT_tutusvalue = 0.2;
	double yukarivalue = 1.0;
	double asagivalue = -0.5;
	double tutusvalue = 0.1;
	double hiz = -1;
	double yav = -0.5;
	double hizvalue = -0.8;
	boolean koltus,tirmanmatus,akaldirtus,aindirtus,hiztus,yavtus;
	@Override
	public void teleopPeriodic() {
		drive.setSafetyEnabled(true);
		drive.arcadeDrive(djoystick.getY(), djoystick.getX());
		koltus = cjoystick.getRawButton(koltuspwm);
		tirmanmatus = cjoystick.getRawButton(tirmanmatuspwm);
		akaldirtus = cjoystick.getRawButton(asansorkaldirtuspwm);
		aindirtus = cjoystick.getRawButton(asansorindirtuspwm);
		hiztus = djoystick.getRawButton(hiztuspwm);
		yavtus = djoystick.getRawButton(yavtuspwm);

		if(hiztus) {
			drive.arcadeDrive(-djoystick.getY() * hiz, -djoystick.getX() * hiz);
		}
		if(yavtus) {
			drive.arcadeDrive(-djoystick.getY() * yav, -djoystick.getX() * yav);
		}
		if(!yavtus && !hiztus) {
			drive.arcadeDrive(-djoystick.getY() * hizvalue, -djoystick.getX() * hizvalue);
		}
		if(man_sensor.get()) {
			asagivalue = 0;
		}else {
			asagivalue = SABIT_asagivalue;
		}
		if(akaldirtus) {
			asansor.set(yukarivalue);
		}
		if(aindirtus) {
			asansor.set(asagivalue);
		}
		if(!aindirtus && !akaldirtus) {
			asansor.set(tutusvalue);
		}
		
		if(koltus) {
			kol.set(true);
		}else {
			kol.set(false);
		}
		if(tirmanmatus) {
			tirmanma.set(1);
		}else {
			tirmanma.set(0);
		}
	}

	@Override
	public void testPeriodic() {
	}
}
