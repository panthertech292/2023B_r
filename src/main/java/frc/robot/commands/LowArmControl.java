// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmSubsystem;

public class LowArmControl extends CommandBase {
  private final ArmSubsystem ArmSubsystem;
  private double v_target;
  private double v_p;
  private double v_minSpeed;
  private double v_error;
  /** Creates a new ArmTest. */
  public LowArmControl(ArmSubsystem s_ArmSubsystem, double target, double p, double minspeed) {
    ArmSubsystem = s_ArmSubsystem;
    v_target = target;
    v_p = p;
    v_minSpeed = minspeed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_ArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    v_error = (v_target - ArmSubsystem.getLowerArmEncoder())*v_p;
    
    if (Math.abs(v_minSpeed) > Math.abs(v_error)){
      if (v_error > 0){
        v_error = v_minSpeed;
      }
      else{
        v_error = -v_minSpeed;
      }
    }
    if (Math.abs(v_target - ArmSubsystem.getLowerArmEncoder()) < 0.008){
      v_error = 0;
    }


    ArmSubsystem.setLowerArmMotorSpeed(v_error);
    System.out.println("ARM IS @: " + ArmSubsystem.getLowerArmEncoder());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ArmSubsystem.setLowerArmMotorSpeed(0);
    //System.out.println("END ARM");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
