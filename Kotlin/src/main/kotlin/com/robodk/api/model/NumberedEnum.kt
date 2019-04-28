package com.robodk.api.model

/**
 * Interface for an enum that can be enumerated by a number.
 *
 * @param M The number type to used to enumerat enum values.
 * @param T The implementing type.
 */
interface NumberedEnum<N : Number, T : NumberedEnum<N, T>> {
    val value: N
    val values: List<T>
}

/**
 * Get an enum from the supplied number.
 */
fun <N, T : NumberedEnum<N, T>> T.fromValue(value: N): T {
    return this.values.single { it.value == value }
}
