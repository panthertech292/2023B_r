// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.WPI_Pigeon2;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  /** Creates a new DriveSubsystem. */

  private final WPI_TalonSRX FrontLeftMotor;
  private final WPI_TalonSRX FrontRightMotor;
  private final WPI_TalonSRX BackLeftMotor;
  private final WPI_TalonSRX BackRightMotor;

  private WPI_Pigeon2 Pigeon2;

  private final MotorControllerGroup LeftSide;
  private final MotorControllerGroup RightSide;
  private final DifferentialDrive DifDrive;

  private double v_leftSpeed;
  private double v_rightSpeed;
  private double v_leftXSpeed;
  private double v_rightYSpeed;

  public DriveSubsystem() {

    FrontLeftMotor = new WPI_TalonSRX(DriveConstants.kFrontLeftMotor);
    FrontRightMotor = new WPI_TalonSRX(DriveConstants.kFrontRightMotor);
    BackLeftMotor = new WPI_TalonSRX(DriveConstants.kBackLeftMotor);
    BackRightMotor = new WPI_TalonSRX(DriveConstants.kBackRightMotor);//n u m b e r

    Pigeon2 = new WPI_Pigeon2(DriveConstants.kPigeon2);

    LeftSide = new MotorControllerGroup(FrontLeftMotor, BackLeftMotor);
    RightSide = new MotorControllerGroup(FrontRightMotor, BackRightMotor);
    DifDrive = new DifferentialDrive(LeftSide,RightSide);

    //Pigeon2.reset();
  }
  public double getPitch(){
    return Pigeon2.getRoll();
  }

  public void differentialArcadeDrive(double forBackSpeed, double rotateSpeed){
    v_leftXSpeed = forBackSpeed;
    v_rightYSpeed = -rotateSpeed;
    DifDrive.arcadeDrive(v_leftXSpeed, v_rightYSpeed);
  }
  public void differentialTankDrive(double leftSpeed, double rightSpeed){
    v_leftSpeed = leftSpeed;
    v_rightSpeed = -rightSpeed;

    DifDrive.tankDrive(v_leftSpeed, v_rightSpeed, false);
    //System.out.println(FrontLeftMotor.get());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    //System.out.println("YAW: " + Pigeon2.getYaw());
    //System.out.println("ROLL: " + Pigeon2.getRoll());
    //System.out.println("PITCH: " + Pigeon2.getPitch());
  }
}
