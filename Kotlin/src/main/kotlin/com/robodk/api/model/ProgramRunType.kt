package com.robodk.api.model

/// <summary>
/// Instruction program call type
/// </summary>
enum class ProgramRunType: NumberedEnum<Int, ProgramRunType> {
    CALL_PROGRAM, // Program call
    INSERT_CODE,  // Insert raw code in the generated program
    START_THREAD, // Start a new process
    COMMENT;      // Add a comment in the code

    override val value get() = ordinal
    override val values = values().toList()
}