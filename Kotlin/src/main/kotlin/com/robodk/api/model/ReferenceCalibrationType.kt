package com.robodk.api.model

/**  Reference frame calibration types */
enum class ReferenceCalibrationType : NumberedEnum<Int, ReferenceCalibrationType> {

    FRAME_3P_P1_ON_X, // Calibrate by 3 points: [X, X+, Y+] (p1 on X axis)
    FRAME_3P_P1_IS_ORIGIN, // Calibrate by 3 points: [Origin, X+, XY+] (p1 is origin)
    FRAME_6P, // Calibrate by 6 points
    TURN_TABLE; // Calibrate turntable

    override val value: Int get() = ordinal
    override val values get() = values().toList()
}