package com.robodk.api

import java.lang.IllegalArgumentException
import java.nio.ByteBuffer

fun Int.toBytes(): ByteArray = ByteArray(4).also { ByteBuffer.wrap(it).putInt(this) }

fun Long.toBytes(): ByteArray = ByteArray(8).also { ByteBuffer.wrap(it).putLong(this) }

fun Double.toBytes(): ByteArray = ByteArray(8).also { ByteBuffer.wrap(it).putDouble(this) }

fun ByteArray.toInt(): Int {
    if (this.size != 4) {
        throw IllegalArgumentException("Need 8 bytes to convert an Int")
    }

    return ByteBuffer.wrap(this).int
}

fun ByteArray.toLong(): Long {
    if (this.size != 8) {
        throw IllegalArgumentException("Need 8 bytes to convert a Long")
    }

    return ByteBuffer.wrap(this).long
}

fun ByteArray.toDouble(): Double {
    if (this.size != 8) {
        throw IllegalArgumentException("Need 8 bytes to convert a Long")
    }

    return ByteBuffer.wrap(this).double
}

