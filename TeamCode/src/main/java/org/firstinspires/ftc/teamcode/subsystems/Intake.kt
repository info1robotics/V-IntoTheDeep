package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

object Intake {

    lateinit var intake:DcMotor
    lateinit var sensorIntake: RevColorSensorV3


    fun init(hardwareMap: HardwareMap) {
        intake = hardwareMap.get(DcMotor::class.java, "motorIntake")
        intake.direction = DcMotorSimple.Direction.REVERSE
        val motorConfigurationType = intake.motorType.clone()
        motorConfigurationType.achieveableMaxRPMFraction = 1.0
        intake.motorType = motorConfigurationType

        sensorIntake = hardwareMap.get(RevColorSensorV3::class.java, "sensorIntake")

    }

    fun setPower(power: Double) {
        intake.power = power
    }

    fun stop() {
        intake.power = 0.0
    }

    fun reverse() {
        intake.power = -0.8
    }

    fun take() {
        intake.power = 0.8
    }

    fun getColorReading(): Triple<Int, Int, Int> {
        // Return red, green, and blue as a Triple
        return Triple(sensorIntake.red(), sensorIntake.green(), sensorIntake.blue())
    }

    fun getDistance(): Double {
        return sensorIntake.getDistance(DistanceUnit.CM)
    }

    fun isYellow(): Boolean {
        val (r, g, b) = getColorReading()
        if(!isRed())
            return r in 120..220 && g in 170..400 && b in 80..120
        return false
    }
    fun isBlue():Boolean{
        val (r, g, b) = getColorReading()
        return r in 45..60 && g in 80..120 && b in 100..200
    }
    fun isRed():Boolean{
        val (r, g, b) = getColorReading()
        return r in 100..225 && g in 80..145 && b in 65..90
    }

    fun isEmpty():Boolean{
        val (r, g, b) = getColorReading()
        return r in 40..60 && g in 70..85 && b in 50..75
    }

    fun firstColour(first: Boolean): Pair<String?, Boolean> {
        if (!first) return Pair(null, false)

        return when {
            isRed() -> Pair("red", false)
            isBlue() -> Pair("blue", false)
            isYellow() -> Pair("yellow", false)
            else -> Pair(null, true) // still looking for the first color
        }
    }

}