package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorImplEx
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.common.PidController
import kotlin.math.PI

object Extendo {
    private const val SPOOL_DIAMETER_MM = 25.0
    private val SPOOL_CIRCUMFERENCE_MM = SPOOL_DIAMETER_MM * PI

    //TODO: Change these according to your setup
    private const val MOTOR_RPM = 1600
    private const val GEAR_RATIO = 1.0
    private const val MOTOR_PPR = 383.6

    const val LOWER_LIMIT = 0
    const val UPPER_LIMIT = 800
    const val TRANSFER_POSITION = 180
    const val ZERO_POSITION = 0

    lateinit var extendoMotor: DcMotorImplEx

    private var POWER_PER_TICK = 0.07
    private var targetPower = 0.0
    private var currentPower = 0.0

    //private val pidController = PidController(0.005, 0.0, 0.001) // TODO: tune

    fun init(hardwareMap: HardwareMap) {
        extendoMotor = hardwareMap.get(DcMotorImplEx::class.java, "motorExtendo")

        extendoMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        extendoMotor.targetPosition = 0
        extendoMotor.mode = DcMotor.RunMode.RUN_TO_POSITION

        extendoMotor.motorType.clone().apply {
            achieveableMaxRPMFraction = 1.0
            extendoMotor.motorType = this
        }
    }

    fun resetEncoder() {
        extendoMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        extendoMotor.targetPosition = 0
        extendoMotor.mode = DcMotor.RunMode.RUN_TO_POSITION
    }

    fun withoutEncoder() {
        extendoMotor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    fun setPower(power: Double) {
        extendoMotor.power = power
    }

    fun forcePower(power: Double) {
        currentPower = power
        targetPower = -1.0
    }

    fun initTargetPower(power: Double) {
        currentPower = 0.0
        targetPower = power
    }

    fun setTargetPosition(target: Int) {
        val current = getCurrentPosition()
       // val power = pidController.calculate(target.toDouble(), current.toDouble()).coerceIn(-1.0, 1.0)
        extendoMotor.targetPosition = target
    }

    fun setTargetPositionSafe(position: Int) {
        val safePosition = position.coerceIn(LOWER_LIMIT, UPPER_LIMIT)
        setTargetPosition(safePosition)
    }

    fun getCurrentPosition(): Int {
        return extendoMotor.currentPosition
    }

    fun spoolRotationToMotorTicks(rotation: Double): Int {
        return (rotation / 360.0 * MOTOR_PPR / GEAR_RATIO).toInt()
    }

    fun isAbleToGoDown(): Boolean {
        return LOWER_LIMIT < getCurrentPosition()
    }

    fun lower() {
        setTargetPosition(0)
    }

    fun raise() {
        setTargetPosition(UPPER_LIMIT)
    }

    fun getPower(): Double {
        return extendoMotor.power
    }

    fun update() {
        Log.instance.add("Current extendo pos", getCurrentPosition())
        Log.instance.add("Target extendo pos", getTargetPosition())
        if (currentPower < targetPower) {
            currentPower += POWER_PER_TICK
            currentPower = currentPower.coerceIn(0.0, 1.0)
        }
        setPower(currentPower)
    }

    fun getTargetPosition(): Int {
        return extendoMotor.targetPosition
    }

    fun typeFloat() {
        extendoMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
    }

    fun typeBrake() {
        extendoMotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }
}