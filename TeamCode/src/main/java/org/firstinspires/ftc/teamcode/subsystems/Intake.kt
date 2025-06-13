package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.ColorSensor
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap

object Intake {

    lateinit var intake:DcMotor
    lateinit var sensorIntake: ColorSensor


    fun init(hardwareMap: HardwareMap) {
        intake = hardwareMap.get(DcMotor::class.java, "motorIntake")
        val motorConfigurationType = intake.motorType.clone()
        motorConfigurationType.achieveableMaxRPMFraction = 1.0
        intake.motorType = motorConfigurationType

        sensorIntake = hardwareMap.get(ColorSensor::class.java, "sensorIntake")
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

    fun isYellow(): Boolean {
        val (r, g, b) = getColorReading()
        return r in 145..1000 && g in 130..1000 && b in 0..150
    }

    fun isEmpty():Boolean{
        val (r, g, b) = getColorReading()
        return r in 20..80 && g in 50..150 && b in 40..130
    }

}