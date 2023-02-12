// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.LowArmControl;
import frc.robot.commands.UpArmControl;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveTeleop;
import frc.robot.commands.DualArmControl;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
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
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final DriveSubsystem s_DriverSubsystem = new DriveSubsystem();
  private final ArmSubsystem s_ArmSubsystem = new ArmSubsystem();

  private final Command z_DriveTeleop = new DriveTeleop(s_DriverSubsystem); 
  private final Command z_LowArmControl_In = new LowArmControl(s_ArmSubsystem, 0.35, 7, 0.20);
  private final Command z_LowArmControl_Out = new LowArmControl(s_ArmSubsystem, 0.48, 7, 0.20);

  private final Command z_UpperArmControl_In = new UpArmControl(s_ArmSubsystem, 0.325, 9, 0.3);
  private final Command z_UpperArmControl_Out = new UpArmControl(s_ArmSubsystem, 0.029, 9, 0.3);

  //Extend and Retract Arms together
  private final Command z_ArmExtend = new DualArmControl(s_ArmSubsystem, 0.48, 7, 0.2, 0.029, 9, 0.3);
  private final Command z_ArmRetract = new DualArmControl(s_ArmSubsystem, 0.35, 7, 0.2, 0.325, 9, 0.3);
  
  private final static XboxController io_drivercontroller = new XboxController(OperatorConstants.kDriverControllerPort);
  private final static XboxController io_opercontroller = new XboxController(OperatorConstants.kOperatorControllerPort);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    s_DriverSubsystem.setDefaultCommand(z_DriveTeleop);
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
    final JoystickButton d_aButton = new JoystickButton(io_drivercontroller, Button.kA.value);
    d_aButton.whileTrue(z_LowArmControl_In);
    final JoystickButton d_bButton = new JoystickButton(io_drivercontroller, Button.kB.value);
    d_bButton.whileTrue(z_LowArmControl_Out);
    final JoystickButton d_xButton = new JoystickButton(io_drivercontroller, Button.kX.value);
    d_xButton.whileTrue(z_UpperArmControl_In);
    final JoystickButton d_yButton = new JoystickButton(io_drivercontroller, Button.kY.value);
    d_yButton.whileTrue(z_UpperArmControl_Out);
    final JoystickButton d_startButton = new JoystickButton(io_drivercontroller, Button.kStart.value);
    d_startButton.whileTrue(z_ArmExtend);
    final JoystickButton d_backButton = new JoystickButton(io_drivercontroller, Button.kBack.value);
    d_backButton.whileTrue(z_ArmRetract);
  }
  public static double deadZoneCheck(double rawControllerInput){
    if (rawControllerInput > OperatorConstants.kControllerDeadZone || rawControllerInput < -OperatorConstants.kControllerDeadZone){
      return rawControllerInput;
    }
    else{
      return 0;
    }
  }

  public static double getDriverLeftSpeed(){
    return deadZoneCheck(io_drivercontroller.getLeftY());
  }
  public static double getDriverRightSpeed() {
    return deadZoneCheck(io_drivercontroller.getRightY());
  }
  public static double getDriverLeftSpeedX(){
    return deadZoneCheck(io_drivercontroller.getLeftX());
  }
  public static double getDriverRightSpeedX(){
    return deadZoneCheck(io_drivercontroller.getRightX());
  }
  public static double getOperRightSpeedY(){
    return deadZoneCheck(io_opercontroller.getRightY());
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}
