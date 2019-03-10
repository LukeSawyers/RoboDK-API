package com.robodk.api

import org.apache.commons.math3.linear.RealMatrix
import java.time.Duration
import java.util.logging.Logger

interface Link {

    /**
     * Allows to install a function which can intercept all interface methods.
     * Used for Aspect oriented programming (e.g. Add logging to an Interface).
     */
    var itemInterceptFunction: (Item) -> Item

    /** Holds any warnings for the last call */
    val lastStatusMessage: String

    val connected: Boolean

    var receiveTimeout: Int

    /**
     * Establish a connection with RoboDK.
     * If RoboDK is not running it will attempt to start RoboDK from the default installation path.
     * (otherwise APPLICATION_DIR must be set properly).
     * @returns If the connection succeeds it returns True, otherwise it returns False.
     */
    fun connect(): Boolean

    /**
     * Stops the communication with RoboDK.
     * If setRunMode is set to MakeRobotProgram for offline programming,
     * any programs pending will be generated.
     */
    fun disconnect(): Link

    fun checkConnection(): Link

    fun verifyConnection(): Boolean

    fun checkStatus(): Link

    //<editor-fold "Transport">

    // <editor-fold "Send">

    fun sendInt(number: Int): Link

    fun sendLine(line: String): Link

    fun sendItem(item: Item?): Link

    fun sendArray(array: DoubleArray): Link

    fun sendPose(pose: RealMatrix): Link

    fun sendMatrix(matrix: RealMatrix): Link

    //</editor-fold>

    // <editor-fold "Receive">

    fun receiveInt(): Pair<Boolean, Int>

    fun receiveLong(): Pair<Boolean, Long>

    fun receiveLine(): Pair<Boolean, String>

    fun receiveItem(): Pair<Boolean, Item?>

    fun receiveArray(): Pair<Boolean, DoubleArray>

    fun receivePose(): Pair<Boolean, RealMatrix>

    //</editor-fold>

    //</editor-fold>

    fun moveX(
        target: Item,
        itemRobot: Item,
        moveType: Int,
        blocking: Boolean = true
    ): Link

    fun moveX(
        joints: DoubleArray,
        itemRobot: Item,
        moveType: Int,
        blocking: Boolean = true
    ): Link

    fun moveX(
        matTarget: RealMatrix,
        itemRobot: Item,
        moveType: Int,
        blocking: Boolean = true
    ): Link
}

fun <T> Link.session(
    message: String? = null,
    log: Logger = Logger.getLogger(javaClass.name),
    block: (Link) -> T
): T {
    if (message != null) {
        log.info("\n\n### ItemLink: $message ###\n")
    }

    checkConnection()
    val result = block(this)
    checkStatus()
    return result
}

fun <T> Link.withTimeout(timeout: Duration, block: Link.() -> T) : T {
    val currentTimeout = receiveTimeout
    receiveTimeout = timeout.toMillis().toInt()
    val result = block()
    receiveTimeout = currentTimeout
    return result
}