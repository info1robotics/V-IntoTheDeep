package org.firstinspires.ftc.teamcode.opmodes.debug

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight

@TeleOp(name = "Limelight ROI Test", group = "Test")
class PipelineTesting : LinearOpMode() {
    override fun runOpMode() {
        telemetry.addLine("Initializing Limelight...")
        telemetry.update()

        Limelight.init(hardwareMap)

        telemetry.addLine("Initialization complete. Waiting for start...")
        telemetry.update()

        waitForStart()
        Limelight.start()

        while (opModeIsActive()) {
            val inROI = Limelight.isYellowInROI()

            telemetry.addData("Yellow in ROI?", inROI)
            telemetry.update()
            sleep(100) // avoid spamming telemetry too fast
        }

        Limelight.stop()
    }
}