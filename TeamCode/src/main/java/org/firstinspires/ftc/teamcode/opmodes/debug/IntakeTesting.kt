package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Intake

@TeleOp
@Config
class IntakeTesting : LinearOpMode() {
    companion object {
        @JvmField
        var power = 0.0
    }


    override fun runOpMode() {
        Intake.init(hardwareMap)

        val log = Log(telemetry)


        waitForStart()


        while (opModeIsActive() && !isStopRequested) {
            Intake.setPower(power)
            log.tick()
        }
    }
}