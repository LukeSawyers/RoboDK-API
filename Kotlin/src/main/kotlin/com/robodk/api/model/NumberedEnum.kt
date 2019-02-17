package com.robodk.api.model

interface NumberedEnum<N : Number, T : NumberedEnum<N, T>> {
    val value: N
    val values: List<T>
}

fun <N, T : NumberedEnum<N, T>> T.fromValue(value: N): T {
    return this.values.single { it.value == value }
}