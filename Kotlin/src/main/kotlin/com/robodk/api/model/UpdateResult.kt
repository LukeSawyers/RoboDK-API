package com.robodk.api.model

/**
 * Represents the result of a software update.
 */
class UpdateResult(
    val validInstructions: Double,
    val programTime: Double,
    val programDistance: Double,
    val validRatio: Double,
    val message: String
)
