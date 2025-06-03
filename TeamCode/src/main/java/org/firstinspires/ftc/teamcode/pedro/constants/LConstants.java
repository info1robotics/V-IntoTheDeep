package org.firstinspires.ftc.teamcode.pedro.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class LConstants {
    static {
        PinpointConstants.hardwareMapName = "pinpoint";
        //TODO: change these according to your robot
        PinpointConstants.forwardY = 1;
        PinpointConstants.strafeX = -2.5;
        PinpointConstants.distanceUnit = DistanceUnit.MM;

        PinpointConstants.encoderResolution = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;

        PinpointConstants.forwardEncoderDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED;
        PinpointConstants.strafeEncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;

        PinpointConstants.useCustomEncoderResolution = false;
        PinpointConstants.customEncoderResolution = -1.0;

        PinpointConstants.useYawScalar = false;
        PinpointConstants.yawScalar = 1.0;

    }
}




