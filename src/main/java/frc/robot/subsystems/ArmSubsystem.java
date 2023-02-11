// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;


public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */
  //Low Arm
  private final CANSparkMax LowerArmMotor;
  private final DutyCycleEncoder LowerArmEncoder;
  private RelativeEncoder LowerArmMotorEncoder;
  private double v_lowerArmSpeed;
  //Up Arm
  private final CANSparkMax UpperArmMotor;
  private final DutyCycleEncoder UpperArmEncoder;
  private RelativeEncoder UpperArmMotorEncoder;
  private double v_upperArmSpeed;

  public ArmSubsystem() {
    LowerArmMotor = new CANSparkMax(ArmConstants.kLowerArmMotor, MotorType.kBrushless);
    LowerArmMotor.restoreFactoryDefaults();
    LowerArmMotor.setIdleMode(IdleMode.kBrake);
    LowerArmMotor.burnFlash();
    LowerArmEncoder = new DutyCycleEncoder(0);
    LowerArmMotorEncoder = LowerArmMotor.getEncoder();

    UpperArmMotor = new CANSparkMax(0, MotorType.kBrushless);
    UpperArmMotor.restoreFactoryDefaults();
    UpperArmMotor.setIdleMode(IdleMode.kBrake);
    UpperArmMotor.burnFlash();
    UpperArmEncoder = new DutyCycleEncoder(1); //PLACE HOLDER
    UpperArmMotorEncoder = UpperArmMotor.getEncoder();
  }
  public void setLowerArmMotorSpeed(double lowerarmspeed){
    v_lowerArmSpeed = lowerarmspeed;
    System.out.println("IN HERE: " + v_lowerArmSpeed + " " + "RPM: " + LowerArmMotorEncoder.getVelocity());
    LowerArmMotor.set(v_lowerArmSpeed);
  }
  public void setUpperArmMotorSpeed(double upperarmspped){
    v_upperArmSpeed = upperarmspped;
    UpperArmMotor.set(v_upperArmSpeed);
  }
  public double getLowerArmEncoder(){
    return LowerArmEncoder.getAbsolutePosition();
  }
  public double getUpperArmEncoder(){
    return UpperArmEncoder.getAbsolutePosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println(LowerArmEncoder.getAbsolutePosition());
    
  }
}