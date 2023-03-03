// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;

public class DualArmManual extends CommandBase {
  private final ArmSubsystem ArmSubsystem;
  /** Creates a new LowArmManual. */
  public DualArmManual(ArmSubsystem s_ArmSubsystem) {
    ArmSubsystem = s_ArmSubsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(s_ArmSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ArmSubsystem.setUpperArmMotorSpeed(RobotContainer.getOperRightSpeed());
    
    //Extend or Retract the low arm based on joystick position
    if (RobotContainer.getOperLeftSpeed() > .15){
      ArmSubsystem.setLowerArmCylinderExtended();
    }
    if (RobotContainer.getOperLeftSpeed() < -0.15){
      ArmSubsystem.setLowerArmCylinderRetracted();
    }
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
