package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Yaw

@TeleOp
@Config
class YawTesting: LinearOpMode() {
    companion object {
        @JvmField
        var POSITION_DEG = 0.0
    }

    override fun runOpMode() {
        val log = Log(telemetry)
        Yaw.init(hardwareMap)
        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            Yaw.setPositionDeg(POSITION_DEG)
        }
    }
}