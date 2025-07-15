package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Fold
import org.firstinspires.ftc.teamcode.subsystems.Sweeper

@TeleOp
@Config
class SweeperTesting : LinearOpMode() {
    companion object {
        @JvmField
        var pos = Sweeper.VERTICAL_POSITION
    }
    override fun runOpMode() {
        Sweeper.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (!isStopRequested && opModeIsActive()) {
            Sweeper.setPosition(pos)
            log.add("Position", Sweeper.getPosition())
            log.tick()

        }
    }

}