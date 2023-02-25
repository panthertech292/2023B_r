// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;


public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */
  //Low Arm
  private final DoubleSolenoid LowerArmCylinder;
  //Up Arm
  private final CANSparkMax UpperArmMotor;
  private final DutyCycleEncoder UpperArmEncoder;
  private RelativeEncoder UpperArmMotorEncoder;
  private double v_upperArmSpeed;

  public ArmSubsystem() {
    LowerArmCylinder = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, ArmConstants.kLowerArmSolenoidRetractChannel, ArmConstants.kLowerARmSolenoidExtendChannel);

    UpperArmMotor = new CANSparkMax(ArmConstants.kUpperArmMotor, MotorType.kBrushless);
    UpperArmMotor.restoreFactoryDefaults();
    UpperArmMotor.setIdleMode(IdleMode.kBrake);
    UpperArmMotor.burnFlash();
    
    UpperArmEncoder = new DutyCycleEncoder(ArmConstants.kUpperArmEncoderChannel); //PLACE HOLDER
    UpperArmMotorEncoder = UpperArmMotor.getEncoder();
  }
  public void setLowerArmCylinderExtended(){
    LowerArmCylinder.set(Value.kForward);
  }
  public void setLowerArmCylinderRetracted(){
    LowerArmCylinder.set(Value.kReverse);
  }
  public void setLowerArmCylinderOff(){
    LowerArmCylinder.set(Value.kOff);
  }

  public void setUpperArmMotorSpeed(double upperarmspped){
    v_upperArmSpeed = upperarmspped;
    //System.out.println("IN HERE: " + v_upperArmSpeed + " " + "RPM: " + UpperArmMotorEncoder.getVelocity());
    UpperArmMotor.set(-v_upperArmSpeed);
    
    //System.out.println("UP: " + UpperArmEncoder.getAbsolutePosition());
    //System.out.println("POWER: " + UpperArmMotor.get());
    //System.out.println("RPM: " + UpperArmMotorEncoder.getVelocity());
  }
  public double getUpperArmEncoder(){
    return UpperArmEncoder.getAbsolutePosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println("UP: " + UpperArmEncoder.getAbsolutePosition());
    
  }
}
