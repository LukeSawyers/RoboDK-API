package com.robodk.api

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix

/** Creates a new matrix with the specified number of rows and columns and populates it with the supplied values. */
fun matrixOf(rows: Int, cols: Int, vararg values: Double): RealMatrix = Array2DRowRealMatrix(
    (0 until rows).map { row ->
        (0 until cols).map { col ->
            values.getOrNull(row * cols + col) ?: 0.0
        }.toDoubleArray()
    }.toTypedArray()
)

/** Creates a new matrix using the supplied double arrays as rows in the matrix.*/
fun matrixOf(rows: List<DoubleArray>): RealMatrix = Array2DRowRealMatrix(rows.toTypedArray())

/** Creates a matrix using the supplied double arrays as rows in the matrix. */
fun matrixOf(vararg rows: DoubleArray) = matrixOf(rows.toList())

/** Creates the inverse matrix. */
val RealMatrix.inverse: RealMatrix get() = MatrixUtils.inverse(this)

/** True if this matrix is homogenous. */
val RealMatrix.isHomogenous get() = this.rowDimension == 4 && this.columnDimension == 4

/**
 * Iterate over every column and row in this matrix.
 * @param block The code to be executed per row and column, supplied with the column index and row index.
 */
fun RealMatrix.forEachColumnAndRow(block: (Int, Int) -> Unit) {
    for (j in 0 until columnDimension) {
        for (i in 0 until rowDimension) {
            block(j, i)
        }
    }
}

/** Converts this matrix to a double array. */
fun RealMatrix.toDoubleArray(): DoubleArray {
    val sendList = mutableListOf<Double>()
    forEachColumnAndRow { col, row ->
        sendList.add(getEntry(row, col))
    }
    return sendList.toDoubleArray()
}

/**
 * Produces a translation array based on values [0,0], [1,0], [2,0] of this translation matrix.
 */
fun RealMatrix.toTranslationMatrix(): RealMatrix = Array2DRowRealMatrix(
    arrayOf(
        doubleArrayOf(1.0, 0.0, 0.0, getEntry(0, 0)),
        doubleArrayOf(0.0, 1.0, 0.0, getEntry(1, 0)),
        doubleArrayOf(0.0, 0.0, 1.0, getEntry(2, 0)),
        doubleArrayOf(0.0, 0.0, 0.0, 1.0)
    )
)
