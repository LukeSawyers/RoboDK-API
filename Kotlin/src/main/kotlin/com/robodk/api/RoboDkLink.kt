package com.robodk.api

import com.robodk.api.collision.CollisionItem
import com.robodk.api.collision.CollisionPair
import com.robodk.api.events.EventType
import com.robodk.api.events.IRoboDkEventSource
import com.robodk.api.model.*
import org.apache.commons.math3.linear.RealMatrix
import java.awt.Color
import java.io.Closeable
import java.time.Duration
import java.util.*
import java.util.logging.Logger

class RoboDkLink(
    val link: Link,
    var logging: Boolean = false,
    override var applicationDir: String = "",
    var safeMode: Boolean = true,
    var autoUpdate: Boolean = false,
    var startHidden: Boolean = false,
    var serverIpAddress: String = "localhost",
    var serverStartPort: Int = 20500,
    var serverEndPort: Int = 20500,
    var socketTimeout: Duration = Duration.ofSeconds(10),
    var roboDkBuild: Int = 0
) : RoboDk, Link by link, Closeable {

    private var log = Logger.getLogger(this::class.java.name)

    // <editor-fold desc="Closeable Implementation">

    private var closed = false

    override fun close() {
        if (!closed) {
            disconnect()
            closed = true
        }
    }

    // </editor-fold>

    // <editor-fold desc="RoboDk Implementation">

    override var lastStatusMessage: String = ""
        private set

    override val collisions: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val collisionItems: List<CollisionItem>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val collisionPairs: List<CollisionPair>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val version: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val openStations: List<Item>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val parameterList: Map<String, String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val stereoCameraMeasure: StereoCameraMeasure
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val license: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val selectedItems: List<Item>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override var activeStation: Item
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var simulationSpeed: Double
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}
    override var runMode: RunMode
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun newLink(): RoboDk {
        return RoboDkLink(
            link,
            serverStartPort = this.serverStartPort,
            serverEndPort = this.serverEndPort
        ).also { it.connect() }
    }

    override fun eventsListen(): IRoboDkEventSource {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun eventsListenClose() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sampleRoboDkEvent(evt: EventType, item: Item): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun eventsLoop(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeRoboDK() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setWindowState(windowState: WindowState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFile(filename: String, parent: Item?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTarget(name: String, parent: Item?, robot: Item?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addProgram(name: String, robot: Item?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addStation(name: String): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addMachiningProject(name: String, itemRobot: Item?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render(alwaysRender: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemByName(name: String, itemType: ItemType): Item? {
        checkConnection()
        if (itemType == ItemType.ANY) {
            sendLine(RdkApi.Commands.Get.ITEM_ANY)
            sendLine(name)
        } else {
            sendLine(RdkApi.Commands.Get.ITEM)
            sendLine(name)
            sendInt(itemType.value)
        }

        val (_, item) = receiveItem()
        checkStatus()
        return item
    }

    override fun getItemListNames(itemType: ItemType): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemList(itemType: ItemType): List<Item> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun itemUserPick(message: String, itemType: ItemType): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRoboDK() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fitAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideRoboDK() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setWindowFlags(flags: EnumSet<WindowFlags>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String, popup: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun save(filename: String, itemsave: Item?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addShape(trianglePoints: RealMatrix, addTo: Item?, shapeOverride: Boolean, color: Color?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addCurve(
        curvePoints: RealMatrix,
        referenceObject: Item?,
        addToRef: Boolean,
        projectionType: ProjectionType
    ): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addPoints(
        points: RealMatrix,
        referenceObject: Item?,
        addToRef: Boolean,
        projectionType: ProjectionType
    ): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun projectPoints(
        points: RealMatrix,
        objectProject: Item,
        projectionType: ProjectionType
    ): RealMatrix {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun closeStation() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addFrame(name: String, parent: Item?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runProgram(function: String): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runCode(code: String, codeIsFunctionCall: Boolean): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun runMessage(message: String, messageIsComment: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isInside(objectInside: Item, objectParent: Item): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCollisionActive(collisionCheck: CollisionCheckOptions): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun enableCollisionCheckingForAllItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disableCollisionCheckingForAllItems() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCollisionActivePair(collisionCheck: CollisionCheckOptions, collisionPair: CollisionPair): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCollisionActivePair(
        checkState: List<CollisionCheckOptions>,
        collisionPairs: List<CollisionPair>
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun collision(item1: Item, item2: Item, useCollisionMap: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getParameter(parameter: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setParameter(parameter: String, value: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setParameter(parameter: String, value: Double) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun command(cmd: String, value: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun command(cmd: String, value: Boolean): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun command(cmd: String, value: Double): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun laserTrackerMeasure(estimate: DoubleArray, search: Boolean): DoubleArray {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun collisionLine(p1: DoubleArray, p2: DoubleArray): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setVisible(itemList: List<Item>, visibleList: List<Boolean>, visibleFrames: List<Int>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setColor(item_list: List<Item>, color_list: List<Color>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showAsCollided(item_list: List<Item>, collided_list: List<Boolean>, robot_link_id: List<Int>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun joints(robotItemList: List<Item>): List<DoubleArray> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setJoints(robotItemList: List<Item>, jointsList: List<DoubleArray>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun calibrateTool(
        posesJoints: RealMatrix,
        format: EulerType,
        algorithm: TcpCalibrationType,
        robot: Item?
    ): Pair<DoubleArray, DoubleArray> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun calibrateReference(
        joints: RealMatrix,
        method: ReferenceCalibrationType,
        useJoints: Boolean,
        robot: Item?
    ): RealMatrix {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun programStart(progName: String, defaultFolder: String, postProcessor: String, robot: Item?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setViewPose(pose: RealMatrix) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getViewPose(preset: ViewPoseType): RealMatrix {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRobotParams(
        robot: Item,
        dhm: Array<DoubleArray>,
        poseBase: RealMatrix,
        poseTool: RealMatrix
    ): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun buildMechanism(
        type: Int,
        listObj: List<Item>,
        param: List<Double>,
        jointsBuild: List<Double>,
        jointsHome: List<Double>,
        jointsSenses: List<Double>,
        jointsLimLow: List<Double>,
        jointsLimHigh: List<Double>,
        baseFrame: RealMatrix?,
        tool: RealMatrix?,
        name: String,
        robot: Item?
    ): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cam2DAdd(item: Item, cameraParameters: String): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cam2DSnapshot(fileSaveImg: String, camHandle: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cam2DClose(camHandle: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cam2DSetParameters(cameraParameters: String, camHandle: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun popup_ISO9283_CubeProgram(robot: Item?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setInteractiveMode(
        modeType: InteractiveType,
        defaultRefFlags: EnumSet<DisplayRefType>,
        customItems: List<Item>?,
        customRefFlags: EnumSet<DisplayRefType>?
    ) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCursorXYZ(xCoord: Int, yCoord: Int, xyzStation: List<Double>?): Item {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTargetJ(pgm: Item, targetName: String, joints: DoubleArray, robotBase: Item?, robot: Item?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // </editor-fold>
}