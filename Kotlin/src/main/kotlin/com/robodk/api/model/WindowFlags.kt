package com.robodk.api.model

import java.util.*

/** RoboDkLink Window Flags */
enum class WindowFlags(override val value: Int) : NumberedEnum<Int, WindowFlags> {
    TREE_ACTIVE(1),
    VIEW_3D_ACTIVE(2),
    LEFT_CLICK(4),
    RIGHT_CLICK(8),
    DOUBLE_CLICK(16),
    MENU_ACTIVE(32),
    MENU_FILE_ACTIVE(64),
    MENU_EDIT_ACTIVE(128),
    MENU_PROGRAM_ACTIVE(256),
    MENU_TOOLS_ACTIVE(512),
    MENU_UTILITIES_ACTIVE(1024),
    MENU_CONNECT_ACTIVE(2048),
    WINDOW_KEYS_ACTIVE(4096),
    TREE_VISIBLE(8192),
    REFERENCES_VISIBLE(16384),
    NONE(0),
    ALL(0xFFFF);

    companion object {
        val MenuActiveAll
            get() = EnumSet.of(
                MENU_ACTIVE,
                MENU_FILE_ACTIVE,
                MENU_EDIT_ACTIVE,
                MENU_PROGRAM_ACTIVE,
                MENU_TOOLS_ACTIVE,
                MENU_UTILITIES_ACTIVE,
                MENU_CONNECT_ACTIVE
            )
    }

    override val values get() = values().toList()
}
