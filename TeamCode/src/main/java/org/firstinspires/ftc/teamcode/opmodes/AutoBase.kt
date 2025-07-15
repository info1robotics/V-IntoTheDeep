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
import org.firstinspires.ftc.teamcode.subsystems.Extendo
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.extra.Limelight
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

    //lateinit var drive: MecanumDrive
    open var task: Task = serial()

    var actionQueue = ActionQueue()

    var full = true

    //y is the axis
    var yGain = 0// gain for the first submersible cycle
    var extendoGain = 0//gain for extendo
@CallSuper
    open fun onInit() {
        gamepadEx1 = GamepadEx(gamepad1)
        Drivetrain.initAuto(hardwareMap)
       // Limelight.init(hardwareMap)

        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        follower = Follower(hardwareMap, FConstants::class.java, LConstants::class.java)
        follower.pose = startPose
        log = Log(this.telemetry)

        Controller.init(hardwareMap)
        Extendo.resetEncoder()
        Extendo.setPower(1.0)
        Controller.setInitAuto()

        Lift.resetEncoders()

        Claw.closeStrong()

    }

    @CallSuper
    fun onInitTick() {
        state = State.INIT

        while (!isStarted && !isStopRequested) {

            if (gamepad1.a) {
                full = true
            }
            if (gamepad1.b) {
                full = false
            }

            if (full) {
                log.add("Running Full")

            } else {
                log.add("No detection")
                yGain = 0
                extendoGain = 0
            }

            when {
                gamepadEx1.getButtonDown("dpad_up") && full -> yGain--
                gamepadEx1.getButtonDown("dpad_down") && full -> yGain++
            }

            when {
                gamepadEx1.getButtonDown("dpad_left") && full -> extendoGain+=10
                gamepadEx1.getButtonDown("dpad_right") && full -> extendoGain-=10
            }


            gamepadEx1.update()
            log.add("y axis gain ", yGain.toString())
            log.add("extendo gain", extendoGain.toString())
            log.tick()
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
       // Limelight.start()
        onStart()
        state = State.START

        while (opModeIsActive() && !isStopRequested) {

            onStartTick()
            log.tick()
            actionQueue.update()
            Lift.update()
            log.add(Extendo.getPower().toString())
            log.tick()
        }
        //Limelight.stop()
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
