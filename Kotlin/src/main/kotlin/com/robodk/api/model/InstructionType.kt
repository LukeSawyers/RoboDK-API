package com.robodk.api.model

enum class InstructionType(val value: Int) {
    INVALID(-1),
    MOVE(0),
    MOVEC(1),
    CHANGE_SPEED(2),
    CHANGE_FRAME(3),
    CHANGE_TOOL(4),
    CHANGE_ROBOT(5),
    PAUSE(6),
    EVENT(7),
    CODE(8),
    PRINT(9)
}
