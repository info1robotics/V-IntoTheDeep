package org.firstinspires.ftc.teamcode.opmodes

import androidx.annotation.CallSuper
import com.pedropathing.follower.Follower
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.GamepadEx
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.enums.AutoStartPos
import org.firstinspires.ftc.teamcode.pedro.constants.FConstants
import org.firstinspires.ftc.teamcode.pedro.constants.LConstants
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.tasks.Task
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.serial
import org.openftc.easyopencv.OpenCvCamera
import org.openftc.easyopencv.OpenCvPipeline

abstract class AutoBase(val startPose: Pose = Pose(0.0, 0.0, Math.toRadians(0.0))) : LinearOpMode() {

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
@CallSuper
    open fun onInit() {

        Drivetrain.initAuto(hardwareMap)

        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        follower = Follower(hardwareMap, FConstants::class.java, LConstants::class.java)
        follower.pose = startPose
        log = Log(this.telemetry)

        Controller.init(hardwareMap)
        Controller.setInit()

        Lift.resetEncoders()

        Claw.closeStrong()

    }

    @CallSuper
    fun onInitTick() {
        state = State.INIT

        while (!isStarted && !isStopRequested) {
            if (gamepadEx1.getButtonDown("dpad_left"))
                county++
            else if (gamepadEx1.getButtonDown("dpad_right"))
                county--

            if (gamepadEx1.getButtonDown("dpad_up"))
                extendoGain += 50

            if (gamepadEx1.getButtonDown("dpad_down"))
                extendoGain -= 50


            gamepadEx1.update()
            log.add("Gain for the y axis in inch: ", county.toString())
            log.add("Gain for the extendo in ticks: ", extendoGain.toString())
        }

    }

    @CallSuper
    @Throws(InterruptedException::class)
    fun onStart() {
        if (isStopRequested) return

        println("left init while loop")
        println(isStarted)
        println(isStopRequested)
        log.tick()
        state = State.START
        task.start(this)
    }

    fun onStartTick() {
        follower.update()
        log.add("@X", follower.pose.x)
        log.add("@Y", follower.pose.y)
        log.add("@Heading", Math.toDegrees(follower.pose.heading))
        task.tick()
        log.tick()

        actionQueue.update()
    }
    @Throws(InterruptedException::class)
    override fun runOpMode() {

        onInit()
        instance=this

        while (!isStarted && !isStopRequested) {
            onInitTick()
            log.tick()
        }

        waitForStart()
        if (isStopRequested) return

        onStart()
        gamepadEx1 = GamepadEx(gamepad1)
        state = State.START

        while (opModeIsActive() && !isStopRequested) {
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
