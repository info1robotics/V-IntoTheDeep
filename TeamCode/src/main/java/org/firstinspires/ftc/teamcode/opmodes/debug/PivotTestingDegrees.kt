package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Pivot

@TeleOp
@Config
class PivotTestingDegrees: LinearOpMode() {
    companion object {
        @JvmField
        var POSITION = 0.0
    }

    override fun runOpMode() {
        val log = Log(telemetry)
        Pivot.init(hardwareMap)
        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            Pivot.setPositionDeg(POSITION)
            log.tick()
            log.add("Degrees    ")
        }
    }
}