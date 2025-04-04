package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx


object Yaw {
    val RADIUS_MM: Double = -1.0 // TODO: CHANGE TO ACTUAL VALUE

    val DEFAULT_POSITION = 0.5
    val OFFSET = kotlin.math.abs(DEFAULT_POSITION - 0.5)

    val SERVO_RANGE = 352.0
    val GEAR_RATIO = 1.6
    val REAL_RANGE = SERVO_RANGE / GEAR_RATIO // 220.0

    lateinit var servoYaw: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoYaw = hardwareMap.get(ServoImplEx::class.java, "servoYaw")
        servoYaw.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPositionDeg(deg: Double) {
        val deg = -deg
        val normalized = (deg + 110) / 220 // Map -110 to 110 to 0 to 1
        var adjusted = normalized + OFFSET

        // Wrap around
        if (adjusted < 0) adjusted += 1
        else if (adjusted > 1) adjusted -= 1

        servoYaw.position = adjusted
    }

    fun setPosition(position: Double) {
        servoYaw.position = position
    }

    fun getPositionDeg(): Double {
        // Adjust for offset when calculating position degrees
        val adjustedPosition = servoYaw.position - OFFSET

        val normalizedPosition =
            if (adjustedPosition < 0) adjustedPosition + 1
            else if (adjustedPosition > 1) adjustedPosition - 1

        else adjustedPosition
        return -(normalizedPosition * 220 - 110)
    }

    fun getPosition(): Double {
        return servoYaw.position
    }
}
