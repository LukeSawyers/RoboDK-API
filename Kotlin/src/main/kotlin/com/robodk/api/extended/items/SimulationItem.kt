package com.robodk.api.extended.items

import com.robodk.api.Item
import com.robodk.api.model.ItemType

interface SimulationItem {
    val backingItem: Item
    val itemId: Long get() = backingItem.itemId
    val itemType: ItemType get() = backingItem.itemType
}