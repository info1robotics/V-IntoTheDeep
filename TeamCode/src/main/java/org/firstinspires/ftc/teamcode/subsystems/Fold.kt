package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Fold {
    //TODO: tune
    val FOLDED = 0.0
    val UNFOLDED = 0.0

    lateinit var servoFold: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoFold = hardwareMap.get(ServoImplEx::class.java, "servoFold")
        servoFold.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoFold.position = position
    }

    fun getPosition(): Double {
        return servoFold.position
    }

    fun fold() { servoFold.position = FOLDED }

    fun unfold() { servoFold.position = UNFOLDED }

    fun folded(): Boolean {
        return servoFold.position == FOLDED
    }


}