package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint

@TeleOp
@Config
class JointTesting : LinearOpMode() {
    companion object {
        @JvmField
        var pos = Joint.COLLECT_POSITION
    }
    override fun runOpMode() {
        Joint.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (!isStopRequested && opModeIsActive()) {

            Joint.setPosition(pos)

            log.tick()

        }
    }

}