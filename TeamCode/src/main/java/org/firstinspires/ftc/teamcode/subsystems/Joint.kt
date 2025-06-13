package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Joint {
    //TODO tune
    val PARALLEL_POSITION = 0.5
    val COLLECT_POSITION = 0.675
    val TRANSITION_POSITION = 0.44

    lateinit var servoJoint: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoJoint = hardwareMap.get(ServoImplEx::class.java, "servoJoint")
        servoJoint.pwmRange = PwmRange(500.0, 2500.0)
    }

    fun setPosition(position: Double) {
        servoJoint.position = position
    }

    fun getPosition(): Double {
        return servoJoint.position
    }

}