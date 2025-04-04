package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImpl
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.common.Log
import kotlin.math.abs

object Roll {
    val DEFAULT_POSITION = 0.535
    val OFFSET = abs(DEFAULT_POSITION - 0.5)

    private lateinit var servoRoll: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoRoll = hardwareMap.get(ServoImplEx::class.java, "servoRoll")
        servoRoll.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPositionDeg(deg: Double) {
        val normalised = (deg + 90) / 180
        var adjusted = normalised + OFFSET

        if (adjusted < 0) adjusted += 1
        else if (adjusted > 1) adjusted -= 1

        Log.instance.add("Servo Position: ", adjusted)

        servoRoll.position = adjusted
    }
}