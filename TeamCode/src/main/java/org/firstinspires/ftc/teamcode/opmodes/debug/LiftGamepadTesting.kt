package org.firstinspires.ftc.teamcode.opmodes.debug

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Lift

@TeleOp
@Config
class LiftGamepadTesting : LinearOpMode() {


    override fun runOpMode() {
        Lift.init(hardwareMap)

        Lift.resetEncoders()
        Lift.forcePower(1.0)
        val log = Log(telemetry)


        waitForStart()

        while (opModeIsActive() && !isStopRequested) {

            if(gamepad1.dpad_down)
                Lift.setTargetPosition(50)
            else if(gamepad1.dpad_up)
                Lift.setTargetPosition(700)

            log.add("Lift Left Ticks", Lift.liftMotorLeft.currentPosition)
            log.add("Lift Right Ticks", Lift.liftMotorRight.currentPosition)
            log.add("Lift Left Target", Lift.liftMotorLeft.targetPosition)
            log.add("Lift Right Target", Lift.liftMotorRight.targetPosition)
            log.add("Lift MM", Lift.ticksToMM(Lift.getCurrentPosition()))
            log.add("Lift Power", Lift.getPower())

            log.add("Lift Right Current Draw",  Lift.liftMotorRight.getCurrent(CurrentUnit.AMPS))
            log.add("Lift Left Current Draw",  Lift.liftMotorLeft.getCurrent(CurrentUnit.AMPS))
            /*
            if (position < previousPosition) {
                Lift.currentPower = 0.4
            } else if (position > previousPosition) {
                Lift.currentPower = 0.7
            }

             */
            Lift.update()
            log.tick()
        }
    }
}