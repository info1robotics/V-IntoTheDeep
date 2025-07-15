package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Sweeper {
    val INITIAL_POSITION = 0.0
    val VERTICAL_POSITION = 0.45
    val HORIZONTAL_POSITION = 0.9

    lateinit var servoSweeper: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoSweeper = hardwareMap.get(ServoImplEx::class.java, "servoSweeper")
        servoSweeper.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoSweeper.position = position
    }

    fun getPosition(): Double {
        return servoSweeper.position
    }



}