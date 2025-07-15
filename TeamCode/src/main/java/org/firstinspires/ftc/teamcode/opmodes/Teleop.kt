package org.firstinspires.ftc.teamcode.opmodes

import android.graphics.Paint.Join
import com.acmerobotics.roadrunner.samplePathByRotation
import com.qualcomm.hardware.lynx.LynxModule
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Gamepad
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Clutch
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Extendo
import org.firstinspires.ftc.teamcode.subsystems.Fold
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.Linkage
import org.firstinspires.ftc.teamcode.subsystems.Pivot
import kotlin.math.abs

@TeleOp
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
        val secondaryRotationPower = gamepad2.right_stick_x.toDouble()
        if(primaryRotationPower != 0.0)
            Drivetrain.driveMecanum(forwardPower, strafePower, primaryRotationPower, 1.0)
        else
            Drivetrain.driveMecanum(forwardPower,strafePower,secondaryRotationPower,0.7)
    }

    private fun handleInputLift()
    {

        if(gamepad2.left_stick_button)
            Lift.setTargetPosition(0)

        if((gamepad2.corrected_left_stick_y() > 0.0 && ready && !lynx))
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

        else if(gamepadEx2.getButtonDown("y")/* ||(Intake.isYellow() && !lynx)*/)//TODO uncomment if playing samples
        {
            Controller.setTransfer()
            Fold.fold()
            lynx = true
            swing = false
            Joint.setPosition(Joint.PARALLEL_POSITION-0.02)
            Intake.setPower(1.0)
            Extendo.setTargetPositionSafe(Extendo.TRANSFER_POSITION)
            actionQueue.add(100)
            {
                Linkage.setPosition(0.26)
                actionQueue.add(500)
                {
                    Claw.closeStrong()
                    Fold.unfold()
                    actionQueue.add(200)
                    {
                        Linkage.setPosition(Linkage.ZERO_POSITION)
                        Intake.setPower(0.0)
                        actionQueue.add(300)
                        {
                            Pivot.setPositionDeg(30.0)
                            actionQueue.add(250)
                            {
                                Lift.setTargetPosition(Lift.LOW_BASKET_POSITION)
                                Linkage.setPosition(Linkage.MAX_POSITION)
                                actionQueue.add(200)
                                {
                                    Fold.fold()
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
        if(gamepadEx2.getButtonDown("x") && Joint.getPosition() != Joint.PARALLEL_POSITION)
        {
            Joint.setPosition(Joint.PARALLEL_POSITION)
            Fold.fold()
        }
        else if(gamepadEx2.getButtonDown("x"))
        {
            lynx=true
            Extendo.setTargetPositionSafe(100)
            actionQueue.add(700)
            {
                Joint.setPosition(Joint.PARALLEL_POSITION+0.12)
                lynx=false
            }
        }
        if(gamepadEx2.getButtonDown("touchpad"))
        {
            Controller.setInit()
            specimen=false
        }
    }


    var even = true
    private fun handleInputDrop()//on gamepad x is a
    {
        if(gamepadEx2.getButtonDown("a") && ready && !specimen)
        {
            Claw.open()
            actionQueue.add(200)
            {
                Controller.setTransfer()
                ready = false
                swing = false
            }

        }

    }
    var specimen = false
    var score = false
    private fun handleInputScore()
    {
        if(gamepadEx2.getButtonDown("b"))
        {
            Controller.setHuman()
            specimen = true
            score=false
        }
        if(gamepadEx2.getButtonDown("a") && specimen && !score)
        {
            Claw.closeStrong()
            actionQueue.add(150){
                Controller.setHighChamber()
                actionQueue.add(200)
                {
                    Linkage.setPosition(Linkage.MAX_POSITION)
                    Claw.closeStrong()
                    score = true

                }
            }

        }
        if(gamepadEx2.getButtonDown("a") && specimen && score)
        {
         //   Lift.setTargetPosition(400)

            Claw.open()
            actionQueue.add(250)
            {
                score=false
                specimen=false
            }
        }
    }
    var lynx = false
    private fun handleInputExtendo()
    {
        var converter = 175
        Extendo.setPower(1.0)
        if(!ready && !lynx)
            Extendo.setTargetPositionSafe((Extendo.getCurrentPosition()+converter*gamepad2.corrected_left_stick_y()).toInt())
    }

    var swing = false
    private fun handleinputIntake() {

        if(Intake.isBlue() && Extendo.getCurrentPosition()>200 )//TODO aliance colour, in this case red
        {
            Joint.setPosition(Joint.PARALLEL_POSITION)
            Fold.fold()
        }


        if(Intake.isYellow())
            swing = true
        else if (gamepad2.right_trigger > 0.1 && !Intake.isYellow() && !lynx) {
            Intake.take()
            if(!swing && !Intake.isYellow() && !Intake.isRed())
            {
                Joint.setPosition(Joint.COLLECT_POSITION)
                Fold.unfold()
            }

        } else if (gamepad2.left_trigger> 0.1 && !Intake.isYellow() && !lynx){
            Intake.reverse()
        }
        else
            Intake.setPower(0.0)
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
        //Lift.forcePower(1.0)

        Extendo.resetEncoder()
        Extendo.forcePower(1.0)

        Controller.setInit()

        waitForStart()

        var ignoreDpadDown = false

        while (opModeIsActive() && !isStopRequested) {

            handleInputDrivetrain()
            handleInputLift()
            handleInputDrop()
            handleInputScore()
            handleTransfer()
            handleinputIntake()
            handleInputExtendo()
            handleFailSafe()


            gamepadEx1.update()
            gamepadEx2.update()
            actionQueue.update()
/*
            val (r, g, b) = Intake.getColorReading()
            log.add("Red", r)
            log.add("Green", g)
            log.add("Blue", b)

           // log.add("Lift Current Draw", Lift.liftMotorLeft.getCurrent(CurrentUnit.AMPS) + Lift.liftMotorRight.getCurrent(CurrentUnit.AMPS))

            log.tick()
            log.add("lynx: ",lynx)
            log.add("ready: ",ready)
            log.add("swing: ",swing)

 */


            Lift.update()
        }
    }
}