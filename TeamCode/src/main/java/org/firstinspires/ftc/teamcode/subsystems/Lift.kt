package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorImplEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.common.Log
import org.firstinspires.ftc.teamcode.common.PidController
import kotlin.math.abs

object Lift {
    val SPOOL_DIAMETER_MM = 32
    val SPOOL_CIRCUMFERENCE_MM = SPOOL_DIAMETER_MM * Math.PI

    //TODO: Change these according to your setup
    val MOTOR_RPM = 1600
    val GEAR_RATIO = 1.0
    val MOTOR_PPR = 383.6

    val HIGH_BASKET_POSITION = 680
    val LOW_BASKET_POSITION = 290

    val LOWER_LIMIT = 0
    val UPPER_LIMIT = 730


    lateinit var liftMotorLeft: DcMotorImplEx
    lateinit var liftMotorRight: DcMotorImplEx

    lateinit var liftMotors: Array<DcMotor>

    var POWER_PER_TICK = 0.07
    var targetPower = 0.0
    var currentPower = 0.0

    private val pidControllerVertical = PidController(  3.5,0.04,0.8)//TODO tune the params


    fun init(hardwareMap: HardwareMap) {
        liftMotorLeft = hardwareMap.get(DcMotorImplEx::class.java, "motorLiftLeft")
        liftMotorRight = hardwareMap.get(DcMotorImplEx::class.java, "motorLiftRight")

        liftMotorLeft.direction = DcMotorSimple.Direction.REVERSE

        liftMotors = arrayOf(liftMotorLeft, liftMotorRight)

        liftMotors.forEach { motor ->
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
            motor.targetPosition = 0
            motor.mode = DcMotor.RunMode.RUN_TO_POSITION

            motor.motorType.clone().apply {
                achieveableMaxRPMFraction = 1.0
                motor.motorType = this
            }
        }
    }

    fun resetEncoders() {
        liftMotors.forEach {
            it.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
            it.targetPosition = 0
            it.mode = DcMotor.RunMode.RUN_TO_POSITION
        }
    }

    fun withoutEncoders()
    {
        liftMotors.forEach{
            it.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        }
    }

    fun setPower(power: Double) {
        liftMotorLeft.power=power
        liftMotorRight.power = Lift.liftMotorLeft.power
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

        val power = pidControllerVertical.calculate(target.toDouble(), current.toDouble()).coerceIn(-1.0, 1.0)
        liftMotorLeft.power = power
        liftMotorRight.power = power



        liftMotors.forEach {
            it.targetPosition = target
        }
    }

    fun setTargetPositionSafe(position: Int) {

        val coercedPosition: Int = position.coerceIn(LOWER_LIMIT, UPPER_LIMIT)

        setTargetPosition(coercedPosition)
    }



    fun getCurrentPosition(): Int {
        return (liftMotorLeft.currentPosition + liftMotorRight.currentPosition) / 2
    }

    fun reachedPosition(tolerance: Int = 10): Boolean {
        return abs(getCurrentPosition() - getTargetPosition()) < (liftMotorRight.targetPositionTolerance + tolerance)
    }

    fun setMode(runMode: DcMotor.RunMode) {
        liftMotors.forEach {
            it.mode = runMode
        }
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
        return LOWER_LIMIT < getCurrentPosition()
    }

    fun lower() {
        setTargetPosition(0)
    }

    fun raise() {
        setTargetPosition(UPPER_LIMIT)
    }

    fun getPower(): Double {
        return liftMotorLeft.power
    }

    fun update() {
        Log.instance.add("Current lift pos", Lift.getCurrentPosition())
        Log.instance.add("Target lift pos", Lift.getTargetPosition())
        Log.instance.add("Lift Power",Lift.getPower())
        if (currentPower < targetPower) {
            currentPower += POWER_PER_TICK
            currentPower = currentPower.coerceIn(0.0, 1.0)
        }
        setPower(currentPower)
    }

    fun getTargetPosition(): Int {
        return liftMotorLeft.targetPosition
    }

    fun typeFloat()
    {
        liftMotors.forEach {
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        }
    }
    fun typeBrake()
    {
        liftMotors.forEach {
            it.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }
    }

}