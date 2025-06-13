package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Extendo
import org.firstinspires.ftc.teamcode.subsystems.Fold
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.Linkage
import org.firstinspires.ftc.teamcode.subsystems.Pivot
import java.util.zip.ZipError

@TeleOp(name = "Teleop")
class Teleop : LinearOpMode() {

    fun Gamepad.corrected_left_stick_y(): Float = -this.left_stick_y

    lateinit var gamepadEx1: GamepadEx
    lateinit var gamepadEx2: GamepadEx
    val actionQueue = ActionQueue()

    private fun handleInputDrivetrain()
    {

        val forwardPower = gamepad1.corrected_left_stick_y().toDouble()
        val strafePower =   gamepad1.left_stick_x.toDouble()
        val primaryRotationPower = gamepad1.right_trigger.toDouble() - gamepad1.left_trigger.toDouble()
        val secondaryRotationPower = gamepad2.left_stick_x.toDouble()
       // if(primaryRotationPower != 0.0)
        Drivetrain.driveMecanum(forwardPower, strafePower, primaryRotationPower, 1.0)
        //else
            //Drivetrain.driveMecanum(forwardPower,strafePower,secondaryRotationPower,0.7)
    }

    private fun handleInputLift()
    {
        if(Lift.getTargetPosition()<Lift.getCurrentPosition())
            Lift.setPower(0.5)
        else
            Lift.setPower(1.0)

        if(gamepad2.left_stick_button)
            Lift.setTargetPosition(0)

        if(gamepad2.corrected_left_stick_y() > 0.0 && ready)
            Lift.setTargetPosition(Lift.HIGH_BASKET_POSITION)

    }
    var ready = false
    private fun handleTransfer()
    {
        if(gamepadEx2.getButtonDown("y") && Pivot.isPositive())
        {
            Claw.toggle()
            actionQueue.add(150){
                Controller.setTransfer()

            }
        }
        else if(gamepadEx2.getButtonDown("y"))
        {
            lynx = true
            swing= false
            Joint.setPosition(Joint.TRANSITION_POSITION)
            actionQueue.add(100)
            {
                Extendo.setTargetPosition(Extendo.TRANSFER_POSITION)
                actionQueue.add(200)
                {
                    Linkage.setPosition(Linkage.MAX_POSITION)
                    actionQueue.add(300)
                    {
                        Claw.closeLight()
                        actionQueue.add(200)
                        {
                            Joint.setPosition(Joint.PARALLEL_POSITION+0.05)
                            Linkage.setPosition(Linkage.ZERO_POSITION)
                            actionQueue.add(200)
                            {
                                Controller.setLowBasket()
                                actionQueue.add(350)
                                {
                                    Fold.unfold()
                                    Linkage.setPosition(Linkage.MAX_POSITION)

                                    ready = true
                                    lynx = false

                                }
                            }
                        }
                    }
                }

            }

        }
    }
    private fun handleFailSafe()
    {
        if(gamepadEx2.getButtonDown("x"))
        {
            Joint.setPosition(Joint.TRANSITION_POSITION)
        }
    }


    var even = true
    private fun handleInputX()//on gamepad x is a
    {
        if(gamepadEx2.getButtonDown("a") && ready)
        {
            Claw.open()
            actionQueue.add(200)
            {
                Controller.setTransfer()
                ready=false
            }

        }

    }

    var lynx = false
    private fun handleInputExtendo()
    {
        var converter = 175
        if(!ready && !lynx)
            Extendo.setTargetPosition((Extendo.getCurrentPosition()+converter*gamepad2.corrected_left_stick_y()).toInt())
    }

    var swing = false
    private fun handleinputIntake() {

        if(Intake.isYellow())
        {
            Fold.fold()
            swing = true
        }
        else if(!Intake.isEmpty() && !Intake.isYellow() && !swing)
        {
            swing = true
            Fold.unfold()
            actionQueue.add(150)
            {
                Joint.setPosition(0.23)
                actionQueue.add(800)
                {
                    Joint.setPosition(Joint.COLLECT_POSITION)
                    swing = false
                }
            }

        }

        if (gamepad2.right_stick_y > 0.1) {
            Intake.take()
            if(!swing)
                Joint.setPosition(Joint.COLLECT_POSITION)

        } else if (gamepad2.right_stick_y < -0.1){
            Intake.reverse()
            if(!swing)
                Joint.setPosition(Joint.COLLECT_POSITION)
        }
        else
            Intake.setPower(0.0)
    }



    override fun runOpMode() {
        gamepadEx1 = GamepadEx(gamepad1)
        gamepadEx2 = GamepadEx(gamepad2)

        val log = Log(telemetry)
        Controller.init(hardwareMap)

        Lift.resetEncoders()
        Lift.forcePower(1.0)

        Extendo.resetEncoder()
        Extendo.forcePower(1.0)

        Controller.setInit()

        waitForStart()

        var ignoreDpadDown = false

        while (opModeIsActive() && !isStopRequested) {

            handleInputDrivetrain()
            handleInputLift()
            handleInputX()
            handleTransfer()
            handleinputIntake()
            handleInputExtendo()
            handleFailSafe()

            gamepadEx1.update()
            gamepadEx2.update()
            actionQueue.update()
            
            val (r, g, b) = Intake.getColorReading()
            log.add("Red", r)
            log.add("Green", g)
            log.add("Blue", b)

            log.tick()

            Lift.update()
        }
    }
}