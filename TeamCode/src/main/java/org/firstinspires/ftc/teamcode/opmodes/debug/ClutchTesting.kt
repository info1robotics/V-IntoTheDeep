package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Clutch
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain

@TeleOp
@Config
class ClutchTesting: LinearOpMode() {
    companion object
    {
        @JvmField
        var pos = 0.0
    }

    override fun runOpMode() {
        Clutch.init(hardwareMap)
        val log = Log(telemetry)
        waitForStart()
        while (opModeIsActive() && !isStopRequested)
        {
            Clutch.setPosition(pos)

            log.add("CLICKED", Clutch.clicked)
        }
    }
}