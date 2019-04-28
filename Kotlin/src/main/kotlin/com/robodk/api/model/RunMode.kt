package com.robodk.api.model

/**
 * Script execution Mode.
 */
enum class RunMode(val value: Int) {
    /**
     * performs the simulation moving the robot (default).
     */
    SIMULATE(1),

    /**
     * Performs a quick check to validate the robot movements.
     */
    QUICK_VALIDATE(2),

    /**
     * Makes the robot program.
     */
    MAKE_ROBOT_PROGRAM(3),

    /**
     * Makes the robot program and updates it to the robot.
     */
    MAKE_ROBOT_PROGRAM_AND_UPLOAD(4),

    /**
     * Makes the robot program and starts it on the robot (independently from the PC).
     */
    MAKE_ROBOT_PROGRAM_AND_START(5),

    /**
     * Moves the real robot from the PC (PC is the client) the robot behaves like a server).
     */
    RUN_ROBOT(6),
}
