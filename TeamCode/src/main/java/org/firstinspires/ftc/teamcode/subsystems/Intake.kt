package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap

object Intake {

    lateinit var intake:DcMotor

    fun init(hardwareMap: HardwareMap) {
        intake = hardwareMap.get(DcMotor::class.java, "intake")
        val motorConfigurationType = intake.motorType.clone()
        motorConfigurationType.achieveableMaxRPMFraction = 1.0
        intake.motorType = motorConfigurationType
    }

    fun setPower(power: Double) {
        intake.power = power
    }

    fun stop() {
        intake.power = 0.0
    }

    fun reverse() {
        intake.power = -1.0
    }

    fun take() {
        intake.power = 1.0
    }
}