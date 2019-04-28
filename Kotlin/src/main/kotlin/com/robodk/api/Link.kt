package com.robodk.api

import org.apache.commons.math3.linear.RealMatrix
import java.time.Duration
import java.util.logging.Logger

/**
 * An object that can connect to RoboDk, send and receive information.
 */
interface Link {

    /**
     * Allows to install a function which can intercept all interface methods.
     * Used for Aspect oriented programming (e.g. Add logging to an Interface).
     */
    var itemInterceptFunction: (Item) -> Item

    /** Holds any warnings for the last call */
    val lastStatusMessage: String

    /** True if this link is conneced, else false. */
    val connected: Boolean

    /** The timeout to use when receiving values. */
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

    /** Check the status of the connection. */
    fun checkConnection(): Link

    /** Verify a connection. */
    fun verifyConnection(): Boolean

    /** Check RoboDk status. */
    fun checkStatus(): Link

    //<editor-fold "Transport">

    // <editor-fold "Send">

    /** Sends the supplied integer to RoboDk. */
    fun sendInt(number: Int): Link

    /** Sends the supplied string line to RoboDk. */
    fun sendLine(line: String): Link

    /** Sends the supplied item to RoboDk. */
    fun sendItem(item: Item?): Link

    /** Sends the supplied double array to RoboDk. */
    fun sendArray(array: DoubleArray): Link

    /** Sends the supplied pose to RoboDk. */
    fun sendPose(pose: RealMatrix): Link

    /** Sends the supplied matrix to RoboDk. */
    fun sendMatrix(matrix: RealMatrix): Link

    //</editor-fold>

    // <editor-fold "Receive">

    /**
     * Receive an integer value.
     * @return Pair of the operation's success and the value. The value will not be valid with failed receive.
     */
    fun receiveInt(): Pair<Boolean, Int>

    /**
     * Receive a long value.
     * @return Pair of the operation's success and the value. The value will not be valid with failed receive.
     */
    fun receiveLong(): Pair<Boolean, Long>

    /**
     * Receive a string line.
     * @return Pair of the operation's success and the value. The value will not be valid with failed receive.
     */
    fun receiveLine(): Pair<Boolean, String>

    /**
     * Receive an item.
     * @return Pair of the operation's success and the value. The value will not be valid with failed receive.
     */
    fun receiveItem(): Pair<Boolean, Item?>

    /**
     * Receive a double array.
     * @return Pair of the operation's success and the value. The value will not be valid with failed receive.
     */
    fun receiveArray(): Pair<Boolean, DoubleArray>

    /**
     * Receive a pose.
     * @return Pair of the operation's success and the value. The value will not be valid with failed receive.
     */
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
