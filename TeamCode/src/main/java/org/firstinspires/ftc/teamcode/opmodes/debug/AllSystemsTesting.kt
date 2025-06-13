package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Clutch
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Fold
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.Linkage
import org.firstinspires.ftc.teamcode.subsystems.Pivot

@Config
@TeleOp
class AllSystemsTesting: LinearOpMode() {
    companion object {
        @JvmField
        var jointPosition = 0.0

        @JvmField
        var pivotPositionDeg = 0.0

        @JvmField
        var clawPosition = Claw.OPEN_POSITION

        @JvmField
        var foldPosition = Fold.UNFOLDED

        @JvmField
        var clutchPosition = 0.0
        @JvmField
        var linkagePosition = Linkage.ZERO_POSITION
    }


    override fun runOpMode() {
        val log = Log(telemetry)
        Lift.init(hardwareMap)
        Lift.resetEncoders()
        Lift.forcePower(0.0)
        Lift.typeBrake()
        Controller.init(hardwareMap)
        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            Joint.setPosition(jointPosition)
            Pivot.setPositionDeg(pivotPositionDeg)
            Claw.setPosition(clawPosition)
            Fold.setPosition(foldPosition)
            Clutch.setPosition(clutchPosition)
            Linkage.setPosition(linkagePosition)

            log.add("Lift Left", Lift.liftMotorLeft.currentPosition)
            log.add("Lift Right Ticks", Lift.liftMotorRight.currentPosition)
            log.add("Lift Left Target", Lift.liftMotorLeft.targetPosition)
            log.add("Lift Right Target", Lift.liftMotorRight.targetPosition)
            log.add("Lift MM", Lift.ticksToMM(Lift.getCurrentPosition()))
            log.add("Lift Power", Lift.getPower())

            log.add("Lift Right Current Draw",  Lift.liftMotorRight.getCurrent(CurrentUnit.AMPS))
            log.add("Lift Left Current Draw",  Lift.liftMotorLeft.getCurrent(CurrentUnit.AMPS))

            Lift.update()
            log.tick()
        }
    }
}