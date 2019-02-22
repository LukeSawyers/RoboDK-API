package com.robodk.api

import org.jblas.DoubleMatrix

/**
 * Produces a translation array based on values [1,1], [2,1], [3,1] of this translation matrix
 */
fun DoubleMatrix.toTranslationMatrix() = DoubleMatrix(
    4, 4,
    1.0, 0.0, 0.0, get(1, 1),
    0.0, 1.0, 0.0, get(2,1),
    0.0,0.0,1.0, get(3,1)
)
