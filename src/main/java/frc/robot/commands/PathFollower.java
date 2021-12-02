// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class PathFollower extends CommandBase {
  private String filePath;
  private Drivetrain drive;
  private Scanner fileScanner;
  private boolean finishedFlag = false;

  /** Creates a new PathFollower. */
  public PathFollower(String filePath, Drivetrain drive) {

    this.filePath = filePath;
    this.drive = drive;

    addRequirements(drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    File file = new File(filePath);

    try {
      this.fileScanner = new Scanner(file);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      this.finishedFlag = true;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (fileScanner.hasNextLine()){
      String line = fileScanner.nextLine();

      String[] fields = line.split(",");

      int delay = Integer.valueOf(fields[0]);
      double speed = Double.valueOf(fields[1]);
      double turnSpeed = Double.valueOf(fields[2]);

      // try {
      //   System.out.printf("Sleeping for %d ms%n", delay);
      //   Thread.sleep(delay);
      // } catch (InterruptedException e) {
      //   // TODO Auto-generated catch block
      //   e.printStackTrace();
      //   Thread.currentThread().interrupt();
      // }

      System.out.printf("Auto drive %f:%f%n", speed, turnSpeed);

      drive.arcadeDrive(speed, turnSpeed);
    }
    else{
      finishedFlag = true;
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    fileScanner.close();
    drive.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (finishedFlag)
      return true;
    else
      return false;
  }
}
