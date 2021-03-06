package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*
Code to configure color arm
 */

@TeleOp(name = "ColorArmTest")
public class ColorArmTest extends LinearOpMode
{
    private Servo ColorArm;

    @Override
    public void runOpMode () throws InterruptedException
    {
        ColorArm = hardwareMap.servo.get("ColorArm");

        waitForStart();

        while(opModeIsActive())
        {
                ColorArm.setPosition(0.1);
            sleep(5000);
                ColorArm.setPosition(0.6);
            sleep(1000);


            idle();
        }
    }

}
