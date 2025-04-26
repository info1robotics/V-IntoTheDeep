package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Fold

@TeleOp
@Config
class FoldTesting : LinearOpMode() {
    companion object {
        @JvmField
        var pos = Fold.UNFOLDED
    }
    override fun runOpMode() {
        Fold.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (!isStopRequested && opModeIsActive()) {
            Fold.setPosition(pos)
            log.add("Position", Fold.getPosition())
            log.tick()

        }
    }

}