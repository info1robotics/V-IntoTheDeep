package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Pitch

@TeleOp
@Config
class PitchTesting : LinearOpMode() {
    companion object {
        @JvmField
        var pos = Pitch.PERPENDICULAR_POSITION
    }
    override fun runOpMode() {
        Pitch.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (!isStopRequested && opModeIsActive()) {
            Pitch.setPosition(pos)
            log.add("Position", Pitch.getPosition())
            log.tick()

        }
    }

}