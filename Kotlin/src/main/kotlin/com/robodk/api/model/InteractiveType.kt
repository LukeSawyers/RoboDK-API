package com.robodk.api.model

/**
/// Modes to use with SetInteractiveMode to change the behavior of the 3D navigation or screen selection.
/// The following groups of flags can be managed independently:
/// 3D View: [None, Rectangle, Rotate, Zoom, Pan].
/// Move References: [MoveNone, MoveReferences, MoveTools]
 */
enum class InteractiveType: NumberedEnum<Int, InteractiveType> {

    /** Default 3D mouse behavior. Same as if we selected Escape and the user is ready to select his own choice. */
    NONE,

    /** Select one or more items (3D view) */
    RECTANGLE,

    /** Set to rotate the view on click (3D view) */
    ROTATE,

    /** Set Zoom mode (3D view) */
    ZOOM,

    /** Set Pan mode (3D view) */
    PAN,

    /** Set to move objects (same behavior as holding Alt) */
    MOVE_REFERENCES,

    /**
     * Set to move objects or tools changing the TCP definition or withoug changing the absolute position of nested
     * references (same behavior as holding Alt+Shift)
     */
    MOVE_TOOLS,

    /** Do not move any objects */
    MOVE_NONE;

    override val value: Int get() = ordinal

    override val values get() = values().toList()
}