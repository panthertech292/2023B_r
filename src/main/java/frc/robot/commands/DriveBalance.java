// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;

public class DriveBalance extends CommandBase {
  private final DriveSubsystem DriveSubsystem;
  private double v_p;
  private double v_minSpeed;
  private double v_error;
  /** Creates a new DriveBalance. */
  public DriveBalance(DriveSubsystem s_DriveSubsystem, double p, double minspeed) {
    v_p = p;
    v_minSpeed = minspeed;
    DriveSubsystem = s_DriveSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_DriveSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    v_error = (DriveSubsystem.getPitch())*v_p;
    
    /*
    if (DriveSubsystem.getPitch() > 2){
      DriveSubsystem.differentialTankDrive(0.40, 0.40);
      System.out.println("GOING FORWARD!");
    }
    if (DriveSubsystem.getPitch() < -2){
      DriveSubsystem.differentialTankDrive(-0.40, -0.40);
      System.out.println("GOING BACK!");
    }*/
    if (DriveSubsystem.getPitch() > -3 && DriveSubsystem.getPitch() < 3){
      DriveSubsystem.differentialTankDrive(0, 0);
    }else{
      if (Math.abs(v_minSpeed) > Math.abs(v_error)){
        if (v_error > 0){
          v_error = v_minSpeed;
        }
        else{
          v_error = -v_minSpeed;
        }
      }
      System.out.println(v_error);
      DriveSubsystem.differentialTankDrive(v_error, v_error);
    }

  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    DriveSubsystem.differentialTankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
