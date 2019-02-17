package com.robodk.api

import com.robodk.api.model.*
import java.awt.Color
import java.util.*

class ItemLink(
    val roboDkLink: RoboDkLink,
    override val itemId: Long = 0,
    override val itemType: ItemType = ItemType.ANY
) : Item {

    override val rdk: RoboDkLink
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val valid: Boolean
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
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
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var pose: Mat
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var geometryPose: Mat
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var poseTool: Mat
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var poseFrame: Mat
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var poseAbs: Mat
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var color: Color
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var joints: DoubleArray
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun clone(connectionLink: RoboDk): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setItemFlags(itemFlags: EnumSet<ItemFlags>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemFlags(): EnumSet<ItemFlags> {
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

    override fun addCurve(curvePoints: Mat, addToRef: Boolean, projectionType: ProjectionType): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun projectPoints(points: Mat, projectionType: ProjectionType): Mat {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPoints(featureType: ObjectSelectionType, featureId: Int): Pair<String, Mat> {
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

    override fun setFrame(frame: Mat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTool(tool: Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTool(tool: Mat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTool(toolPose: Mat, toolName: String): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun solveFK(joints: DoubleArray): Mat {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun jointsConfig(joints: DoubleArray): DoubleArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun solveIK(pose: Mat, jointsApprox: DoubleArray?, tool: Mat?, reference: Mat?): DoubleArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun solveIK_All(pose: Mat, tool: Mat?, reference: Mat?): Mat {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun connect(robotIp: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveJ(itemtarget: Item, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveJ(joints: DoubleArray, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveJ(target: Mat, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveL(itemTarget: Item, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveL(joints: DoubleArray, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveL(target: Mat, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveC(itemTarget1: Item, itemTarget2: Item, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveC(joints1: DoubleArray, joints2: DoubleArray, blocking: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun moveC(target1: Mat, target2: Mat, blocking: Boolean) {
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

    override fun showSequence(sequence: Mat) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun stop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun waitMove(timeoutSec: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun instructionList(): Pair<Int, Mat> {
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
    ): Triple<Int, String, Mat> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun finish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}