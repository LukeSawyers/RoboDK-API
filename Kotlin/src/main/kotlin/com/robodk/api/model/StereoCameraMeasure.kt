package com.robodk.api.model

import org.apache.commons.math3.linear.RealMatrix

data class StereoCameraMeasure(
    val pose1: RealMatrix,
    val pose2: RealMatrix,
    val nPoints1: Int,
    val nPoints2: Int,
    val time: Int,
    val status: Int
)
