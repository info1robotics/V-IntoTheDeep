package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Extendo
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Lift
@TeleOp (name = "Teleop Specimen")
class TeleopSpecimen: LinearOpMode() {
    fun Gamepad.corrected_left_stick_y(): Float = -this.left_stick_y

    lateinit var gamepadEx1: GamepadEx
    lateinit var gamepadEx2: GamepadEx
    val actionQueue = ActionQueue()

    private fun handleInputDrivetrain()
    {

        val forwardPower = gamepad1.corrected_left_stick_y().toDouble()
        val strafePower =   gamepad1.left_stick_x.toDouble()
        val primaryRotationPower = gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble()
        val secondaryRotationPower = gamepad2.right_stick_x.toDouble()
        if(primaryRotationPower != 0.0)
            Drivetrain.driveMecanum(forwardPower, strafePower, primaryRotationPower, 1.0)
        else
            Drivetrain.driveMecanum(forwardPower,strafePower,secondaryRotationPower,0.7)
    }
    private fun handleStates()
    {
        if(gamepadEx2.getButtonDown("x"))
        {
            Controller.setHuman()
        }
    }


    private fun handleScoring()
    {

    }


    override fun runOpMode() {

        val allHubs = hardwareMap.getAll(
            LynxModule::class.java
        )

        val controlHub = allHubs.first { it.isParent }
        val expansionHub = allHubs.first { !it.isParent }
        gamepadEx1 = GamepadEx(gamepad1)
        gamepadEx2 = GamepadEx(gamepad2)

        val log = Log(telemetry)
        Controller.init(hardwareMap)

        Lift.resetEncoders()

        Extendo.resetEncoder()
        Extendo.forcePower(1.0)

        Controller.setInit()

        waitForStart()
        while (opModeIsActive() && !isStopRequested) {
            log.add("Expansion Hub Draw", expansionHub.getCurrent(CurrentUnit.AMPS))
            log.add("Control Hub Draw", controlHub.getCurrent(CurrentUnit.AMPS))

            handleInputDrivetrain()


            gamepadEx1.update()
            gamepadEx2.update()
            actionQueue.update()

            val (r, g, b) = Intake.getColorReading()
            log.add("Red", r)
            log.add("Green", g)
            log.add("Blue", b)

            log.add("Lift Current Draw", Lift.liftMotorLeft.getCurrent(CurrentUnit.AMPS) + Lift.liftMotorRight.getCurrent(
                CurrentUnit.AMPS))

            log.tick()

            Lift.update()
        }
    }
}