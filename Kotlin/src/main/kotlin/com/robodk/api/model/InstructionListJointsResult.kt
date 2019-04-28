package com.robodk.api.model

class InstructionListJointsResult(
    var errorCode: Int = 0,
    var errorMessage: String = "",
    var jointList: MutableList<JointsResult> = mutableListOf()
) {
    class JointsResult (
        var joints: DoubleArray = doubleArrayOf(),
        var error: ErrorPathType
    )
}
