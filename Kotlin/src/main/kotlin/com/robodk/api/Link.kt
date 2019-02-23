package com.robodk.api

interface Link {

    /**
     * Allows to install a function which can intercept all interface methods.
     * Used for Aspect oriented programming (e.g. Add logging to an Interface).
     */
    var itemInterceptFunction: (Item) -> Item

    /** Holds any warnings for the last call */
    val lastStatusMessage: String

    val connected: Boolean

    /**
     * Establish a connection with RoboDK.
     * If RoboDK is not running it will attempt to start RoboDK from the default installation path.
     * (otherwise APPLICATION_DIR must be set properly).
     * @returns If the connection succeeds it returns True, otherwise it returns False.
     */
    fun connect() : Boolean

    /**
     * Stops the communication with RoboDK.
     * If setRunMode is set to MakeRobotProgram for offline programming,
     * any programs pending will be generated.
     */
    fun disconnect()

    fun checkConnection()

    fun verifyConnection(): Boolean

    fun checkStatus()

    fun sendInt(number: Int)

    fun sendLine(line: String)

    fun sendItem(item: Item)

    fun sendArray(array: DoubleArray)

    fun receiveInt(): Pair<Boolean, Int>

    fun receiveLong(): Pair<Boolean, Long>

    fun receiveLine(): Pair<Boolean, String>

    fun receiveItem(): Item?

    fun receiveArray(): Pair<Boolean, DoubleArray>
}