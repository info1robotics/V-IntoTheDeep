package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain

@Config
@TeleOp
class HangTesting : LinearOpMode() {

    companion object {
        @JvmField
        var power = 0.0
    }
    override fun runOpMode() {
        Drivetrain.init(hardwareMap)
        val log = Log(telemetry)
        val liftLeft = hardwareMap.dcMotor.get("motorLiftLeft")
        val liftRight = hardwareMap.dcMotor.get("motorLiftRight")

        liftLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        liftLeft.targetPosition = 0
        liftLeft.mode = DcMotor.RunMode.RUN_TO_POSITION

        liftLeft.motorType.clone().apply {
            achieveableMaxRPMFraction = 1.0
            liftLeft.motorType = this
        }

        liftRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        liftRight.targetPosition = 0
        liftRight.mode = DcMotor.RunMode.RUN_TO_POSITION

        liftRight.motorType.clone().apply {
            achieveableMaxRPMFraction = 1.0
            liftRight.motorType = this
        }

        liftLeft.direction = DcMotorSimple.Direction.REVERSE

        liftLeft.mode = DcMotor.RunMode.RUN_USING_ENCODER
        liftRight.mode = DcMotor.RunMode.RUN_USING_ENCODER

        liftLeft.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        liftRight.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT

        waitForStart()
        while (opModeIsActive() && !isStopRequested) {

            Drivetrain.fl.power = -power
            Drivetrain.bl.power = -power
            Drivetrain.fr.power = -power
            Drivetrain.br.power = -power

            liftLeft.power = power
            liftRight.power = power

            liftLeft.targetPosition = -700
            liftRight.targetPosition = -700

            liftLeft.mode = DcMotor.RunMode.RUN_TO_POSITION
            liftRight.mode = DcMotor.RunMode.RUN_TO_POSITION

            log.tick()
            telemetry.addData("Lift Left Ticks", liftLeft.currentPosition)
            telemetry.addData("Lift Right Ticks", liftRight.currentPosition)
        }

    }

}