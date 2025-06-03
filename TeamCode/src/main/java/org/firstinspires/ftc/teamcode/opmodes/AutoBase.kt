package org.firstinspires.ftc.teamcode.opmodes

import com.pedropathing.follower.Follower
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName
import org.firstinspires.ftc.teamcode.common.ActionQueue
import org.firstinspires.ftc.teamcode.common.AutoUtil.p
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
    lateinit var follower: Follower
    lateinit var log: Log
    var startPos: AutoStartPos = AutoStartPos.UNKNOWN
    lateinit var camera: OpenCvCamera
    lateinit var pipeline: OpenCvPipeline

    lateinit var drive: MecanumDrive
    open var task: Task = serial()

    var actionQueue = ActionQueue()

    var full = true

    open fun onInit() {
    }

    fun onInitTick() {
    }

    @Throws(InterruptedException::class)
    fun onStart() {
    }

    fun onStartTick() {
    }

    @Throws(InterruptedException::class)
    override fun runOpMode() {
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
            actionQueue.tick()
        }
    }

//    fun enableVision() {
//        pipeline = LineDetectionPipeline();
//
//        val cameraMonitorViewId = hardwareMap.appContext.resources.getIdentifier(
//            "cameraMonitorViewId",
//            "id",
//            hardwareMap.appContext.packageName
//        )
//        Log.instance.add("camera ", cameraMonitorViewId)
//        camera = OpenCvCameraFactory.getInstance().createWebcam(
//            hardwareMap.get(
//                WebcamName::class.java, "Webcam 1"
//            ), cameraMonitorViewId
//        )
//
//        camera.setPipeline(pipeline);
//        try {
//            camera.openCameraDeviceAsync(object : AsyncCameraOpenListener {
//                override fun onOpened() {
//                    camera.startStreaming(1920, 1080, OpenCvCameraRotation.UPRIGHT)
//                }
//
//                override fun onError(errorCode: Int) {
//                    /*
//                     * This will be called if the camera could not be opened
//                     */
//                }
//            })
//        } catch (_: Exception) {
//
//        }
//    }

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
