package com.robodk.api.model

/** TCP calibration types */
enum class TcpCalibrationType : NumberedEnum<Int, TcpCalibrationType> {

    CALIBRATE_TCP_BY_POINT,
    CALIBRATE_TCP_BY_PLANE;

    override val value: Int get() = ordinal
    override val values = values().toList()
}