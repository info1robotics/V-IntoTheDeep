package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint

@TeleOp
@Config
class SensorTesting : LinearOpMode() {
    override fun runOpMode() {
        Intake.init(hardwareMap)
        val log = Log(telemetry)

        var first = true
        var detectedColor: String? = null

        waitForStart()

        while (opModeIsActive() && !isStopRequested) {

            val (r, g, b) = Intake.getColorReading()

            log.add("Red", r)
            log.add("Green", g)
            log.add("Blue", b)
            log.add("is Empty",Intake.isEmpty())
            log.add("is Yellow", Intake.isYellow())
            log.add("is Blue",Intake.isBlue())
            log.add("is Red",Intake.isRed())

            if (first) {
                val (color, updatedFirst) = Intake.firstColour(first)
                if (color != null) {
                    detectedColor = color
                    first = updatedFirst
                }
            }

            log.add("First Detected Color", detectedColor ?: "none")
            log.tick()
        }
    }
}