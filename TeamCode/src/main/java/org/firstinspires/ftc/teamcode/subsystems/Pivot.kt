package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx

object Pivot {
    //TODO: tune positions
    //double positions
    val UPRIGHT_POSITION = 0.85
    val DOWNRIGHT_POSITION = 0.067
    val PERPENDICULAR_POSITION = 0.4
    val TRANSFER_POSITION = 0.98

    //degrees positions
    val STRAIGHT_POSITION = 0.0

    lateinit var servoPivotLeft: ServoImplEx
    lateinit var servoPivotRight: ServoImplEx

    fun init(hardwareMap: HardwareMap) {
        servoPivotLeft = hardwareMap.get(ServoImplEx::class.java, "servoPivotLeft")
        servoPivotRight = hardwareMap.get(ServoImplEx::class.java, "servoPivotRight")
        servoPivotLeft.pwmRange = PwmRange(500.0, 2500.0)
        servoPivotRight.pwmRange = PwmRange(500.0, 2500.0)
        servoPivotRight.direction = Servo.Direction.REVERSE
    }

    fun setPosition(position: Double) {
        servoPivotLeft.position = position
        servoPivotRight.position = position
    }

    fun getPosition(): Double {
        return (servoPivotLeft.position + servoPivotRight.position) / 2
    }

    fun setPositionDeg(positionDeg: Double) {
        val positionDeg = -positionDeg
        val inputMin = -90.0
        val inputMax = 90.0

        val targetPosition = DOWNRIGHT_POSITION + (positionDeg - inputMin) / (inputMax - inputMin) * (UPRIGHT_POSITION - DOWNRIGHT_POSITION)
        setPosition(targetPosition)
    }

    fun getPositionDeg(): Double {
        val inputMin = -90.0
        val inputMax = 90.0

        val currentPosition = getPosition()
        val positionDeg = inputMin + (currentPosition - DOWNRIGHT_POSITION) / (UPRIGHT_POSITION - DOWNRIGHT_POSITION) * (inputMax - inputMin)

        return -positionDeg
    }
    fun isPositive():Boolean//the pisition of the pivot in degrees is > 0
    {
        return Pivot.getPositionDeg() > 0
    }


}