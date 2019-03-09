package com.robodk.api

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix

fun matrixOf(rows: Int, cols: Int, vararg values: Double): RealMatrix = Array2DRowRealMatrix(
    (0 until rows).map { row ->
        (0 until cols).map { col ->
            values.getOrNull(row * cols + col) ?: 0.0
        }.toDoubleArray()
    }.toTypedArray()
)
val RealMatrix.inverse: RealMatrix get() = MatrixUtils.inverse(this)

val RealMatrix.isHomogenous get() = this.rowDimension == 4 && this.columnDimension == 4

fun RealMatrix.toDoubleArray() : DoubleArray {
    val sendList = mutableListOf<Double>()
    for(col in 0 until columnDimension) {
        for(row in 0 until rowDimension) {
            sendList.add(getEntry(row, col))
        }
    }
    return sendList.toDoubleArray()
}

/**
 * Produces a translation array based on values [0,0], [1,0], [2,0] of this translation matrix.
 */
fun RealMatrix.toTranslationMatrix() : RealMatrix = Array2DRowRealMatrix(
    arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, getEntry(0, 0)),
        doubleArrayOf(0.0, 1.0, 0.0, getEntry(1, 0)),
        doubleArrayOf(0.0, 0.0, 1.0, getEntry(2, 0)),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)
