package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.JewelColor;
import org.firstinspires.ftc.teamcode.DriveControl;

/*
Main Autonomous Route for Blue Side
 */

@Autonomous(name = "AAB1Auto")
public class AAB1Auto extends LinearOpMode {
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private Servo leftServo;
    private Servo rightServo;
    private DcMotor liftMotor;
    public Servo colorArm;
    public LynxI2cColorRangeSensor colorSensor;


    @Override

    public void runOpMode() throws InterruptedException {

        colorArm = hardwareMap.servo.get("ColorArm");
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight = hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        
        /*
        DriveControl is our driving class.
        JewelColor is our class for detecting the color of the ball.
         */

        DriveControl robot = new DriveControl(motorFrontRight, motorFrontLeft, motorBackRight, motorBackLeft,
                                              leftServo, rightServo, liftMotor);
        
        colorSensor = (LynxI2cColorRangeSensor) hardwareMap.get("ColorSensor0");
        
        JewelColor jewel = new JewelColor(colorSensor);

        /*
        Procedures after initialization, before start:
        Set color arm servo to up position.
        Close the claw and lift to grab the starting glyph.
         */

        colorArm.setPosition(0.6);


        waitForStart();

        robot.closeClaw();
        robot.waitFor(3);
        robot.moveLift(-1, 0.2);

        colorArm.setPosition(0.1);
        int count = 0;

        /*
        Detecting color of the jewel:
        Call getColor() method.
        Use while loop to detect color until color is not null.
         */

        while(opModeIsActive()) {

            colorArm.setPosition(0.1);
            char color = jewel.getColor();
            telemetry.addLine(Character.toString(color));
            telemetry.update();

            if(color == 'b'){
                //Knock off the other ball.
                robot.waitFor(1);
                robot.drive(1, 2);
                robot.completeStop();
                robot.waitFor(1);
                colorArm.setPosition(0.6);
                robot.waitFor(1);
                robot.drive(-1, 2);
                break;

            }
            if(color == 'r'){
                //Knock off the ball being detected.
                robot.waitFor(1);
                robot.drive(-1, 2);
                robot.completeStop();
                robot.waitFor(1);
                colorArm.setPosition(0.6);
                robot.waitFor(1);
                robot.drive(1, 2);
                break;
            }
            if( !((color == 'r') || (color == 'b'))){
                robot.completeStop();


            }

            String cycle = Integer.toString(++count);
            telemetry.addLine(cycle);

            telemetry.update();


            if (count > 120){
                colorArm.setPosition(0.6);
                break;
            }


            idle();
        }

        //Turn to line up to cryptobox.
        robot.fourWheelTurn(-0.5 ,32);
        robot.drive(1,33);
        //Drops glyph in cryptobox.
        robot.openClaw();
        robot.drive(1, 4);

    }
}

    
