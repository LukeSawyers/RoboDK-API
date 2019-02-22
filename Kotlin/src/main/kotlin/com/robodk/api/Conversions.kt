package com.robodk.api

import java.lang.IllegalArgumentException

fun Int.toBytes(): ByteArray = byteArrayOf(
    (this shr 24).toByte(),
    (this shr 16).toByte(),
    (this shr 8).toByte(),
    this.toByte()
)

fun ByteArray.toLong(): Long {
    if (this.size != 8) {
        throw IllegalArgumentException("Need 8 bytes to convert a Long")
    }

    return (this[0].toLong() shl 56) +
            (this[1].toLong() shl 48) +
            (this[2].toLong() shl 40) +
            (this[3].toLong() shl 32) +
            (this[4].toLong() shl 24) +
            (this[5].toLong() shl 16) +
            (this[6].toLong() shl 8) +
            this[7].toLong()
}

fun ByteArray.toInt(): Int {
    if (this.size != 4) {
        throw IllegalArgumentException("Need 8 bytes to convert an Int")
    }

    return (this[0].toInt() shl 24) +
            (this[1].toInt() shl 16) +
            (this[2].toInt() shl 8) +
            this[3].toInt()
}