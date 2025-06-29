package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();


        TrajectoryActionBuilder firstTraj = myBot.getDrive().actionBuilder(new Pose2d(61.0, 14, Math.toRadians(180)))
                .strafeToConstantHeading(new Vector2d(-16.0, -52.0))
                .strafeToConstantHeading(new Vector2d(-52.1, -33.4))
                .strafeToLinearHeading(new Vector2d(-52.1, -48.4), Math.toRadians(-120));


//        TrajectoryActionBuilder secondTraj = firstTraj.endTrajectory()
//                        .strafeTo(new Vector2d(50, 50));



        myBot.runAction(
                new SequentialAction(
                        firstTraj.build()
//                        secondTraj.build()
                )
        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}