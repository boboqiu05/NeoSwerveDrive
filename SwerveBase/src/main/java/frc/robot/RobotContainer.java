// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OIConstants;

import frc.robot.commands.SetSwerveDrive;
import frc.robot.subsystems.DriveSubsystem;

public class RobotContainer {

        // The robot's subsystems
        final DriveSubsystem m_drive;

        // The driver and codriver controllers

        private CommandXboxController m_driverController = new CommandXboxController(
                        OIConstants.kDriverControllerPort);

        final PowerDistribution m_pdp = new PowerDistribution();
        /**
         * The container for the robot. Contains subsystems, OI devices, and commands.
         */
        public RobotContainer() {

                Pref.deleteUnused();

                Pref.addMissing();

                m_drive = new DriveSubsystem();

                m_drive.showOnShuffleboard = false;

                SmartDashboard.putData("Scheduler", CommandScheduler.getInstance());

                LiveWindow.disableAllTelemetry();

                SmartDashboard.putData("Drive", m_drive);

                CommandScheduler.getInstance()
                                 .onCommandInitialize(command -> System.out.println(command.getName() + " is starting"));
                CommandScheduler.getInstance()
                                 .onCommandFinish(command -> System.out.println(command.getName() + " has ended"));
                CommandScheduler.getInstance()
                                 .onCommandInterrupt(
                                                 command -> System.out.println(command.getName() + " was interrupted"));
                CommandScheduler.getInstance().onCommandInitialize(
                                 command -> SmartDashboard.putString("CS", command.getName() + " is starting"));
                CommandScheduler.getInstance()
                                 .onCommandFinish(command -> SmartDashboard.putString("CE",
                                                 command.getName() + " has Ended"));
                CommandScheduler.getInstance().onCommandInterrupt(
                                 command -> SmartDashboard.putString("CE", command.getName() + "was Interrupted"));

                setDefaultCommands();
        }

        private void setDefaultCommands() {

                m_drive.setDefaultCommand(getDriveCommand());

        }

        public Command getDriveCommand() {
                return new SetSwerveDrive(m_drive,
                                () -> m_driverController.getLeftY(),
                                () -> m_driverController.getLeftX(),
                                () -> m_driverController.getRightX(),
                                m_driverController.a());

        }


        public Command getStopDriveCommand() {
                return new InstantCommand(() -> m_drive.stopModules());
        }
}