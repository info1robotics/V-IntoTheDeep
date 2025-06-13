package org.firstinspires.ftc.teamcode.opmodes

import com.pedropathing.follower.Follower
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.AutoUtil.p
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.enums.AutoStartPos
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive
import org.firstinspires.ftc.teamcode.tasks.Task
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.serial
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener
import org.openftc.easyopencv.OpenCvCameraFactory
import org.openftc.easyopencv.OpenCvCameraRotation
import org.openftc.easyopencv.OpenCvPipeline

abstract class AutoBase : LinearOpMode() {
    lateinit var gamepadEx1: GamepadEx

    lateinit var follower: Follower
    lateinit var log: Log
    var startPos: AutoStartPos = AutoStartPos.UNKNOWN
    lateinit var camera: OpenCvCamera
    lateinit var pipeline: OpenCvPipeline

    lateinit var drive: MecanumDrive
    open var task: Task = serial()

    var actionQueue = ActionQueue()

    var full = true

    var county = 0.0
    var extendoGain = 0.0

    open fun onInit() {


    }

    fun onInitTick() {


        if(gamepadEx1.getButtonDown("dpad_left"))
            county++

        else if(gamepadEx1.getButtonDown("dpad_right"))
            county--

        if(gamepadEx1.getButtonDown("dpad_up"))
            extendoGain += 50

        if(gamepadEx1.getButtonDown("dpad_down"))
            extendoGain -= 50


        gamepadEx1.update()
        log.add("Gain for the y axis in inch: ",county.toString())
        log.add("Gain for the extendo in ticks: ",extendoGain.toString())

    }

    @Throws(InterruptedException::class)
    fun onStart() {
    }

    fun onStartTick() {
    }

    @Throws(InterruptedException::class)
    override fun runOpMode() {
        gamepadEx1 = GamepadEx(gamepad1)
        log = Log(this.telemetry)

        instance = this

        drive = MecanumDrive(this.hardwareMap, p(36.0, -60.0, Math.PI / 2))


        onInit()

        state = State.INIT
//        enableVision()
        println("enabled vision")

        while (!isStarted && !isStopRequested) {

            onInitTick()
            log.tick()

        }

        waitForStart()
        if (isStopRequested) return

        println("left init while loop")
        println(isStarted)
        println(isStopRequested)
        log.tick()

//        camera.closeCameraDeviceAsync({});
        onStart()

        state = State.START
        task?.start(this)

        while (opModeIsActive() && !isStopRequested) {
            task?.tick()
            onStartTick()

            log.tick()
            actionQueue.update()
        }
    }

    enum class State {
        DEFAULT,
        INIT,
        START
    }

    companion object {
        var state: State = State.DEFAULT
        var instance: AutoBase? = null
    }
}
