package org.firstinspires.ftc.teamcode.opmodes

import android.os.LocaleList
import com.acmerobotics.roadrunner.TrajectoryActionBuilder
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.pedropathing.localization.Pose
import com.pedropathing.pathgen.BezierCurve
import com.pedropathing.pathgen.BezierLine
import com.pedropathing.pathgen.Point
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.common.AutoUtil.p
import org.firstinspires.ftc.teamcode.common.AutoUtil.rad
import org.firstinspires.ftc.teamcode.subsystems.Claw
import org.firstinspires.ftc.teamcode.subsystems.Clutch
import org.firstinspires.ftc.teamcode.subsystems.Controller
import org.firstinspires.ftc.teamcode.subsystems.Extendo
import org.firstinspires.ftc.teamcode.subsystems.Fold
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Joint
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.Linkage
import org.firstinspires.ftc.teamcode.subsystems.Pivot
import org.firstinspires.ftc.teamcode.subsystems.Sweeper
import org.firstinspires.ftc.teamcode.tasks.Task
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.action
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.execute
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.serial
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepms
import org.firstinspires.ftc.teamcode.tasks.TaskBuilder.sleepuntil

@Autonomous
class AutoChamber : AutoBase(Pose(61.0,14.0, Math.toRadians(180.0))) {
    private val seqCollectHuman = serial(
       execute {
           Claw.closeStrong()
           actionQueue.add(100)
           {
               Controller.setHighChamber()
           }
       }
    )
    private val setHuman = serial(
        execute{
            Linkage.setPosition(Linkage.ZERO_POSITION)
            Claw.openFull()
            Pivot.setPositionDeg(125.0)
            Lift.setTargetPosition(0)

        }

    )
    private val collectSub = serial(
        execute{

            Joint.setPosition(Joint.COLLECT_POSITION)
            Extendo.setTargetPositionSafe(extendoGain+250)
            actionQueue.add(400)
            {
                Joint.setPosition(0.28)
                actionQueue.add(100)
                {
                    Extendo.setTargetPositionSafe(Extendo.TRANSFER_POSITION)
                }
            }
        }
    )
    private val seqTransfer = serial(
        execute{
            Controller.setTransfer()
            Joint.setPosition(0.28)
            actionQueue.add(150)
            {
                Linkage.setPosition(0.23)
                actionQueue.add(250)
                {
                    Claw.closeStrong()
                    Fold.unfold()
                    actionQueue.add(100)
                    {
                        Intake.setPower(0.0)
                        Linkage.setPosition(Linkage.ZERO_POSITION)
                        Pivot.setPositionDeg(113.0)
                        Joint.setPosition(Joint.PARALLEL_POSITION)
                        Fold.fold()
                        actionQueue.add(500)
                        {
                            Claw.openFull()
                            actionQueue.add(700)
                            {
                                Linkage.setPosition(Linkage.MAX_POSITION)
                            }
                        }
                    }
                }
            }
        }
    )

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
                goTo(35.0,41.0,180.0)//preload
                Controller.setHighChamber()
            },

            sleepms(500),
            execute{
                Extendo.setTargetPositionSafe(0)
                goTo(22.0,30.0,180.0)
            },

            sleepms(500),
            execute{
                goTo(8.0,37.0,270.0)

            },

            sleepms(1300),
            execute{
                Linkage.setPosition(Linkage.MAX_POSITION)
                goTo(8.0,29.0,270.0)

            },

            sleepms(400),
            execute{
                Claw.openFull()
            },
            execute{goTo(15.5,40.0,180.0)},
            sleepms(400),
            execute{goTo(59.5,34.5,180.0)},
            sleepms(100),

            setHuman,
            sleepms(1200),
            seqCollectHuman,

            sleepms(100),
            //second cycle
            execute{
                //Sweeper.setPosition(Sweeper.VERTICAL_POSITION)
                goTo(22.0,30.0,180.0)
            },
            sleepms(500),
            execute{
                goTo(6.5,37.0,270.0)
            },
            sleepms(1400 ),
            execute{
                Linkage.setPosition(Linkage.MAX_POSITION)
            },
            execute{
                Fold.fold()
                Joint.setPosition(0.42)
                Extendo.setTargetPositionSafe(extendoGain)
                Intake.setPower(1.0)
                goTo(6.5,30.0,270.0)//collect sequence from submersible
            },
            sleepms(500),
            execute{
                Claw.open()
            },
            execute{
                goTo(6.5 + yGain,31.0,270.0)//collect sequence from submersible
            },
            sleepms(100),
            collectSub,

            sleepms(500),//to human
            execute{goTo(57.5,34.5,180.0)},
            sleepms(400),
            seqTransfer,
            sleepms(2000),
            seqCollectHuman,
            sleepms(100),
            execute{
                Extendo.setTargetPositionSafe(0)
                Linkage.setPosition(Linkage.ZERO_POSITION)
                //third sample
                goTo(22.0,30.0,180.0)
            },
            sleepms(400),
            execute{
                goTo(5.0,38.0,270.0)
            },
            sleepms(1500 ),

            execute{
                goTo(5.0,30.0,270.0)
                Linkage.setPosition(Linkage.MAX_POSITION)
                Claw.closeStrong()
            },

            sleepms(400),
            execute{
                Claw.openFull()
            },

            //sleepms(150)to human
            execute{goTo(15.5,39.5,180.0)},
            sleepms(350),
            execute{goTo(59.5,34.5,180.0)},
            sleepms(150),
            setHuman,

            sleepms(1250),
            seqCollectHuman,
            sleepms(100),

            //fourth sample
            execute{
                goTo(22.0,30.0,180.0)
            },
            sleepms(500),
            execute{
                goTo(3.5,37.0,270.0)
            },
            sleepms(1500 ),
            execute{
                goTo(3.5,30.0,270.0)
                    Linkage.setPosition(Linkage.MAX_POSITION)
            },
            sleepms(400),
            execute{
                Claw.openFull()
            },
            sleepms(100),//to human
            execute{goTo(5.0,37.5,180.0)},
            sleepms(300),
            execute{goTo(59.0,34.5,180.0)},

            sleepms(200),
            setHuman,
            sleepms(1150),
            seqCollectHuman,
            sleepms(100),

            //fifth sample
            execute{
                goTo(22.0,30.0,180.0)
            },
            sleepms(500),
            execute{
                goTo(9.0,38.0,270.0)
            },
            sleepms(1500),
            execute{
                goTo(9.0,30.0,270.0)//collect sequence from submersible
                Linkage.setPosition(Linkage.MAX_POSITION)
            },
            sleepms(400),
            execute{
                Claw.openFull()

            },
            sleepms(150),//to human
            execute{goTo(59.0,34.5,180.0)},
            sleepms(500),
            setHuman,
            sleepms(1150),
            seqCollectHuman,
            sleepms(100),
            //6th sample
            execute{
                goTo(22.0,30.0,180.0)
            },
            sleepms(500),
            execute{
                goTo(11.0,38.0,270.0)
            },
            sleepms(1500 ),
            execute{
                goTo(11.0,28.0,270.0)//collect sequence from submersible
                Linkage.setPosition(Linkage.MAX_POSITION)
            },
            sleepms(600),
            execute{
                Claw.open()
            }
        )
    }
}