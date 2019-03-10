package com.robodk.api

import com.robodk.api.model.*
import org.apache.commons.math3.linear.RealMatrix
import java.awt.Color
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

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
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val children: List<Item>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val selectedFeature: Triple<Boolean, ObjectSelectionType, Int>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val jointsHome: DoubleArray
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val jointLimits: Pair<DoubleArray, DoubleArray>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val busy: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val instructionCount: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override var parent: Item
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
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
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override var pose: RealMatrix
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var geometryPose: RealMatrix
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var poseTool: RealMatrix
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var poseFrame: RealMatrix
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var poseAbs: RealMatrix
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var color: Color
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(otherItem: Item): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newLink() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(filename: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setParentStatic(parent: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun attachClosest(): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detachClosest(parent: Item?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun detachAll(parent: Item?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setVisible(visible: Boolean, visibleFrame: VisibleRefType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAsCollided(collided: Boolean, robotLinkId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPoseFrame(frameItem: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPoseTool(toolItem: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun recolor(toColor: DoubleArray, fromColor: DoubleArray, tolerance: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun recolor(toColor: Color, fromColor: Color, tolerance: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setColor(shapeId: Int, tocolor: Color) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTransparency(alpha: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun scale(scale: DoubleArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCurve(curvePoints: RealMatrix, addToRef: Boolean, projectionType: ProjectionType): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun projectPoints(points: RealMatrix, projectionType: ProjectionType): RealMatrix {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPoints(featureType: ObjectSelectionType, featureId: Int): Pair<String, RealMatrix> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMachiningParameters(ncfile: String, partObj: Item?, options: String): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAsCartesianTarget() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setAsJointTarget() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun objectLink(linkId: Int): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLink(typeLinked: ItemType): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRobot(robot: Item?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFrame(frame: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setFrame(frame: RealMatrix) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTool(tool: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTool(tool: RealMatrix) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTool(toolPose: RealMatrix, toolName: String): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun solveIK_All(pose: RealMatrix, tool: RealMatrix?, reference: RealMatrix?): RealMatrix {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun connect(robotIp: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveJ(itemTarget: Item, blocking: Boolean) {
        if (itemTarget.itemType == ItemType.PROGRAM) {

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveL(joints: DoubleArray, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveL(target: RealMatrix, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveC(itemTarget1: Item, itemTarget2: Item, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveC(joints1: DoubleArray, joints2: DoubleArray, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveC(target1: RealMatrix, target2: RealMatrix, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveJ_Test(j1: DoubleArray, j2: DoubleArray, minstepDeg: Double): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveJ_Test_Blend(
        j1: DoubleArray,
        j2: DoubleArray,
        j3: DoubleArray,
        blendDeg: Double,
        minstepDeg: Double
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveL_Test(j1: DoubleArray, j2: DoubleArray, minstepDeg: Double): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setSpeed(speedLinear: Double, accelLinear: Double, speedJoints: Double, accelJoints: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRounding(rounding: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSequence(sequence: RealMatrix) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRunType(programExecutionType: ProgramExecutionType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runProgram(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runCode(parameters: String?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runCodeCustom(code: String, runType: ProgramRunType): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pause(timeMs: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setDO(ioVar: String, ioValue: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun waitDI(ioVar: String, ioValue: String, timeoutMs: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCustomInstruction(
        name: String,
        pathRun: String,
        pathIcon: String,
        blocking: Boolean,
        cmdRunOnRobot: String
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addMoveJ(itemTarget: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addMoveL(itemTarget: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showInstructions(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTargets(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInstruction(instructionId: Int): ProgramInstruction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setInstruction(instructionId: Int, instruction: ProgramInstruction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(): UpdateResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(
        collisionCheck: CollisionCheckOptions,
        timeoutSec: Int,
        linStepMm: Double,
        jointStepDeg: Double
    ): UpdateResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun instructionList(): Pair<Int, RealMatrix> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getInstructionListJoints(
        mmStep: Double,
        degStep: Double,
        saveToFile: String,
        collisionCheck: CollisionCheckOptions,
        flags: ListJointsType,
        timeoutSec: Int,
        time_step: Double
    ): InstructionListJointsResult {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun instructionListJoints(
        mmStep: Double,
        degStep: Double,
        saveToFile: String,
        collisionCheck: CollisionCheckOptions,
        flags: ListJointsType,
        timeoutSec: Int,
        time_step: Double
    ): Triple<Int, String, RealMatrix> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun finish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString() = "ItemLink: ID: $itemId Type: $itemType"

    private fun <T> Link.session(message: String? = null, block :(Link) -> T): T
    {
        if (message != null) {
            log.info("\n\n### ItemLink: $message ###\n")
        }

        link.checkConnection()
        val result = block(link)
        link.checkStatus()
        return result
    }
}