package com.robodk.api

import com.robodk.api.exception.RdkException
import com.robodk.api.model.ItemType
import com.robodk.api.model.fromValue
import mu.KotlinLogging
import java.io.*
import java.net.ConnectException
import java.net.Socket
import java.net.SocketTimeoutException
import java.time.Duration
import kotlin.properties.Delegates

class SocketLink(
    var timeoutMillis: Int = 100,
    var safeMode: Boolean = true,
    var autoUpdate: Boolean = false,
    var echo: Boolean = false,
    var startHidden: Boolean = false,
    var serverIpAddress: String = "localhost",
    var serverStartPort: Int = 20500,
    var serverEndPort: Int = 20500,
    var socketTimeout: Duration = Duration.ofSeconds(10)
) : Link {

    override var itemInterceptFunction: (Item) -> Item = { it }

    override val lastStatusMessage get() = _lastStatusMessage

    override val connected get() = socket?.isConnected ?: false

    private var log = KotlinLogging.logger { }

    private var dataIn: DataInputStream? = null
    private var dataOut: DataOutputStream? = null
    private var outWriter: BufferedWriter? = null
    private var inReader: BufferedReader? = null

    private var socket: Socket? by Delegates.observable<Socket?>(null) { obs, oldVal, newVal ->
        if (echo && oldVal != newVal && newVal != null) {
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
                    log.warn("Failed to connect to RoboDk on IP: $serverIpAddress, Port: $port")
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
        log.warn("Could not connect to RoboDk. IP: $serverIpAddress, Port Start: $serverStartPort, Port End: $serverEndPort")
        return false
    }

    override fun disconnect() = socket?.close() ?: Unit

    /// <summary>
    ///     If we are not connected it will attempt a connection, if it fails, it will throw an error
    /// </summary>
    override fun checkConnection() {
        if (!connected && !connect()) {
            throw RdkException("Can't connect to RoboDK API")
        }
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
    override fun checkStatus() {
        val (success, status) = receiveInt()
        if (!success) {
            return
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
    }


    override fun sendInt(number: Int) {
        val sendData = number.toBytes()
        sendData(sendData)
        if (echo) {
            log.info("SendInt: $number")
        }
    }

    override fun sendLine(line: String) {
        outWriter!!.append(line).append('\n')
        outWriter!!.flush()
        if (echo) {
            log.info("SendLine: $line")
        }
    }

    private fun sendData(data: ByteArray) {
        try {
            with(dataOut!!) {
                write(data)
                flush()
            }
        } catch (ex: Exception) {
            throw RdkException("Failed to send data: $data -> $ex")
        }
    }

    override fun sendItem(item: Item) {
        val idBytes = item.itemId.toBytes()
        sendData(idBytes)
        if (echo) {
            log.info("SendItem: $item")
        }
    }

    override fun sendArray(array: DoubleArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun receiveInt(): Pair<Boolean, Int> {
        return try {
            val buffer = ByteArray(4)
            val read = dataIn!!.read(buffer)
            val success = read == buffer.size
            val value = buffer.toInt()
            if (echo) {
                log.info("ReceiveInt: ${if (success) "Success: $value" else "Fail"}")
            }
            Pair(success, value)
        } catch (ex: SocketTimeoutException) {
            Pair(false, 0)
        }
    }

    override fun receiveLong(): Pair<Boolean, Long> {
        return try {
            val buffer = ByteArray(8)
            val read = dataIn!!.read(buffer)
            val success = read == buffer.size
            val value = buffer.toLong()
            if (echo) {
                log.info("ReceiveLong: ${if (success) "Success: $value" else "Fail"}")
            }
            Pair(success, value)
        } catch (ex: SocketTimeoutException) {
            Pair(false, 0)
        }
    }

    override fun receiveLine(): Pair<Boolean, String> {
        return try {
            val line = inReader!!.readLine()
            if (echo) {
                log.info("ReceiveLine: $line")
            }
            Pair(false, line)
        } catch (ex: SocketTimeoutException) {
            Pair(false, "")
        }
    }

    // Receives an item pointer
    override fun receiveItem(): Item? {
        val (itemIdRead, itemId) = receiveLong()
        val (itemTypeRead, typeNum) = receiveInt()
        if (!itemIdRead || !itemTypeRead) {
            return null
        }

        val type = ItemType.ANY.fromValue(typeNum)
        val item = ItemLink(this, itemId, type)
        log.info("Received item: $item")
        return itemInterceptFunction(item)
    }

    override fun receiveArray(): Pair<Boolean, DoubleArray> {
        val (countSuccess, count) = receiveInt()

        if(!countSuccess) {
            return Pair(false, doubleArrayOf())
        }

        val arr = DoubleArray(count) { 0.0 }

        for(i in 0 until count) {
            val (doubleSuccess, double) = receiveDouble()
            if(!doubleSuccess) {
                return Pair(false, doubleArrayOf())
            }
            arr[i] = double
        }

        return Pair(true, arr)
    }

    private fun receiveDouble(): Pair<Boolean, Double> {
        return try {
            val buffer = ByteArray(8)
            val read = dataIn!!.read(buffer)
            val success = read == buffer.size
            val value = buffer.toLong()
            if (echo) {
                log.info("ReceiveLong: ${if (success) "Success: $value" else "Fail"}")
            }
            Pair(success, 0.0)
        } catch (ex: SocketTimeoutException) {
            Pair(false, 0.0)
        }
    }
}