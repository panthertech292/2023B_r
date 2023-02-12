// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class DualArmControl extends CommandBase {
  private final ArmSubsystem ArmSubsystem;
  private double v_lowTarget;
  private double v_lowP;
  private double v_lowMinSpeed;
  private double v_lowError;

  private double v_highTarget;
  private double v_highP;
  private double v_highMinSpeed;
  private double v_highError;
  /** Creates a new DualArmControl. */
  public DualArmControl(ArmSubsystem s_ArmSubsystem, double lowtarget, double lowp, double lowminspeed, double hightarget, double highp, double highminspeed) {
    ArmSubsystem = s_ArmSubsystem;
    v_lowTarget = lowtarget;
    v_lowP = lowp;
    v_lowMinSpeed = lowminspeed;

    v_highTarget = hightarget;
    v_highP = highp;
    v_highMinSpeed = highminspeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_ArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    v_lowError = (v_lowTarget - ArmSubsystem.getLowerArmEncoder())*v_lowP;
    v_highError = (v_highTarget - ArmSubsystem.getUpperArmEncoder())*v_highP;

    //Low Arm Checks
    if (Math.abs(v_lowMinSpeed) > Math.abs(v_lowError)){
      if (v_lowError > 0){
        v_lowError = v_lowMinSpeed;
      }
      else{
        v_lowError = -v_lowMinSpeed;
      }
    }
    if (Math.abs(v_lowTarget - ArmSubsystem.getLowerArmEncoder()) < 0.008){
      v_lowError = 0;
    }

    //UP Arm checks
    if (Math.abs(v_highMinSpeed) > Math.abs(v_highError)){
      if (v_highError > 0){
        v_highError = v_highMinSpeed;
      }
      else{
        v_highError = -v_highMinSpeed;
      }
    }
    if (Math.abs(v_highTarget - ArmSubsystem.getUpperArmEncoder()) < 0.008){
      v_highError = 0;
    }

    ArmSubsystem.setLowerArmMotorSpeed(v_lowError);
    ArmSubsystem.setUpperArmMotorSpeed(v_highError);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ArmSubsystem.setLowerArmMotorSpeed(0);
    ArmSubsystem.setUpperArmMotorSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
