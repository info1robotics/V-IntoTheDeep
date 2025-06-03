package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Extendo
import org.firstinspires.ftc.teamcode.subsystems.Lift

@TeleOp
@Config
class ExtendoTesting : LinearOpMode() {
    companion object {
        @JvmField
        var position = 0
    }


    override fun runOpMode() {
        Extendo.init(hardwareMap)

        Extendo.resetEncoder()
        Extendo.forcePower(1.0)
        val log = Log(telemetry)


        waitForStart()

        var previousPosition = position

        while (opModeIsActive() && !isStopRequested) {
            Extendo.setTargetPosition(position)

            log.add("Extendo Ticks", Extendo.extendoMotor.currentPosition)
            log.add("Extendo Target", Extendo.extendoMotor.targetPosition)
            log.add("Extendo Power", Extendo.   getPower())

            log.add("Extendo Current Draw",  Extendo.extendoMotor.getCurrent(CurrentUnit.AMPS))
            /*
            if (position < previousPosition) {
                Extendo.currentPower = 0.4
            } else if (position > previousPosition) {
                Extendo.currentPower = 0.7
            }

             */
            previousPosition = position
            Extendo.update()
            log.tick()
        }
    }
}