package com.robodk.api.model

/** State of the RoboDK window. */
enum class WindowState(override val value: Int) : NumberedEnum<Int, WindowState> {

    /** Hidden. */
    HIDDEN(-1),

    /** Visible. */
    SHOW(0),

    /** Minimize Window. */
    MINIMIZED(1),

    /** Show normal window (last known state). */
    NORMAL(2),

    /** Show maximized window. */
    MAXIMIZED(3),

    /** Show fullscreen window. */
    FULLSCREEN(4),

    /** Show maximized window without the toolbar and without the menu. */
    CINEMA(5),

    /** Show fullscreen window without the toolbar and without the menu. */
    FULLSCREENCINEMA(6);

    override val values get() = values().toList()
}
