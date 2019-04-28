package com.robodk.api

import java.nio.ByteBuffer

/** Serialize this integer into a byte array. */
fun Int.toBytes(): ByteArray = ByteArray(4).also { ByteBuffer.wrap(it).putInt(this) }

/** Serialize this long into a byte array. */
fun Long.toBytes(): ByteArray = ByteArray(8).also { ByteBuffer.wrap(it).putLong(this) }

/** Serialize this double into a byte array. */
fun Double.toBytes(): ByteArray = ByteArray(8).also { ByteBuffer.wrap(it).putDouble(this) }

/** Convert the values in this array into an integer. */
fun ByteArray.toInt(): Int {
    check(this.size == 4) { "Need 4 bytes to convert an Int" }
    return ByteBuffer.wrap(this).int
}

/** Convert the values in this array into a long. */
fun ByteArray.toLong(): Long {
    check(this.size == 8) { "Need 8 bytes to convert a Long" }
    return ByteBuffer.wrap(this).long
}

/** Convert the values in this array into a double. */
fun ByteArray.toDouble(): Double {
    check(this.size == 8) { "Need 8 bytes to convert a Long" }
    return ByteBuffer.wrap(this).double
}

