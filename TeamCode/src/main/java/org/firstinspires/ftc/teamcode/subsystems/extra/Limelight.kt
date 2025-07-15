package org.firstinspires.ftc.teamcode.subsystems.extra

import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.internal.system.AppUtil
import android.util.Log

object Limelight {
    lateinit var limelight: Limelight3A

    private var previousList = mutableListOf<Double>()

    fun init(hardwareMap: HardwareMap) {
        limelight = hardwareMap.get(Limelight3A::class.java, "limelight")
        limelight.pipelineSwitch(0)
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

    /**
     * Reads the pythonOutput array from latestResult,
     * returns true if yellow is in ROI (6th element == 1),
     * false otherwise.
     */
    fun isYellowInROI(): Boolean {
        val result = limelight.latestResult
        if (result == null) {
            Log.w("Limelight", "Latest result is null")
            return false
        }

        val pythonOutput = result.pythonOutput
        if (pythonOutput.size <= 5) {
            Log.w("Limelight", "Python output too short: ${pythonOutput.size}")
            return false
        }

        if (pythonOutput.toList() == previousList) {
            return false
        }
        previousList = pythonOutput.toMutableList()

        return pythonOutput[5].toInt() == 1
    }
}