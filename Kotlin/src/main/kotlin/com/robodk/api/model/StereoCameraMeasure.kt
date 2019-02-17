package com.robodk.api.model

import com.robodk.api.Mat

data class StereoCameraMeasure(
    val pose1: Mat,
    val pose2: Mat,
    val nPoints1: Int,
    val nPoints2: Int,
    val time: Int,
    val status: Int
)