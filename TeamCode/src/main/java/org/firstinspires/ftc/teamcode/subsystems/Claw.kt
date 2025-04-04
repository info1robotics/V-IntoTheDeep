package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.subsystems.Pitch.servoPitch

object Claw {
    val OPEN_PARALLEL_POSITION = 0.5
    val CLOSE_LIGHT_POSITION = 0.85
    val CLOSE_STRONG_POSITION = 0.89

    lateinit var servoClaw: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoClaw = hardwareMap.get(ServoImplEx::class.java, "servoClaw")
        servoClaw.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoClaw.position = position
    }
}