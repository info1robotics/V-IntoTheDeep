package org.firstinspires.ftc.teamcode.opmodes

import com.pedropathing.localization.Pose
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Extendo
import org.firstinspires.ftc.teamcode.subsystems.Fold
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.Linkage
import org.firstinspires.ftc.teamcode.subsystems.Pivot
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.execute
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.serial
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepms

@Autonomous
class AutoBasket : AutoBase(Pose(-38.0,-61.0, Math.toRadians(0.0))) {


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
                goTo(-62.0,-57.0,70.0)
                Lift.setTargetPosition(Lift.HIGH_BASKET_POSITION)
                actionQueue.add(300)
                {
                    Linkage.setPosition(Linkage.MAX_POSITION)
                    Pivot.setPositionDeg(15.0)
                }
                actionQueue.add(850)
                {
                    Claw.open()
                }
            },
            sleepms(1200),
            execute{
                goTo(-59.0,-54.0,70.0)
                Controller.setInit()
                Fold.fold()
                Joint.setPosition(Joint.COLLECT_POSITION)
                Extendo.setPower(1.0)
                Extendo.setTargetPosition(700)
                Intake.setPower(1.0)
            },
            sleepms(1200),

            execute{
                Controller.setTransfer()
                Joint.setPosition(0.42)
                Extendo.setTargetPositionSafe(Extendo.TRANSFER_POSITION)
                actionQueue.add(300)
                {
                    Linkage.setPosition(0.18)
                    actionQueue.add(250)
                    {
                        Claw.closeStrong()
                        actionQueue.add(200)
                        {
                            Intake.setPower(0.0)
                            actionQueue.add(100)
                            {
                                Fold.unfold()
                                Linkage.setPosition(Linkage.ZERO_POSITION)
                                Pivot.setPositionDeg(0.0)
                                actionQueue.add(150)
                                {
                                    Lift.setTargetPosition(Lift.HIGH_BASKET_POSITION)
                                    Linkage.setPosition(Linkage.MAX_POSITION)
                                    Claw.closeStrong()
                                    Fold.fold()
                                }
                            }
                        }

                    }
                }
            },
            sleepms(1200),
            execute{
                goTo(-62.0,-58.0,93.0)
                Pivot.setPositionDeg(15.0)
                actionQueue.add(450)
                {
                    Claw.open()
                }
            },
            sleepms(600),
            execute{
                goTo(-61.0,-56.0,93.0)
                Controller.setInit()
                Fold.fold()
                Joint.setPosition(Joint.COLLECT_POSITION)
                Extendo.setPower(1.0)
                Extendo.setTargetPosition(700)
                Intake.setPower(1.0)
            },
            sleepms(1200),

            execute{
                Controller.setTransfer()
                Joint.setPosition(0.28)
                Extendo.setTargetPositionSafe(Extendo.TRANSFER_POSITION)
                actionQueue.add(300)
                {
                    Linkage.setPosition(0.18)
                    actionQueue.add(250)
                    {
                        Claw.closeStrong()
                        actionQueue.add(200)
                        {
                            Intake.setPower(0.0)
                            actionQueue.add(100)
                            {
                                Fold.unfold()
                                Linkage.setPosition(Linkage.ZERO_POSITION)
                                Pivot.setPositionDeg(0.0)
                                actionQueue.add(150)
                                {
                                    Lift.setTargetPosition(Lift.HIGH_BASKET_POSITION)
                                    Linkage.setPosition(Linkage.MAX_POSITION)
                                    Claw.closeStrong()
                                    Fold.fold()
                                }
                            }
                        }

                    }
                }
            },
            sleepms(1200),
            execute{goTo(-62.0,-62.0,106.0)},
            execute{
                Pivot.setPositionDeg(15.0)
                actionQueue.add(450)
                {
                    Claw.open()
                }
            },
            sleepms(600),
            execute{
                goTo(-60.0,-60.0,106.0)
                Controller.setInit()
                Fold.fold()
                Joint.setPosition(Joint.COLLECT_POSITION)
                Extendo.setPower(1.0)
                Extendo.setTargetPosition(700)
                Intake.setPower(1.0)
            },
            sleepms(1200),

            execute{
                Controller.setTransfer()
                Joint.setPosition(0.35)
                Extendo.setTargetPositionSafe(Extendo.TRANSFER_POSITION)
                actionQueue.add(300)
                {
                    Linkage.setPosition(0.18)
                    actionQueue.add(250)
                    {
                        Claw.closeStrong()
                        actionQueue.add(200)
                        {
                            Intake.setPower(0.0)
                            actionQueue.add(100)
                            {
                                Fold.unfold()
                                Linkage.setPosition(Linkage.ZERO_POSITION)
                                Pivot.setPositionDeg(0.0)
                                actionQueue.add(150)
                                {
                                    Lift.setTargetPosition(Lift.HIGH_BASKET_POSITION)
                                    Linkage.setPosition(Linkage.MAX_POSITION)
                                    Claw.closeStrong()
                                    Fold.fold()
                                }
                            }
                        }

                    }
                }
            },
            sleepms(1200),
            execute{goTo(-62.0,-62.0,98.0)},
            execute{
                Pivot.setPositionDeg(15.0)
                actionQueue.add(450)
                {
                    Claw.open()
                }
            },


        )
    }
}