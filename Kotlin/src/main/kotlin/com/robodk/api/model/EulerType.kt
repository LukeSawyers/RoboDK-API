package com.robodk.api.model

/** Euler type */
enum class EulerType(override val value: Int) : NumberedEnum<Int, EulerType> {

    /** joints */
    JOINT_FORMAT(-1),

    /** generic */
    EULER_RX_RYP_RZPP(0),

    /** ABB RobotStudio */
    EULER_RZ_RYP_RXPP(1),

    /** Kawasaki, Adept, Staubli */
    EULER_RZ_RYP_RZPP(2),

    /** CATIA, SolidWorks */
    EULER_RZ_RXP_RZPP(3),

    /** Fanuc, Kuka, Motoman, Nachi */
    EULER_RX_RY_RZ(4),

    /** CRS */
    EULER_RZ_RY_RX(5),

    /** ABB Rapid */
    EULER_QUATERNION(6);

    override val values get() = values().toList()
}