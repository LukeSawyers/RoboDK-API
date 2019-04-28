package com.robodk.api.model

/**
 *  Program execution type
 */
enum class ProgramExecutionType(val value: Int) {

    /**
     *  Set the program to run on the simulator
     */
    RUN_ON_SIMULATOR(1),

    /**
     *  Set the program to run on the robot
     */
    RUN_ON_ROBOT(2)
}
