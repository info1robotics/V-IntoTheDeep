package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Claw

@TeleOp
@Config
class ClawTesting : LinearOpMode() {
    companion object {
        @JvmField
        var pos = Claw.OPEN_POSITION

    }
    override fun runOpMode() {
        Claw.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (!isStopRequested && opModeIsActive()) {
            Claw.setPosition(pos)
            log.add("Position", Claw.servoClaw.position)
            log.add("Position Analog", Claw.getPosition())
            log.tick()

        }
    }

}