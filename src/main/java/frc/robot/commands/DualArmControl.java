// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class DualArmControl extends CommandBase {
  private final ArmSubsystem ArmSubsystem;
  private boolean v_setExtended;
  private double v_target;
  private double v_p;
  private double v_minSpeed;
  private double v_error;
  /** Creates a new DualArmControl. */
  public DualArmControl(ArmSubsystem s_ArmSubsystem, boolean setExtended, double target, double p, double minspeed) {
    ArmSubsystem = s_ArmSubsystem;
    v_setExtended = setExtended;
    v_target = target;
    v_p = p;
    v_minSpeed = minspeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_ArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Lower Arm Control
    //Lower arm control is in the initializer because the solenoid only needs to be set once
    if(v_setExtended){
      ArmSubsystem.setLowerArmCylinderExtended();
    }else{
      ArmSubsystem.setLowerArmCylinderRetracted();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //Up Arm Control
    v_error = ((v_target - ArmSubsystem.getUpperArmEncoder())*v_p);
    
    if (Math.abs(v_minSpeed) > Math.abs(v_error)){
      if (v_error > 0){
        v_error = v_minSpeed;
      }
      else{
        v_error = -v_minSpeed;
      }
    }
    if (Math.abs(v_target - ArmSubsystem.getUpperArmEncoder()) < 0.008){
      v_error = 0;
    }
    

    ArmSubsystem.setUpperArmMotorSpeed(v_error);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ArmSubsystem.setUpperArmMotorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
