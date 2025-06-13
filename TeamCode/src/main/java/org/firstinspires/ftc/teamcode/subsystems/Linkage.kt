package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object   Linkage {
    //TODO tune
    val MAX_POSITION= 0.24//max position
    val ZERO_POSITION = 0.0
    val SCORE_BASKET = 0.14
    lateinit var servoLinkage: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoLinkage = hardwareMap.get(ServoImplEx::class.java, "servoLinkage")
        servoLinkage.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoLinkage.position = position
    }

    fun getPosition(): Double {
        return servoLinkage.position
    }
}