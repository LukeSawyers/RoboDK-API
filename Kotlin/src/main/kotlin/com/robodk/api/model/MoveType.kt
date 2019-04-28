package com.robodk.api.model

enum class MoveType(val value: Int) {
    Invalid(-1),
    Joint(1),
    Linear(2),
    Circular(3)
}
