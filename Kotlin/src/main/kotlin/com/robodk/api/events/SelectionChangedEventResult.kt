package com.robodk.api.events

import com.robodk.api.Item
import com.robodk.api.Mat
import com.robodk.api.model.ObjectSelectionType

class SelectionChangedEventResult(
    item: Item,
    val objectSelectionType: ObjectSelectionType,
    val shapeId: Int,
    val clickedOffset: Mat
) : EventResult(EventType.SELECTION_3D_CHANGED, item)