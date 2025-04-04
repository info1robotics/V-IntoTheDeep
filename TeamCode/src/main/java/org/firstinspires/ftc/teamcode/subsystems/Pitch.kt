package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Pitch {
    val UPRIGHT_POSITION = 0.114
    val COLLECT_POSITION = 0.685
    val PERPENDICULAR_POSITION = 0.4

    lateinit var servoPitch: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoPitch = hardwareMap.get(ServoImplEx::class.java, "servoPitch") // TODO: change to servoYaw
        servoPitch.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoPitch.position = position
    }

    fun getPosition(): Double {
        return servoPitch.position
    }
}