package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by mingch on 9/9/17.
 */

@Autonomous(name = "AutonomousMovementTest")
public class AutonomousMovementTest extends LinearOpMode {
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;

    DriveControl move = new DriveControl();


    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontRight = hardwareMap.dcMotor.get("FrontRight2");
        motorFrontLeft = hardwareMap.dcMotor.get("FrontLeft3");
        motorBackRight = hardwareMap.dcMotor.get("BackRight0");
        motorBackLeft = hardwareMap.dcMotor.get("BackLeft1");

        motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        move.drive(1,10);

        fourWheelTurn(1,90);
        sleep(500);
        fourWheelTurn(1,180);
        sleep(500);
        fourWheelTurn(1,360);

    }

    public void drive(double power, double inches) {
        power = power*0.5;
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        double w = (inches/23)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        telemetry.addLine((power > 0 ? "Forward" : "Backward") + x + "Inches");
        telemetry.update();
        sleep(y);
        completeStop();
    }

    public void fourWheelTurn(double power, double degrees) {
        motorBackLeft.setPower(-power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(-power);
        motorBackRight.setPower(power);
        double w = (degrees/180)*1000;
        int y = (int) Math.rint(w);
        String x = Integer.toString(y);
        telemetry.addLine((power > 0 ? "Counterclockwise" : "Clockwise") + x + "Degrees");
        telemetry.update();
        sleep(y);
        completeStop();
    }

    public void driveTime(double power, long time){
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        String x = Long.toString(time);
        telemetry.addLine((power > 0 ? "Forwards for" : "Backwards for") + x + "seconds");
        sleep(time * 1000);
    }

    public void completeStop(){
        motorBackLeft.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
    }
    
    public void closeClaw(){
        leftServo.setPosition(1);
        rightServo.setPosition(0);
    }

    public void openClaw(){
        leftServo.setPosition(0.5);
        rightServo.setPosition(0.2);
    }
    public void liftMovement(double power, long time){
        liftMotor.setPower(power);
        sleep(time);
        completeStop();
    }
}
