package com.robodk.api.model

/**
 * Type of an item(robot, object, target, reference frame, ...).
 */
enum class ItemType(override val value: Int) : NumberedEnum<Int, ItemType> {
    ANY(-1),
    STATION(1),
    ROBOT(2),
    FRAME(3),
    TOOL(4),
    OBJECT(5),
    TARGET(6),
    PROGRAM(8),
    INSTRUCTION(9),
    PROGRAM_PYTHON(10),
    MACHINING(11),
    BALL_BAR_VALIDATION(12),
    CALIB_PROJECT(13),
    VALID_ISO_9283(14);

    override val values get() = ItemType.values().toList()
}
