package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap

object Controller {
    fun init(hardwareMap: HardwareMap) {
        Claw.init(hardwareMap)
        Pitch.init(hardwareMap)
        Roll.init(hardwareMap)
        Yaw.init(hardwareMap)
        Pivot.init(hardwareMap)
    }

    fun setStraight() {
        Pitch.setPosition(Pitch.PERPENDICULAR_POSITION)
        Roll.setPositionDeg(0.0)
        Yaw.setPositionDeg(0.0)
        Pivot.setPositionDeg(0.0)
    }
}