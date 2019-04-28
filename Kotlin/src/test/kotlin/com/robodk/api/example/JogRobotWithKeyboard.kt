package com.robodk.api.example

import com.robodk.api.*
import com.robodk.api.model.ItemType
import org.apache.commons.math3.linear.RealMatrix
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

const val moveSpeed = 10.0

fun main() {
    val log = Logger.getAnonymousLogger()

    val robot = RoboDkLink(SocketLink().also { it.logLevel = Level.OFF }).let {
        it.connect()
        it.getItemByName("", ItemType.ROBOT)!!
    }

    if (!robot.valid) {
        log.info("No robot in the station, load this program, then run the logger")
        return
    }

    println("Using Robot: ${robot.name}")

    val scanner = Scanner(System.`in`)

    printCommands()

    runProgram(robot, scanner)
}

private fun printCommands() {
    println(
        with(StringBuilder()) {
            appendln("Commands:")
            appendln("\t D: Increment Y")
            appendln("\t A: Decrement Y")
            appendln("\t S: Increment X")
            appendln("\t W: Decrement X")
            appendln("\t Q: Increment Z")
            appendln("\t E: Decrement Z")
            appendln("\t H: Print these commands again")
            toString()
        }
    )
}

private fun runProgram(robot: Item, scanner: Scanner) {
    while (true) {
        if (!scanner.hasNext()) {
            return
        }

        val next = scanner.next()
        val direction = when (next) {
            "D" -> matrixOf(3, 1, 0.0, 1.0, 0.0)
            "A" -> matrixOf(3, 1, 0.0, -1.0, 0.0)
            "S" -> matrixOf(3, 1, 1.0, 0.0, 0.0)
            "W" -> matrixOf(3, 1, -1.0, 0.0, 0.0)
            "Q" -> matrixOf(3, 1, 0.0, 0.0, 1.0)
            "E" -> matrixOf(3, 1, 0.0, 0.0, -1.0)
            "H" -> {
                printCommands()
                null
            }
            else -> null
        } ?: continue

        val move = direction.scalarMultiply(moveSpeed)
        robot.translateRobot(move)
    }
}

private fun Item.translateRobot(move: RealMatrix, blocking: Boolean = true): Boolean {
    val log = Logger.getAnonymousLogger()
    val robotJoints = joints
    val robotPos = solveFK(robotJoints)
    val robotConfig = jointsConfig(robotJoints)
    val newRobotPos = move.toTranslationMatrix().multiply(robotPos)
    val newRobotJoints = solveIK(newRobotPos)
    if (newRobotJoints.size < 6) {
        log.warning("No Robot Solution! The new position is too far, out of reach, or too close to a singularity")
        return false
    }

    val newRobotConfig = jointsConfig(newRobotJoints)
    if (!robotConfig.contentEquals(newRobotConfig)) {
        log.warning(with(StringBuilder()) {
            appendln("Robot configuration changed. This will lead to unexpected movements")
            appendln("Original Config: ${robotConfig.contentToString()}")
            appendln("New Config: ${newRobotConfig.contentToString()}")
            toString()
        })
    }
    moveJ(newRobotJoints, blocking)
    return true
}
