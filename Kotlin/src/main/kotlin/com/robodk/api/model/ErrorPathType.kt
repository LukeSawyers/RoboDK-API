package com.robodk.api.model

/**
 *  Flags used as Error code for the returned values from InstructionJointsList().
 */
enum class ErrorPathType(override val value: Int) : NumberedEnum<Int, ErrorPathType> {

    /**
     *  none of the flags is set -> No Error.
     */
    NONE(0),

    /**
     *  One or more points is not reachable.
     */
    KINEMATIC(0x1), // 0b001

    /**
     *  The path reaches the limit of joint axes.
     */
    PATH_LIMIT(0x2), // 0b010

    /**
     *  The robot reached a singularity point.
     */
    PATH_SINGULARITY(0x4), // 0b100

    /**
     *  The robot is too close to a singularity.
     *  Lower the singularity tolerance to allow the robot to continue.
     */
    PATH_NEAR_SINGULARITY(0x8), // 0b1000

    /**
     *  Collision detected.
     */
    COLLISION(0x20); // 0b100000

    override val values get() = values().toList()
}
