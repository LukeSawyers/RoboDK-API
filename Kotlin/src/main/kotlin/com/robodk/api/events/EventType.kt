package com.robodk.api.events

import com.robodk.api.model.NumberedEnum

/**
 * Script Execution Mode
 */
enum class EventType : NumberedEnum<Int, EventType> {
    NO_EVENT,

    /**
     * One object (Item) has been selected or deselected from the tree
     */
    SELECTION_TREE_CHANGED,

    /**
     * The location of an object, robot or reference frame was moved
     */
    ITEM_MOVED,

    /**
     * A reference frame has been picked, or left clicked (any tool, reference frame or object)
     */
    REFERENCE_PICKED,

    /**
     * A reference frame has been released (any tool or reference frame or object)
     */
    REFERENCE_RELEASED,

    /**
     * A tool has changed (the TCP has been modified)
     */
    TOOL_MODIFIED,

    /**
     * A new program to follow the ISO 9283 cube has been created
     */
    ISO_CUBE_CREATED,

    /**
     * One object (Item) has been selected or deselected from 3D view
     */
    SELECTION_3D_CHANGED;

    override val value get() = ordinal
    override val values = EventType.values().toList()
}