package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotor.RunMode
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.Log
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class Drivetrain(hardwareMap: HardwareMap) {
    var fl: DcMotor = hardwareMap.get(DcMotor::class.java, "FL")
    var fr: DcMotor = hardwareMap.get(DcMotor::class.java, "FR")
    var bl: DcMotor = hardwareMap.get(DcMotor::class.java, "BL")
    var br: DcMotor = hardwareMap.get(DcMotor::class.java, "BR")

    val motors = arrayOf(fl, fr, bl, br)

    init {
        fl.direction = DcMotorSimple.Direction.REVERSE
        bl.direction = DcMotorSimple.Direction.REVERSE
        br.direction = DcMotorSimple.Direction.FORWARD
        fr.direction = DcMotorSimple.Direction.FORWARD

        motors.forEach {  motor ->
            motor.mode = RunMode.RUN_WITHOUT_ENCODER
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

            motor.motorType.clone().apply {
                achieveableMaxRPMFraction = 1.0
                motor.motorType = this
            }
        }
    }

    fun setRunMode(runMode: RunMode?) {
        motors.forEach {
            it.mode = runMode
        }
    }

    fun driveMecanum(inAxial: Double, inLateral: Double, inYaw: Double, maxOutput: Double) {
        val modMaintainMotorRatio: Double

        val inputAxial = (inAxial * maxOutput)
        val inputLateral = (inLateral * maxOutput) * LATERAL_MULTIPLIER // TODO: tune
        val inputYaw = (inYaw * maxOutput)

        modMaintainMotorRatio = max(abs(inputAxial) + abs(inputLateral) + abs(inputYaw), 1.0)

        val leftFrontPower = (inputAxial + inputLateral + inputYaw) / modMaintainMotorRatio
        val rightFrontPower = (inputAxial - inputLateral - inputYaw) / modMaintainMotorRatio
        val leftBackPower = (inputAxial - inputLateral + inputYaw) / modMaintainMotorRatio
        val rightBackPower = (inputAxial + inputLateral - inputYaw) / modMaintainMotorRatio

        Log.instance.let {
            it.add("leftFrontPower", leftFrontPower.toString())
            it.add("rightFrontPower", rightFrontPower.toString())
            it.add("leftBackPower", leftBackPower.toString())
            it.add("rightBackPower", rightBackPower.toString())
        }

        setDriveMotorPower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower)
    }

    fun driveMecanumFieldCentric(
        inAxial: Double,
        inLateral: Double,
        inYaw: Double,
        heading: Double,
        maxOutput: Double
    ) {
        val modMaintainMotorRatio: Double

        val inputAxial = (inAxial * maxOutput)
        val inputLateral = (inLateral * maxOutput) * LATERAL_MULTIPLIER
        val inputYaw = (inYaw * maxOutput)

        val botHeading = heading

        val adjLateral = inputLateral * cos(botHeading) - inputAxial * sin(botHeading)
        val adjAxial = inputLateral * sin(botHeading) + inputAxial * cos(botHeading)

        modMaintainMotorRatio = max(abs(inputAxial) + abs(inputLateral) + abs(inputYaw), 1.0)

        val leftFrontPower = (adjAxial + adjLateral + inputYaw) / modMaintainMotorRatio
        val rightFrontPower = (adjAxial - adjLateral - inputYaw) / modMaintainMotorRatio
        val leftBackPower = (adjAxial - adjLateral + inputYaw) / modMaintainMotorRatio
        val rightBackPower = (adjAxial + adjLateral - inputYaw) / modMaintainMotorRatio

        setDriveMotorPower(leftFrontPower, rightFrontPower, leftBackPower, rightBackPower)
    }

    fun setDriveMotorPower(
        leftFrontPower: Double,
        rightFrontPower: Double,
        leftBackPower: Double,
        rightBackPower: Double
    ) {
        fl.power = leftFrontPower
        fr.power = rightFrontPower
        bl.power = leftBackPower
        br.power = rightBackPower
    }

    companion object {
        var LATERAL_MULTIPLIER: Double = 1.5
    }
}