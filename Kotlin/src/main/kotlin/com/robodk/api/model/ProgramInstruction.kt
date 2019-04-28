package com.robodk.api.model

import org.apache.commons.math3.linear.RealMatrix

class ProgramInstruction (

    var name : String = "",
    var instructionType : InstructionType = InstructionType.MOVE,

    // The following parameter are only Valid if InstructionType is InstructionType.Move
    // TODO: Subclase ProgramMoveInstruction

    var moveType : MoveType = MoveType.Linear,
    var isJointTarget : Boolean = false,
    var target : RealMatrix,
    var joints : DoubleArray
)
