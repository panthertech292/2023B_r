// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.*;
import frc.robot.commands.Auto.BasicAuto;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.PickupSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  private final static XboxController io_drivercontroller = new XboxController(OperatorConstants.kDriverControllerPort);
  private final static XboxController io_opercontroller = new XboxController(OperatorConstants.kOperatorControllerPort);
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem s_DriverSubsystem = new DriveSubsystem();
  private final ArmSubsystem s_ArmSubsystem = new ArmSubsystem();
  private final PickupSubsystem s_PickupSubsystem = new PickupSubsystem();

  //Drive Commands
  private final Command z_DriveTeleop = new DriveTeleop(s_DriverSubsystem);
  private final Command z_DriveBalance = new DriveBalance(s_DriverSubsystem, 0.015, 0.25);

  //Arm Commands
  //Both Arms
  private final Command z_DualArmManual = new DualArmManual(s_ArmSubsystem);
  private final Command z_DualArmPickupSpot = new DualArmControl(s_ArmSubsystem, false,0.33,9,0.15);
  private final Command z_DualArmScoreSpot = new DualArmControl(s_ArmSubsystem, true, 0.13, 9, 0.15);
  private final Command z_DualArmStowedSpot = new DualArmControl(s_ArmSubsystem, false, 0.49, 9, 0.15);
  private final Command z_DualArmFloorSpot = new DualArmControl(s_ArmSubsystem, true, 0.42, 9, 0.15);
  //Low Arm
  //private final Command z_LowArmExtend = new LowArmExtend(s_ArmSubsystem);
  //private final Command z_LowArmRetract = new LowArmRetract(s_ArmSubsystem);
  //Up Arm
  //private final Command z_UpperArmControl_In = new UpArmControl(s_ArmSubsystem, 0.525, 9, 0.3);
  //private final Command z_UpperArmControl_Out = new UpArmControl(s_ArmSubsystem, 0.029, 9, 0.3);

  //Auto Commands
  private final Command z_BasicAuto = new BasicAuto(s_DriverSubsystem, s_ArmSubsystem, s_PickupSubsystem);


  //Pickup Commands
  private final Command z_ClawOpen = new ClawOpen(s_PickupSubsystem);
  private final Command z_ClawClose = new ClawClose(s_PickupSubsystem);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    CameraServer.startAutomaticCapture().setFPS(20);
    configureBindings();
    s_DriverSubsystem.setDefaultCommand(z_DriveTeleop);
    s_ArmSubsystem.setDefaultCommand(z_DualArmManual);
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */

  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`
    
    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //Driver Buttons
    final JoystickButton d_aButton = new JoystickButton(io_drivercontroller, Button.kA.value);
    d_aButton.whileTrue(z_DualArmPickupSpot);
    final JoystickButton d_bButton = new JoystickButton(io_drivercontroller, Button.kB.value);
    d_bButton.whileTrue(z_DualArmScoreSpot);
    final JoystickButton d_xButton = new JoystickButton(io_drivercontroller, Button.kX.value);
    d_xButton.whileTrue(z_DualArmStowedSpot);
    final JoystickButton d_yButton = new JoystickButton(io_drivercontroller, Button.kY.value);
    d_yButton.whileTrue(z_DualArmFloorSpot);
    final JoystickButton d_startButton = new JoystickButton(io_drivercontroller, Button.kStart.value);
    d_startButton.whileTrue(z_DriveBalance);
    final JoystickButton d_backButton = new JoystickButton(io_drivercontroller, Button.kBack.value);
    //d_backButton.whileTrue(z_ArmRetract);
    final JoystickButton d_rightBumper = new JoystickButton(io_drivercontroller, Button.kRightBumper.value);
    d_rightBumper.onTrue(z_ClawClose);
    final JoystickButton d_leftBumper = new JoystickButton(io_drivercontroller, Button.kLeftBumper.value);
    d_leftBumper.onTrue(z_ClawOpen);

    //Operator Buttons
    final JoystickButton o_rightBumper = new JoystickButton(io_opercontroller, Button.kRightBumper.value);
    o_rightBumper.onTrue(z_ClawClose);
    final JoystickButton o_leftBumper = new JoystickButton(io_opercontroller, Button.kLeftBumper.value);
    o_leftBumper.onTrue(z_ClawOpen);
    final JoystickButton o_aButton = new JoystickButton(io_opercontroller, Button.kA.value);
    o_aButton.whileTrue(z_DualArmPickupSpot); 
    final JoystickButton o_bButton = new JoystickButton(io_opercontroller, Button.kB.value);
    o_bButton.whileTrue(z_DualArmScoreSpot);
    final JoystickButton o_xButton = new JoystickButton(io_opercontroller, Button.kX.value);
    o_xButton.whileTrue(z_DualArmStowedSpot);
    final JoystickButton o_yButton = new JoystickButton(io_opercontroller, Button.kY.value);
    o_yButton.whileTrue(z_DualArmFloorSpot);

  }
  public static double deadZoneCheck(double rawControllerInput){
    if (rawControllerInput > OperatorConstants.kControllerDeadZone || rawControllerInput < -OperatorConstants.kControllerDeadZone){
      return rawControllerInput;
    }
    else{
      return 0;
    }
    
  }

  public static double deadZoneCheck2(double rawControllerInput){
    if (rawControllerInput > OperatorConstants.kControllerDeadZone2 || rawControllerInput < -OperatorConstants.kControllerDeadZone2){
      return rawControllerInput;
    }
    else{
      return 0;
    }
    
  }

  public static double getDriverLeftSpeed(){
    return deadZoneCheck2(io_drivercontroller.getLeftY());
  }
  public static double getDriverRightSpeed() {
    return deadZoneCheck(io_drivercontroller.getRightY());
  }
  public static double getDriverLeftSpeedX(){
    return deadZoneCheck2(io_drivercontroller.getLeftX());
  }
  public static double getDriverRightSpeedX(){
    return deadZoneCheck(io_drivercontroller.getRightX());
  }

  public static double getOperRightSpeed(){
    return deadZoneCheck(io_opercontroller.getRightY());
  }
  public static double getOperLeftSpeed(){
    return deadZoneCheck(io_opercontroller.getLeftY());
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return Autos.exampleAuto(m_exampleSubsystem);
    return z_BasicAuto;
  }
}
