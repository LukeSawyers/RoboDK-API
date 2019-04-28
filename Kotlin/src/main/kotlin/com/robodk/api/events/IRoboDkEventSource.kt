package com.robodk.api.events

import java.time.Duration

/** A source of RoboDk Events */
interface IRoboDkEventSource {

    /** Returns true if this event source is connected. */
    val connected: Boolean

    /**
     * Wait for a new RoboDkLink event.
     * This function blocks until a new RoboDkLink event occurs, or the timeout is reached.
     *
     * @param timeout Timeout in milliseconds.
     */
    fun waitForEvent(timeout: Int = 1000): EventResult

    /**
     * Wait for a new RoboDkLink event.
     * This function blocks until a new RoboDkLink event occurs, or the timeout is reached.
     *
     * @param timeout Timeout duration.
     */
    fun waitForEvent(timeout: Duration = Duration.ofSeconds(1)) = waitForEvent(timeout.toMillis().toInt())
}
