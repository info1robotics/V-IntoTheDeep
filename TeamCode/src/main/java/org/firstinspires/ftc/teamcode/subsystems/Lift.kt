
package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorImplEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.common.PidController
import kotlin.math.abs

object Lift {
    val SPOOL_DIAMETER_MM = 32.0
    val SPOOL_CIRCUMFERENCE_MM = SPOOL_DIAMETER_MM * Math.PI

    val MOTOR_RPM = 1600
    val GEAR_RATIO = 1.0
    val MOTOR_PPR = 383.6

    val HIGH_BASKET_POSITION = 680
    val LOW_BASKET_POSITION = 290

    val LOWER_LIMIT = 0
    val UPPER_LIMIT = 800


    lateinit var liftMotorLeft: DcMotorImplEx
    lateinit var liftMotorRight: DcMotorImplEx

    lateinit var liftMotors: Array<DcMotor>

    private var targetPosition = 0

    private val pidControllerVertical = PidController(
        0.008,
        0.00001,
        0.005
    )
    var currentPower = 0.0
    var targetPower = 0.0

    fun init(hardwareMap: HardwareMap) {
        liftMotorLeft = hardwareMap.get(DcMotorImplEx::class.java, "motorLiftLeft")
        liftMotorRight = hardwareMap.get(DcMotorImplEx::class.java, "motorLiftRight")

        liftMotorLeft.direction = DcMotorSimple.Direction.REVERSE

        liftMotors = arrayOf(liftMotorLeft, liftMotorRight)

        liftMotors.forEach { motor ->
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            motor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

            motor.motorType.clone().apply {
                achieveableMaxRPMFraction = 1.0
                motor.motorType = this
            }
        }
    }

    fun resetEncoders() {
        liftMotors.forEach {
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
    }

    fun setPower(power: Double) {
        liftMotorLeft.power = power
        liftMotorRight.power = power
    }

    fun setTargetPosition(position: Int) {
        targetPosition = position.coerceIn(LOWER_LIMIT, UPPER_LIMIT)
    }

    fun getTargetPosition(): Int {
        return targetPosition
    }

    fun getCurrentPosition(): Int {
        return (liftMotorLeft.currentPosition + liftMotorRight.currentPosition) / 2
    }

    fun reachedPosition(tolerance: Int = 10): Boolean {
        return abs(getCurrentPosition() - targetPosition) < tolerance
    }

    fun lower() {
        setTargetPosition(LOWER_LIMIT)
    }

    fun raise() {
        setTargetPosition(UPPER_LIMIT)
    }

    fun ticksToMM(ticks: Int): Double {
        return ticks.toDouble() / MOTOR_PPR * SPOOL_CIRCUMFERENCE_MM * GEAR_RATIO
    }

    fun mmToTicks(mm: Double): Int {
        return (mm / SPOOL_CIRCUMFERENCE_MM * MOTOR_PPR / GEAR_RATIO).toInt()
    }

    fun spoolRotationToMotorTicks(rotation: Double): Int {
        return (rotation / 360 * MOTOR_PPR / GEAR_RATIO).toInt()
    }

    fun isAbleToGoDown(): Boolean {
        return getCurrentPosition() > LOWER_LIMIT
    }

    fun setMode(runMode: DcMotor.RunMode) {
        liftMotors.forEach {
            it.mode = runMode
        }
    }

    fun typeFloat() {
        liftMotors.forEach {
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        }
    }

    fun typeBrake() {
        liftMotors.forEach {
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }
    }

    fun getPower(): Double {
        return liftMotorLeft.power
    }

    fun forcePower(power: Double) {
        currentPower = power
        targetPower = -1.0
    }

    fun update() {
        val current = getCurrentPosition()
        val target = getTargetPosition()

        var power = pidControllerVertical.calculate(target.toDouble(), current.toDouble())

        // Optional gravity compensation
        val gravityComp = if (target > current) 0.05 else 0.0

        power += gravityComp

        // Limit power to save current draw
        power = power.coerceIn(-1.0, 1.0)

        setPower(power)

        Log.instance.add("Current lift pos", current)
        Log.instance.add("Target lift pos", target)
        Log.instance.add("Lift power", power)
    }
}
