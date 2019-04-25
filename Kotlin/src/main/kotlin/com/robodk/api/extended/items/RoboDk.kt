package com.robodk.api.extended.items

import com.robodk.api.Item
import com.robodk.api.RoboDk
import com.robodk.api.exception.RdkException
import com.robodk.api.model.ItemType

val itemInterfaceTypeMap = mapOf(
    Station::class to ItemType.STATION,
    Robot::class to ItemType.ROBOT,
    Frame::class to ItemType.FRAME,
    Tool::class to ItemType.TOOL,
    com.robodk.api.extended.items.Object::class to ItemType.OBJECT,
    MachineTarget::class to ItemType.TARGET,
    Program::class to ItemType.PROGRAM,
    Instruction::class to ItemType.INSTRUCTION,
    PythonProgram::class to ItemType.PROGRAM_PYTHON,
    Machining::class to ItemType.MACHINING,
    BallBarValidation::class to ItemType.BALL_BAR_VALIDATION,
    CalibrationProject::class to ItemType.CALIB_PROJECT,
    ValidIso9283::class to ItemType.VALID_ISO_9283
)

val itemTypeDefaultClassMap = mapOf<ItemType, (Item) -> AbstractSimulationItem>(
    ItemType.STATION to { item -> StationLink(item) },
    ItemType.ROBOT to { item -> StationLink(item) },
    ItemType.FRAME to { item -> StationLink(item) },
    ItemType.TOOL to { item -> StationLink(item) },
    ItemType.OBJECT to { item -> StationLink(item) },
    ItemType.TARGET to { item -> MachineTargetLink(item) },
    ItemType.PROGRAM to { item -> ProgramLink(item) },
    ItemType.INSTRUCTION to { item -> InstructionLink(item) },
    ItemType.PROGRAM_PYTHON to { item -> PythonProgramLink(item) },
    ItemType.MACHINING to { item -> MachineTargetLink(item) },
    ItemType.BALL_BAR_VALIDATION to { item -> BallBarValidationLink(item) },
    ItemType.CALIB_PROJECT to { item -> CalibrationProjectLink(item) },
    ItemType.VALID_ISO_9283 to { item -> ValidIso9283Link(item) }
)

inline fun <reified T : SimulationItem> RoboDk.getTypedItemByName(name: String): T {
    val cls = T::class
    if (cls !in itemInterfaceTypeMap) {
        throw RdkException("Simulation Item type $cls is not currently supported")
    }
    val itemType = itemInterfaceTypeMap.getValue(cls)
    val item = getItemByName(name, itemType)

    @Suppress("UNCHECKED_CAST")
    return itemTypeDefaultClassMap.getValue(itemType).invoke(item!!) as T
}
