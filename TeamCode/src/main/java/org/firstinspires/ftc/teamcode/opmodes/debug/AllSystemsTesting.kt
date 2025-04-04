package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Pitch
import org.firstinspires.ftc.teamcode.subsystems.Pivot
import org.firstinspires.ftc.teamcode.subsystems.Roll
import org.firstinspires.ftc.teamcode.subsystems.Yaw

@Config
@TeleOp
class AllSystemsTesting: LinearOpMode() {
    companion object {
        @JvmField
        var pitchPosition = Pitch.COLLECT_POSITION

//        @JvmField
//        var rollPositionDeg = 0.0

        @JvmField
        var yawPositionDeg = 0.0

        @JvmField
        var pivotPositionDeg = 0.0

        @JvmField
        var clawPosition = Claw.OPEN_PARALLEL_POSITION
    }


    override fun runOpMode() {
        val log = Log(telemetry)
        Controller.init(hardwareMap)
        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            Pitch.setPosition(pitchPosition)
            Yaw.setPositionDeg(yawPositionDeg)
            Roll.setPositionDeg(-yawPositionDeg)
            Pivot.setPositionDeg(pivotPositionDeg)
            Claw.setPosition(clawPosition)
        }
    }
}