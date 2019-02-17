package com.robodk.api.model

import java.util.*

enum class ItemFlags : NumberedEnum<Int, ItemFlags> {
    NONE,
    SELECTABLE,             // Allow selecting the item
    EDITABLE,               // Allow editing the item
    DRAG_ALLOWED,            // allow dragging the item
    DROP_ALLOWED,            // Allow dropping nested items
    ENABLED,               // Enable this item in the tree
    AUTO_TRI_STATE,          // TODO: DOCUMENTATION
    NO_CHILDREN,           // TODO: DOCUMENTATION
    USER_TRI_STATE;        // TODO: DOCUMENTATION

    companion object {
        val ALL
            get() = EnumSet.of(
                ItemFlags.AUTO_TRI_STATE,
                ItemFlags.ENABLED,
                ItemFlags.DROP_ALLOWED,
                ItemFlags.DROP_ALLOWED,
                ItemFlags.EDITABLE,
                ItemFlags.SELECTABLE
            )
    }

    override val value get() = if(ordinal > 0) 1 shl ordinal -1 else 0
    override val values = values().toList()
}