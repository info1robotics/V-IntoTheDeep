package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.pedropathing.localization.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.common.AutoUtil.p
import org.firstinspires.ftc.teamcode.common.AutoUtil.rad
import org.firstinspires.ftc.teamcode.tasks.Task
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.action
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.execute
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.serial
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepms

@Autonomous
class AutoChamber : AutoBase(Pose(61.0,14.0, Math.toRadians(180.0))) {


    fun turnTo(degrees: Double) { // if you want to turn right, use negative degrees
        val temp = Pose(follower.pose.x, follower.pose.y, Math.toRadians(degrees))
        follower.holdPoint(temp)
    }

    fun goTo(x: Double, y: Double, degrees: Double) {
        val temp = Pose(x, y, Math.toRadians(degrees))
        follower.holdPoint(temp)
    }

    override fun onInit(){
        super.onInit()
        task = serial(

            execute{
                goTo(35.0,41.0,270.0)//preload
            },
            sleepms(700),
            execute{
                goTo(9.0,39.0,270.0)
            },
            sleepms(1500),
            execute {
                goTo(9.0,34.5,180.0)
            },
            sleepms(900),//to human
            execute{
                goTo(60.0,34.5,180.0)
            },


            sleepms(1400),
            execute{
                goTo(9.0,40.0,270.0)//first sample
            },
            sleepms(1500),
            execute {
                goTo(9.0,34.5,180.0)
            },
            sleepms(900),
            execute{
                goTo(60.0,34.5,180.0)//to human
            },

            sleepms(1400),
            execute{
                goTo(9.0,40.0,270.0)//second sample
            },
            sleepms(1500),
            execute {
                goTo(9.0,34.5,180.0)
            },
            sleepms(900),
            execute{
                goTo(60.0,34.5,180.0)//to human
            }
            //TODO finish the sequences and add the actions



        )
    }
}