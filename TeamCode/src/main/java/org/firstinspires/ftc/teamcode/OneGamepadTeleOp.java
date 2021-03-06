package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.lynx.LynxI2cColorRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "OneGamepadTeleOp")
public class OneGamepadTeleOp extends LinearOpMode
{
    public LynxI2cColorRangeSensor colorSensor;
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private DcMotor liftMotor;
    private Servo leftServo;
    private Servo rightServo;
    public Servo ColorArm;


    @Override
    public void runOpMode () throws InterruptedException
    {
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight= hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        liftMotor = hardwareMap.dcMotor.get("liftMotor");
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");
        ColorArm = hardwareMap.servo.get("ColorArm");


        liftMotor.setDirection(DcMotor.Direction.REVERSE);

        motorBackRight.setDirection(DcMotor.Direction.REVERSE);
        motorFrontRight.setDirection(DcMotor.Direction.REVERSE);

        leftServo.setPosition(0.2);
        rightServo.setPosition(0.5);


        double powerMod = 1.0;

        waitForStart();

        ColorArm.setPosition(1);


        while(opModeIsActive())
        {
            ColorArm.setPosition(1);

            //Driving

            /*
            Checks if right bumper is pressed.
            If so, power is reduced.
             */
            if(gamepad2.right_bumper){
                powerMod = 0.25;
            }else{
                powerMod = 1.0;
            }

            motorBackLeft.setPower(powerMod * gamepad2.left_stick_y);
            motorFrontLeft.setPower(powerMod * gamepad2.left_stick_y);
            motorBackRight.setPower(powerMod * gamepad2.right_stick_y);
            motorFrontRight.setPower(powerMod * gamepad2.right_stick_y);

            //Claw And Lift
            //When a on gamepad 2(B) is pressed, claw opens
            if(gamepad2.a){
                leftServo.setPosition(0.2);
                rightServo.setPosition(0.5);

            }
            //When b on gamepad 2(B) is pressed, claw closes
            if (gamepad2.b){
                leftServo.setPosition(1);
                rightServo.setPosition(0);

            }

            //When x is held, close the claw until fully closed
            if(gamepad2.x && leftServo.getPosition() <= 1){
                leftServo.setPosition(leftServo.getPosition() + 0.01);
                rightServo.setPosition(rightServo.getPosition() - 0.00625);

            }

            //When y is held, open the claw until fully open
            if(gamepad2.y && leftServo.getPosition() >= 0.2){
                leftServo.setPosition(leftServo.getPosition() - 0.01);
                rightServo.setPosition(rightServo.getPosition() + 0.00625);
            }

            /*
            If up is pressed, lift moves up.
            If down is pressed, lift moves down.
            */

            if(gamepad2.dpad_up) {
                liftMotor.setPower(-0.5);
            }else if(gamepad2.dpad_down){
                liftMotor.setPower(0.5);
            }else{
                liftMotor.setPower(0);
            }

            idle();
        }
    }

}

