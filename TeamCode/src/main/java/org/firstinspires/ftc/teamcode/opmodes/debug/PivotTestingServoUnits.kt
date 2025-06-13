package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Pivot

@TeleOp
@Config
class PivotTestingServoUnits: LinearOpMode() {
    companion object {
        @JvmField
        var POSITION = 0.4
    }

    override fun runOpMode() {
        val log = Log(telemetry)
        Pivot.init(hardwareMap)
        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            Pivot.setPosition(POSITION)
            log.tick()
            log.add("Servo Units values")

            log.add("In degrees:")
            log.add(Pivot.getPositionDeg().toString())
        }
    }
}