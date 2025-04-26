package org.firstinspires.ftc.teamcode.subsystems

import com.qualcomm.robotcore.hardware.HardwareMap
import com.qualcomm.robotcore.hardware.PwmControl.PwmRange
import com.qualcomm.robotcore.hardware.ServoImplEx

object Clutch {
    //TODO: tune positions
    val CLICKED_VAL = 0.0

    lateinit var servoClutch: ServoImplEx

    var clicked = false;

    fun init(hardwareMap: HardwareMap)
    {
        servoClutch = hardwareMap.get(ServoImplEx::class.java,"servoClutch")
        servoClutch.pwmRange = PwmRange(500.0,2500.0)
        servoClutch.position = 0.0
        clicked = false;
    }

    fun setPosition(position : Double)
    {
        servoClutch.position = position
    }

    fun getCurrentPosition(): Double {
        return servoClutch.position
    }

    fun click()
    {
        servoClutch.position = CLICKED_VAL
        if(servoClutch.position> CLICKED_VAL-0.02)
        {
            clicked = true;
        }
    }



}