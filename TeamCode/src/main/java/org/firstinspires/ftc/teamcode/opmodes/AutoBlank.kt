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

class AutoBlank : AutoBase() {


    fun turnTo(degrees: Double) { // if you want to turn right, use negative degrees
        val temp = Pose(follower.pose.x, follower.pose.y, Math.toRadians(degrees))
        follower.holdPoint(temp)
    }

    fun goTo(x: Double, y: Double, degrees: Double) {
        val temp = Pose(x, y, Math.toRadians(degrees))
        follower.holdPoint(temp)
    }

    val testTraj = drive.actionBuilder(p(0.0, 0.0, 0.0))
        .build()



    override var task: Task = serial(


        execute{
            goTo(10.0,20.0,rad(20.0))

        },

        action(testTraj),
        sleepms(999999999)


    )
}