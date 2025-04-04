package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Lift

class Teleop : LinearOpMode() {
    override fun runOpMode() {
        val gamepadEx1 = GamepadEx(gamepad1)
        val gamepadEx2 = GamepadEx(gamepad2)

        val log = Log(telemetry)
        Lift.init(hardwareMap)

        waitForStart()

        var ignoreDpadDown = false

        while (opModeIsActive() && !isStopRequested) {
            if (gamepad2.dpad_down && !ignoreDpadDown) {
                Lift.lower()
                Lift.setPower(0.5)
            } else if (gamepad2.dpad_up) {
                Lift.raise()
                Lift.setPower(1.0)
            } else {
                Lift.setTargetPosition(Lift.getCurrentPosition())
                Lift.setPower(0.5)
            }

            if (ignoreDpadDown && !gamepad2.dpad_down) {
                ignoreDpadDown = false
            }


            gamepadEx1.update()
            gamepadEx2.update()

            Lift.update()
        }
    }
}