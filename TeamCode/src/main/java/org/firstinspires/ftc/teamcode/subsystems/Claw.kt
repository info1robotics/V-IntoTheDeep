package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import kotlin.math.abs


object  Claw {
    //TODO: tune positions
    val OPEN_PARALLEL_POSITION = 0.0
    val CLOSE_LIGHT_POSITION = 0.0
    val CLOSE_STRONG_POSITION = 0.0
    val MOST_OPEN_POSITION = 0.0

    lateinit var servoClaw: ServoImplEx

    var open = true

    fun init(hardwareMap: HardwareMap) {
        servoClaw = hardwareMap.get(ServoImplEx::class.java, "servoClaw")
        servoClaw.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoClaw.position = position
    }

    fun closeLight() {
        setPosition(CLOSE_LIGHT_POSITION)
        open = false
    }

    fun closeStrong() {
        setPosition(CLOSE_STRONG_POSITION)
        open = false
    }

    fun open() {
        setPosition(OPEN_PARALLEL_POSITION)
        open = true
    }

    fun openFull() {
        setPosition(MOST_OPEN_POSITION)
        open = true
    }

    fun toggle() {
        if (open) closeLight()
        else open()
    }

    fun getPosition(): Double {
        return servoClaw.position
    }


}