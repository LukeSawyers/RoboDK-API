package com.robodk.api.model

/**
 *  Object selection features:
 */
enum class ObjectSelectionType : NumberedEnum<Int, ObjectSelectionType> {
    NONE,
    SURFACE,
    CURVE,
    POINT;

    override val value get() = ordinal
    override val values get() = values().toList()
}