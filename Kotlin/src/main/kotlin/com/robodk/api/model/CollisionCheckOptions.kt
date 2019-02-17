package com.robodk.api.model

/**
 * Collision checking options
 */
enum class CollisionCheckOptions : NumberedEnum<Int, CollisionCheckOptions> {
    COLLISION_CHECK_OFF,
    COLLIION_CHECK_ON;

    override val value get() = ordinal
    override val values = values().toList()
}