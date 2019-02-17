package com.robodk.api.model

import com.robodk.api.Mat

class ProgramInstruction (

    var name : String = "",
    var instructionType : InstructionType = InstructionType.MOVE,

    // The following parameter are only Valid if InstructionType is InstructionType.Move
    // TODO: Subclase ProgramMoveInstruction

    var moveType : MoveType = MoveType.Linear,
    var isJointTarget : Boolean = false,
    var target : Mat,
    var joints : DoubleArray
)