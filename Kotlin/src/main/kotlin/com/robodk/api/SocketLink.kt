package com.robodk.api

import com.robodk.api.exception.RdkException
import com.robodk.api.model.ItemType
import com.robodk.api.model.fromValue
import javafx.css.Size
import org.apache.commons.math3.linear.RealMatrix
import java.io.*
import java.net.ConnectException
import java.net.Socket
import java.net.SocketTimeoutException
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.properties.Delegates

class SocketLink(
    var safeMode: Boolean = true,
    var autoUpdate: Boolean = false,
    var startHidden: Boolean = false,
    var serverIpAddress: String = "localhost",
    var serverStartPort: Int = 20500,
    var serverEndPort: Int = 20500,
    private var timeoutMillis: Int = 100
) : Link {

    private object SizeOf{
        const val INT = 4
        const val LONG = 8
        const val DOUBLE = 8
    }

    override var receiveTimeout: Int
        get() = socket?.soTimeout ?: timeoutMillis
        set(value) {
            socket?.soTimeout = value
            if (socket != null) {
                socket!!.soTimeout = value
            }
            timeoutMillis = value
        }

    override var itemInterceptFunction: (Item) -> Item = { it }

    override val lastStatusMessage get() = _lastStatusMessage

    override val connected get() = socket?.isConnected ?: false

    var logLevel
        get() = log.level
        set(value) {
            log.level = value
        }

    private var log = Logger.getLogger(this::class.java.name).also { it.level = Level.INFO }

    private var dataIn: DataInputStream? = null
    private var dataOut: DataOutputStream? = null
    private var outWriter: BufferedWriter? = null
    private var inReader: BufferedReader? = null

    private var socket: Socket? by Delegates.observable<Socket?>(null) { obs, oldVal, newVal ->
        if (oldVal != newVal && newVal != null) {
            newVal.soTimeout = timeoutMillis
            dataIn = DataInputStream(newVal.getInputStream())
            dataOut = DataOutputStream(newVal.getOutputStream())
            outWriter = BufferedWriter(OutputStreamWriter(newVal.getOutputStream()))
            inReader = BufferedReader(InputStreamReader(newVal.getInputStream()))
        }
    }

    private var _lastStatusMessage: String = ""

    override fun connect(): Boolean {
        if (connected) {
            return true
        }

        for (i in 0 until 2) {
            if (serverEndPort < serverStartPort) {
                serverEndPort = serverStartPort
            }
            for (port in serverStartPort..serverEndPort) {
                val socket = try {
                    Socket(serverIpAddress, port)
                } catch (ex: ConnectException) {
                    log.warning("Failed to connect to RoboDk on IP: $serverIpAddress, Port: $port")
                    continue
                }

                val connected = socket.isConnected
                if (connected) {
                    log.info("Connected to RoboDk on IP: $serverIpAddress, Port: $port")
                    this.socket = socket
                    return if (verifyConnection()) {
                        true
                    } else {
                        this.socket = null
                        false
                    }
                } else {
                    socket.close()
                }
            }
        }
        log.warning("Could not connect to RoboDk. IP: $serverIpAddress, Port Start: $serverStartPort, Port End: $serverEndPort")
        return false
    }

    override fun disconnect(): Link {
        socket?.close()
        return this
    }

    /// <summary>
    ///     If we are not connected it will attempt a connection, if it fails, it will throw an error
    /// </summary>
    override fun checkConnection(): Link {
        if (!connected && !connect()) {
            throw RdkException("Can't connect to RoboDK API")
        }
        return this
    }

    override fun verifyConnection(): Boolean {
        sendLine(RdkApi.Commands.START)
        val startParameter = "${if (safeMode) "1" else "0"} ${if (autoUpdate) "1" else "0"} "
        sendLine(startParameter)
        val (success, response) = receiveLine()
        return response == RdkApi.Responses.READY
    }

    /**
     * Checks the status of the connection.
     */
    override fun checkStatus(): Link {
        val (success, status) = receiveInt()
        if (!success) {
            return this
        }
        _lastStatusMessage = ""
        when (status) {
            // No status
            0 -> _lastStatusMessage = ""

            // Invalid item
            1 -> {
                _lastStatusMessage =
                    "Invalid item provided: The item identifier provided is not valid or it does not exist."
                throw RdkException(lastStatusMessage)
            }

            // Warning
            2 -> _lastStatusMessage = receiveLine().second

            // Error
            3 -> {
                _lastStatusMessage = receiveLine().second
                throw RdkException(lastStatusMessage)
            }

            // Invalid Licence
            9 -> {
                _lastStatusMessage = receiveLine().second
                throw RdkException(lastStatusMessage)
            }

            // Problems running function
            else -> {
                _lastStatusMessage = "Unknown problem running RoboDK API function"
                throw RdkException(lastStatusMessage)
            }
        }
        return this
    }

    //<editor-fold "Transport">

    // <editor-fold "Send">

    override fun sendInt(number: Int): Link {
        val sendData = number.toBytes()
        sendData(sendData)
        log.info("SendInt: $number")
        return this
    }

    override fun sendLine(line: String): Link {
        outWriter!!.append(line).append('\n')
        outWriter!!.flush()
        log.info("SendLine: $line")
        return this
    }

    private fun sendData(data: ByteArray): Link {
        try {
            with(dataOut!!) {
                write(data)
                flush()
            }
        } catch (ex: Exception) {
            throw RdkException("Failed to send data: $data -> $ex")
        }
        return this
    }

    override fun sendItem(item: Item?): Link {
        val idBytes = (item?.itemId ?: 0).toBytes()
        sendData(idBytes)
        log.info("SendItem: $item")
        return this
    }

    override fun sendArray(array: DoubleArray): Link {
        val size = array.size
        sendInt(size)
        sendData(ByteArray(8 * size).also {
            for (i in 0 until size) {
                System.arraycopy(array[i].toBytes(), 0, it, i * 8, 8)
            }
        })
        log.info("SendArray: Size: ${array.size} - ${array.contentToString()}")
        return this
    }

    override fun sendPose(pose: RealMatrix): Link {
        if (!pose.isHomogenous) {
            throw RdkException("Matrix not Homogenous $pose")
        }
        // suppress the echo from sending the array
        val logLevel = log.level
        this.logLevel = Level.OFF
        val bytes = pose.toDoubleArray().flatMap { it.toBytes().asIterable() }.toByteArray()
        sendData(bytes)
        this.logLevel = logLevel
        log.info("Send Pose: $pose")
        return this
    }

    override fun sendMatrix(matrix: RealMatrix): Link {
        val rows = matrix.rowDimension
        val cols = matrix.columnDimension
        sendInt(rows)
        sendInt(cols)
        val bytes = matrix.toDoubleArray().flatMap { it.toBytes().asIterable() }.toByteArray()
        sendData(bytes)
        log.info("Send Matrix: $matrix")
        return this
    }

    //</editor-fold>

    // <editor-fold "Receive">

    override fun receiveInt(): Pair<Boolean, Int> {
        return try {
            val (success, buffer) = receiveData(SizeOf.INT)
            val value = buffer.toInt()
            log.info("ReceiveInt: ${if (success) "Success: $value" else "Fail"}")
            Pair(success, value)
        } catch (ex: SocketTimeoutException) {
            Pair(false, 0)
        }
    }

    override fun receiveLong(): Pair<Boolean, Long> {
        return try {
            val (success, buffer) = receiveData(SizeOf.LONG)
            val value = buffer.toLong()
            log.info("ReceiveLong: ${if (success) "Success: $value" else "Fail"}")
            Pair(success, value)
        } catch (ex: SocketTimeoutException) {
            Pair(false, 0)
        }
    }

    override fun receiveLine(): Pair<Boolean, String> {
        return try {
            val line = inReader!!.readLine()
            log.info("ReceiveLine: $line")
            Pair(false, line)
        } catch (ex: SocketTimeoutException) {
            Pair(false, "")
        }
    }

    // Receives an item pointer
    override fun receiveItem(): Pair<Boolean, Item?> {
        val (itemIdRead, itemId) = receiveLong()
        val (itemTypeRead, typeNum) = receiveInt()
        if (!itemIdRead || !itemTypeRead) {
            return false to null
        }

        val type = ItemType.ANY.fromValue(typeNum)
        val item = ItemLink(this, itemId, type)
        log.info("Received item: $item")
        return true to itemInterceptFunction(item)
    }

    override fun receiveArray(): Pair<Boolean, DoubleArray> {
        val (countSuccess, count) = receiveInt()

        if (!countSuccess) {
            return Pair(false, doubleArrayOf())
        }

        val arr = DoubleArray(count) { 0.0 }

        for (i in 0 until count) {
            val (doubleSuccess, double) = receiveDouble()
            if (!doubleSuccess) {
                return Pair(false, doubleArrayOf())
            }
            arr[i] = double
        }

        return Pair(true, arr)
    }

    override fun receivePose(): Pair<Boolean, RealMatrix> {
        val pose = matrixOf(4, 4)
        return try {
            val (success, buffer) = receiveData(16 * SizeOf.DOUBLE)
            if (!success) {
                throw RdkException("Invalid pose sent")
            }
            var count = 0
            for (col in 0 until pose.columnDimension) {
                for (row in 0 until pose.rowDimension) {
                    pose.setEntry(row, col, buffer.copyOfRange(count, count + 8).toDouble())
                    count += 8
                }
            }
            true
        } catch (ex: SocketTimeoutException) {
            false
        } to pose
    }

    //</editor-fold>

    //</editor-fold>

    override fun moveX(target: Item, itemRobot: Item, moveType: Int, blocking: Boolean) =
        moveXWith(itemRobot, moveType, blocking) {
            sendInt(3)
            sendArray(doubleArrayOf())
            sendItem(target)
        }

    override fun moveX(joints: DoubleArray, itemRobot: Item, moveType: Int, blocking: Boolean) =
        moveXWith(itemRobot, moveType, blocking) {
            sendInt(1)
            sendArray(joints)
            sendItem(null)
        }


    override fun moveX(matTarget: RealMatrix, itemRobot: Item, moveType: Int, blocking: Boolean) =
        moveXWith(itemRobot, moveType, blocking) {
            if (!matTarget.isHomogenous) {
                throw RdkException("Invalid target type")
            }
            sendInt(2)
            sendArray(matTarget.toDoubleArray())
            sendItem(null)
        }

    private fun moveXWith(itemRobot: Item, moveType: Int, blocking: Boolean, block: () -> Unit): Link {
        itemRobot.waitMove()
        sendLine(RdkCommand.MOVE_X)
        sendInt(moveType)
        block()
        sendItem(itemRobot)
        checkStatus()
        if (blocking) {
            itemRobot.waitMove()
        }
        return this
    }
    private fun receiveDouble(): Pair<Boolean, Double> {
        return try {
            val (success, buffer) = receiveData(SizeOf.DOUBLE)
            val value = buffer.toDouble()
            log.info("ReceiveDouble: ${if (success) "Success: $value" else "Fail"}")
            Pair(success, value)
        } catch (ex: SocketTimeoutException) {
            Pair(false, 0.0)
        }
    }

    private fun receiveData(size: Int): Pair<Boolean, ByteArray> {
        val byteArray = ByteArray(size)
        return (dataIn!!.read(byteArray) == size) to byteArray
    }
}
