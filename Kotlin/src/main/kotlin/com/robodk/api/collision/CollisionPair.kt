package com.robodk.api.collision

import com.robodk.api.Item

/**
 * A pair of collision items.
 */
class CollisionPair(val collisionItem1: CollisionItem, val collisionItem2: CollisionItem) {

    constructor(item1: Item, robotLinkId1: Int, item2: Item, robotLinkId2: Int) : this(
        CollisionItem(item1, robotLinkId1),
        CollisionItem(item2, robotLinkId2)
    )

    constructor(item1: Item, item2: Item) : this(
        CollisionItem(item1),
        CollisionItem(item2)
    )

    val item1 get() = collisionItem1.item
    val item2 get() = collisionItem2.item

    val robotLinkId1 get() = collisionItem1.robotLinkId
    val robotLinkId2 get() = collisionItem2.robotLinkId
}
