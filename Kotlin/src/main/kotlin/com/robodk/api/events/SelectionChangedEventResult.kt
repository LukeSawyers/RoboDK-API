package com.robodk.api.events

import com.robodk.api.Item
import com.robodk.api.model.ObjectSelectionType
import org.apache.commons.math3.linear.RealMatrix

class SelectionChangedEventResult(
    item: Item,
    val objectSelectionType: ObjectSelectionType,
    val shapeId: Int,
    val clickedOffset: RealMatrix
) : EventResult(EventType.SELECTION_3D_CHANGED, item)
