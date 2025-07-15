package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.roadrunner.TankDrive.Params

object Controller {
    fun init(hardwareMap: HardwareMap) {
        Drivetrain.init(hardwareMap)
        Lift.init(hardwareMap)
        Linkage.init(hardwareMap)
        Extendo.init(hardwareMap)
        Intake.init(hardwareMap)
        Clutch.init(hardwareMap)
        Claw.init(hardwareMap)
        Pivot.init(hardwareMap)
        Joint.init(hardwareMap)
        Fold.init(hardwareMap)
        Sweeper.init(hardwareMap)
    }
    fun setInit()
    {
        Sweeper.setPosition(Sweeper.VERTICAL_POSITION)
        Lift.setTargetPosition(0)
        Linkage.setPosition(Linkage.ZERO_POSITION)
        Pivot.setPosition(Pivot.TRANSFER_POSITION)
        Claw.open()
        //Extendo.setTargetPosition(0)
        Joint.setPosition(Joint.PARALLEL_POSITION)
        Fold.setPosition(Fold.UNFOLDED)
        Clutch.setPosition(0.0)
        Extendo.setTargetPositionSafe(0)
        Extendo.setPower(1.0)
    }
    fun setInitAuto()
    {
        Lift.setTargetPosition(0)
        Sweeper.setPosition(Sweeper.VERTICAL_POSITION)
        Linkage.setPosition(Linkage.ZERO_POSITION)
        Pivot.setPositionDeg(0.0)
        Claw.closeLight()
        //Extendo.setTargetPosition(0)
        Joint.setPosition(Joint.PARALLEL_POSITION)
        Fold.setPosition(Fold.UNFOLDED)
        Clutch.setPosition(0.0)
    }
    fun setTransfer()
    {
        Lift.setTargetPosition(0)
        Linkage.setPosition(Linkage.ZERO_POSITION)
        Pivot.setPosition(Pivot.TRANSFER_POSITION)
        Claw.open()
        Extendo.setTargetPosition(Extendo.TRANSFER_POSITION)
        Joint.setPosition(Joint.PARALLEL_POSITION)
        //Fold.setPosition(Fold.FOLDED)
        Clutch.setPosition(0.0)
    }
    fun setLowBasket()
    {

        Lift.setTargetPosition(Lift.LOW_BASKET_POSITION)
        Pivot.setPositionDeg(35.0)
        //Linkage.setPosition(Linkage.SCORE_BASKET)

    }
    fun setHighBasket()
    {
        Lift.setTargetPosition(Lift.HIGH_BASKET_POSITION)
        Pivot.setPositionDeg(35.0)
        //Linkage.setPosition(Linkage.MAX_POSITION)

    }
    fun setHighChamber()
    {
        Lift.setTargetPosition(400)
        Pivot.setPositionDeg(-82.0)
    }
    fun setHuman()
    {
        Lift.setTargetPosition(0)
        Pivot.setPositionDeg(115.0)
        Linkage.setPosition(Linkage.ZERO_POSITION)
        Claw.open()
    }
    fun setVision()
    {
        Lift.setTargetPosition(0)
        Linkage.setPosition(Linkage.ZERO_POSITION)
        Pivot.setPositionDeg(98.0)
        Claw.open()
        //Extendo.setTargetPosition(0)
        Joint.setPosition(Joint.PARALLEL_POSITION)
        Fold.setPosition(Fold.UNFOLDED)
        Clutch.setPosition(0.0)
    }



}