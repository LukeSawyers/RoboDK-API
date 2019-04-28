package com.robodk.api.model

/**
 *  Type of information returned by InstructionListJoints and GetInstructionListJoints.
 */
enum class ListJointsType : NumberedEnum<Int, ListJointsType> {

    /**
     *  Same result as Position (fastest).
     */
    ANY,

    /**
     *  Return the joints position. The returned columns are organized in the following way:
     *  [J1, J2, ..., Jn, ERROR, MM_STEP, DEG_STEP, MOVE_ID]
     */
    POSITION,

    /**
     *  Include the speed information (also includes the time).
     *  The returned columns are organized in the following way:
     *  [J1, J2, ..., Jn, ERROR, MM_STEP, DEG_STEP, MOVE_ID,
     *  TIME, X_TCP, Y_TCP, Z_TCP,  Speed_J1, Speed_J2, ..., Speed_Jn]
     */
    SPEED,

    /**
     *  Return the speed and acceleration information (also includes the time).
     *  The returned columns are organized in the following way:
     *  [J1, J2, ..., Jn, ERROR, MM_STEP, DEG_STEP, MOVE_ID,
     *  TIME, X_TCP, Y_TCP, Z_TCP,
     *  Speed_J1, Speed_J2, ..., Speed_Jn,
     *  Accel_J1, Accel_J2, ..., Accel_Jn]
     */
    SPEED_AND_ACCELERATION,

    /**
     *  Make the result time-based so that the interval between joint values is provided at constant time steps.
     */
    TIME_BASED;

    override val value get() = ordinal
    override val values get() = values().toList()
}
