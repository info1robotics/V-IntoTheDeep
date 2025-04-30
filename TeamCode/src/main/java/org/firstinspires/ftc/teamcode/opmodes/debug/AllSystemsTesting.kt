package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Clutch
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Fold
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Linkage
import org.firstinspires.ftc.teamcode.subsystems.Pitch
import org.firstinspires.ftc.teamcode.subsystems.Pivot

@Config
@TeleOp
class AllSystemsTesting: LinearOpMode() {
    companion object {
        @JvmField
        var pitchPosition = Pitch.COLLECT_POSITION

        @JvmField
        var jointPosition = 0.0

        @JvmField
        var pivotPositionDeg = 0.0

        @JvmField
        var clawPosition = Claw.OPEN_PARALLEL_POSITION

        @JvmField
        var foldPosition = Fold.UNFOLDED

        @JvmField
        var clutchPosition = 0.0
        @JvmField
        var linkagePosition = Linkage.INIT
    }


    override fun runOpMode() {
        val log = Log(telemetry)
        Controller.init(hardwareMap)
        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            Joint.setPosition(jointPosition)
            Pitch.setPosition(pitchPosition)
            Pivot.setPositionDeg(pivotPositionDeg)
            Claw.setPosition(clawPosition)
            Fold.setPosition(foldPosition)
            Clutch.setPosition(clutchPosition)
            Linkage.setPosition(linkagePosition)
        }
    }
}