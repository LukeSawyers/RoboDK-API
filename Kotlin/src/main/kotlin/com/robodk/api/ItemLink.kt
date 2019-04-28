package com.robodk.api

import com.robodk.api.model.CollisionCheckOptions
import com.robodk.api.model.InstructionListJointsResult
import com.robodk.api.model.ItemFlags
import com.robodk.api.model.ItemType
import com.robodk.api.model.ListJointsType
import com.robodk.api.model.ObjectSelectionType
import com.robodk.api.model.ProgramExecutionType
import com.robodk.api.model.ProgramInstruction
import com.robodk.api.model.ProgramRunType
import com.robodk.api.model.ProjectionType
import com.robodk.api.model.RunMode
import com.robodk.api.model.UpdateResult
import com.robodk.api.model.VisibleRefType
import org.apache.commons.math3.linear.RealMatrix
import java.awt.Color
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

/** Implementation of te [Item] interface through a [Link] interface. */
class ItemLink(
    val link: Link,
    override val itemId: Long = 0,
    override val itemType: ItemType = ItemType.ANY
) : Item {

    private var log = Logger.getLogger(this::class.java.name).also { it.level = Level.INFO }

    var logLevel
        get() = log.level
        set(value) {
            log.level = value
        }

    override val valid = itemId != 0L

    override val visible: Boolean
        get() = TODO("not implemented")
    override val children: List<Item>
        get() = TODO("not implemented")
    override val selectedFeature: Triple<Boolean, ObjectSelectionType, Int>
        get() = TODO("not implemented")
    override val jointsHome: DoubleArray
        get() = TODO("not implemented")
    override val jointLimits: Pair<DoubleArray, DoubleArray>
        get() = TODO("not implemented")
    override val busy: Boolean
        get() = TODO("not implemented")
    override val instructionCount: Int
        get() = TODO("not implemented")
    override var parent: Item
        get() = TODO("not implemented")
        set(value) {}

    override var name: String
        get() = link.session("Get Name") {
            it.sendLine(RdkGet.NAME)
                .sendItem(this)
                .receiveLine()
        }.second
        set(value) = link.session("Set Name: $value") {
            it.sendLine(RdkSet.NAME)
                .sendLine(value)
        }


    override var itemFlags: EnumSet<ItemFlags>
        get() = TODO("not implemented")
        set(value) {}

    override var pose: RealMatrix
        get() = TODO("not implemented")
        set(value) {}
    override var geometryPose: RealMatrix
        get() = TODO("not implemented")
        set(value) {}
    override var poseTool: RealMatrix
        get() = TODO("not implemented")
        set(value) {}
    override var poseFrame: RealMatrix
        get() = TODO("not implemented")
        set(value) {}
    override var poseAbs: RealMatrix
        get() = TODO("not implemented")
        set(value) {}
    override var color: Color
        get() = TODO("not implemented")
        set(value) {}

    override var joints: DoubleArray
        get() = link.session("Get Joints") {
            it.sendLine(RdkGet.THETAS)
                .sendItem(this)
                .receiveArray()
        }.second
        set(value) = link.session("Set Joints ${value.contentToString()}") {
            it.sendLine(RdkSet.THETAS)
                .sendArray(value)
                .sendItem(this)
                .checkStatus()
        }


    override fun clone(connectionLink: RoboDk): Item {
        TODO("not implemented")
    }

    override fun equalTo(otherItem: Item): Boolean {
        TODO("not implemented")
    }

    override fun newLink() {
        TODO("not implemented")
    }

    override fun save(filename: String) {
        TODO("not implemented")
    }

    override fun delete() {
        TODO("not implemented")
    }

    override fun setParentStatic(parent: Item) {
        TODO("not implemented")
    }

    override fun attachClosest(): Item {
        TODO("not implemented")
    }

    override fun detachClosest(parent: Item?): Item {
        TODO("not implemented")
    }

    override fun detachAll(parent: Item?) {
        TODO("not implemented")
    }

    override fun setVisible(visible: Boolean, visibleFrame: VisibleRefType) {
        TODO("not implemented")
    }

    override fun showAsCollided(collided: Boolean, robotLinkId: Int) {
        TODO("not implemented")
    }

    override fun setPoseFrame(frameItem: Item) {
        TODO("not implemented")
    }

    override fun setPoseTool(toolItem: Item) {
        TODO("not implemented")
    }

    override fun recolor(toColor: DoubleArray, fromColor: DoubleArray, tolerance: Double) {
        TODO("not implemented")
    }

    override fun recolor(toColor: Color, fromColor: Color, tolerance: Double) {
        TODO("not implemented")
    }

    override fun setColor(shapeId: Int, tocolor: Color) {
        TODO("not implemented")
    }

    override fun setTransparency(alpha: Double) {
        TODO("not implemented")
    }

    override fun scale(scale: DoubleArray) {
        TODO("not implemented")
    }

    override fun projectPoints(points: RealMatrix, projectionType: ProjectionType): RealMatrix {
        TODO("not implemented")
    }

    override fun getPoints(featureType: ObjectSelectionType, featureId: Int): Pair<String, RealMatrix> {
        TODO("not implemented")
    }

    override fun setMachiningParameters(ncfile: String, partObj: Item?, options: String): Item {
        TODO("not implemented")
    }

    override fun setAsCartesianTarget() {
        TODO("not implemented")
    }

    override fun setAsJointTarget() {
        TODO("not implemented")
    }

    override fun objectLink(linkId: Int): Item {
        TODO("not implemented")
    }

    override fun getLink(typeLinked: ItemType): Item {
        TODO("not implemented")
    }

    override fun setRobot(robot: Item?) {
        TODO("not implemented")
    }

    override fun setFrame(frame: Item) {
        TODO("not implemented")
    }

    override fun setFrame(frame: RealMatrix) {
        TODO("not implemented")
    }

    override fun setTool(tool: Item) {
        TODO("not implemented")
    }

    override fun setTool(tool: RealMatrix) {
        TODO("not implemented")
    }

    override fun addTool(toolPose: RealMatrix, toolName: String): Item {
        TODO("not implemented")
    }

    override fun solveFK(joints: DoubleArray) = link.session("Solve FK: ${joints.contentToString()}") {
        it.sendLine(RdkGet.FK)
            .sendArray(joints)
            .sendItem(this)
            .receivePose()
    }.second

    override fun jointsConfig(joints: DoubleArray) = link.session("Joints Config: ${joints.contentToString()}") {
        it.sendLine(RdkGet.THETAS_CONFIG)
            .sendArray(joints)
            .sendItem(this)
            .receiveArray()
    }.second


    override fun solveIK(
        pose: RealMatrix,
        jointsApprox: DoubleArray?,
        tool: RealMatrix?,
        reference: RealMatrix?
    ): DoubleArray {
        var sendPose = pose
        if (tool != null) {
            sendPose = sendPose.multiply(tool.inverse)
        }
        if (reference != null) {
            sendPose = reference.multiply(sendPose)
        }

        return link.session("Solve IK: $sendPose") {
            if (jointsApprox == null) {
                it.sendLine(RdkGet.IK)
                    .sendPose(sendPose)
            } else {
                it.sendLine(RdkGet.IK_JOINTS)
                    .sendPose(pose)
                    .sendArray(jointsApprox)
            }.sendItem(this)
                .receiveArray().second
        }
    }

    override fun solveIKAll(pose: RealMatrix, tool: RealMatrix?, reference: RealMatrix?): RealMatrix {
        TODO("not implemented")
    }

    override fun connect(robotIp: String): Boolean {
        TODO("not implemented")
    }

    override fun disconnect(): Boolean {
        TODO("not implemented")
    }

    override fun moveJ(itemTarget: Item, blocking: Boolean) {
        if (itemTarget.itemType == ItemType.PROGRAM) {
            // TODO
        } else {
            link.moveX(itemTarget, this, 1, blocking)
        }
    }

    override fun moveJ(joints: DoubleArray, blocking: Boolean) {
        link.moveX(joints, this, 1, blocking)
    }

    override fun moveJ(target: RealMatrix, blocking: Boolean) {
        link.moveX(target, this, 1, blocking)
    }

    override fun moveL(itemTarget: Item, blocking: Boolean) {
        TODO("not implemented")
    }

    override fun moveL(joints: DoubleArray, blocking: Boolean) {
        TODO("not implemented")
    }

    override fun moveL(target: RealMatrix, blocking: Boolean) {
        TODO("not implemented")
    }

    override fun moveC(itemTarget1: Item, itemTarget2: Item, blocking: Boolean) {
        TODO("not implemented")
    }

    override fun moveC(joints1: DoubleArray, joints2: DoubleArray, blocking: Boolean) {
        TODO("not implemented")
    }

    override fun moveC(target1: RealMatrix, target2: RealMatrix, blocking: Boolean) {
        TODO("not implemented")
    }

    override fun moveJTest(j1: DoubleArray, j2: DoubleArray, minstepDeg: Double): Int {
        TODO("not implemented")
    }

    override fun moveJTestBlend(
        j1: DoubleArray,
        j2: DoubleArray,
        j3: DoubleArray,
        blendDeg: Double,
        minstepDeg: Double
    ): Boolean {
        TODO("not implemented")
    }

    override fun moveLTest(j1: DoubleArray, j2: DoubleArray, minstepDeg: Double): Int {
        TODO("not implemented")
    }

    override fun setSpeed(speedLinear: Double, accelLinear: Double, speedJoints: Double, accelJoints: Double) {
        TODO("not implemented")
    }

    override fun setRounding(rounding: Double) {
        TODO("not implemented")
    }

    override fun showSequence(sequence: RealMatrix) {
        TODO("not implemented")
    }

    override fun stop() {
        TODO("not implemented")
    }

    override fun waitMove(timeoutSec: Double) {
        link.session {
            it.sendLine(RdkCommand.WAIT_MOVE)
                .sendItem(this)
        }
        val timeout = link.receiveTimeout
        link.receiveTimeout = (timeoutSec * 1000.0).toInt()
        link.checkStatus() // this will cause it to wait
        link.receiveTimeout = timeout
    }

    override fun makeProgram(filename: String, runMode: RunMode): Boolean {
        TODO("not implemented")
    }

    override fun setRunType(programExecutionType: ProgramExecutionType) {
        TODO("not implemented")
    }

    override fun runProgram(): Int {
        TODO("not implemented")
    }

    override fun runCode(parameters: String?): Int {
        TODO("not implemented")
    }

    override fun runCodeCustom(code: String, runType: ProgramRunType): Boolean {
        TODO("not implemented")
    }

    override fun pause(timeMs: Double) {
        TODO("not implemented")
    }

    override fun setDO(ioVar: String, ioValue: String) {
        TODO("not implemented")
    }

    override fun waitDI(ioVar: String, ioValue: String, timeoutMs: Double) {
        TODO("not implemented")
    }

    override fun addCustomInstruction(
        name: String,
        pathRun: String,
        pathIcon: String,
        blocking: Boolean,
        cmdRunOnRobot: String
    ) {
        TODO("not implemented")
    }

    override fun addMoveJ(itemTarget: Item) {
        TODO("not implemented")
    }

    override fun addMoveL(itemTarget: Item) {
        TODO("not implemented")
    }

    override fun showInstructions(show: Boolean) {
        TODO("not implemented")
    }

    override fun showTargets(show: Boolean) {
        TODO("not implemented")
    }

    override fun getInstruction(instructionId: Int): ProgramInstruction {
        TODO("not implemented")
    }

    override fun setInstruction(instructionId: Int, instruction: ProgramInstruction) {
        TODO("not implemented")
    }

    override fun update(): UpdateResult {
        TODO("not implemented")
    }

    override fun update(
        collisionCheck: CollisionCheckOptions,
        timeoutSec: Int,
        linStepMm: Double,
        jointStepDeg: Double
    ): UpdateResult {
        TODO("not implemented")
    }

    override fun instructionList(): Pair<Int, RealMatrix> {
        TODO("not implemented")
    }

    override fun getInstructionListJoints(
        mmStep: Double,
        degStep: Double,
        saveToFile: String,
        collisionCheck: CollisionCheckOptions,
        flags: ListJointsType,
        timeoutSec: Int,
        timeStep: Double
    ): InstructionListJointsResult {
        TODO("not implemented")
    }

    override fun instructionListJoints(
        mmStep: Double,
        degStep: Double,
        saveToFile: String,
        collisionCheck: CollisionCheckOptions,
        flags: ListJointsType,
        timeoutSec: Int,
        timeStep: Double
    ): Triple<Int, String, RealMatrix> {
        TODO("not implemented")
    }

    override fun finish() {
        TODO("not implemented")
    }

    override fun toString() = "ItemLink: ID: $itemId Type: $itemType"

}
