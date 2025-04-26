package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap

object Controller {
    fun init(hardwareMap: HardwareMap) {
        Drivetrain.init(hardwareMap)
        Lift.init(hardwareMap)
        Extendo.init(hardwareMap)
        Intake.init(hardwareMap)
        Clutch.init(hardwareMap)
        Claw.init(hardwareMap)
        Pitch.init(hardwareMap)
        Pivot.init(hardwareMap)
        Joint.init(hardwareMap)
        Fold.init(hardwareMap)
    }

}