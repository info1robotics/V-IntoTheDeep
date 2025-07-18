package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Linkage

@TeleOp
@Config
class LinkageTesting : LinearOpMode() {
    companion object {
        @JvmField
        var pos = Linkage.ZERO_POSITION
    }
    override fun runOpMode() {
        Linkage.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (!isStopRequested && opModeIsActive()) {
            Linkage.setPosition(pos)
            log.add("Position", Linkage.getPosition())
            log.tick()

        }
    }

}