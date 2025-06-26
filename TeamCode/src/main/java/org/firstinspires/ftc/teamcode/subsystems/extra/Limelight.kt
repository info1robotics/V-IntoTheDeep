package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.hardware.limelightvision.LLResult
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.Log

object Limelight {
    lateinit var limelight: Limelight3A;


    fun init(hardwareMap: HardwareMap) {
        limelight = hardwareMap.get(Limelight3A::class.java, "limelight")
        // limelight.pipelineSwitch(4) // TODO: change the pipeline
    }

    fun start() {
        limelight.start()
    }

    fun stop() {
        limelight.stop()
    }

    fun saveSnapshot() {
        limelight.captureSnapshot("cache-${System.currentTimeMillis()}")
    }
}