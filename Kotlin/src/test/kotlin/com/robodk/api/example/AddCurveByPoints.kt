package com.robodk.api.example

import com.robodk.api.RoboDkLink
import com.robodk.api.matrixOf
import com.robodk.api.model.ItemType
import org.apache.commons.math3.linear.RealMatrix
import kotlin.math.abs
import kotlin.math.sqrt

const val yLevel = 200.0
val projDir = doubleArrayOf(0.0, 1.0, 0.0)

// Sample Input Values: 475 -450 450 50 5

fun main() {
    val rdk = RoboDkLink()
    val obj = rdk.itemUserPick(
        "Select object to project the path<br>(select cancel if you do not want to project the path)",
        ItemType.OBJECT
    )
    println("Enter the path parameters. Example:\\n[RADIUS  xSTART  xEND  xSTEP  ACCURACY]")
    val readString = readLine()?.split(' ')
    val (radius, xStart, xEnd, xStep, accuracy) = if (readString?.size != 5) {
        println("5 Numbers were not entered, using default parameters")
        listOf(475.0, -450.0, 450.0, 50.0, 5.0)
    } else {
        readString.map(String::toDouble)
    }

    val curvePoints = circlePoints(radius, xStart, xEnd, xStep, accuracy, yLevel, projDir)

    val curve = rdk.addCurve(curvePoints, obj)
    val name = curve.name
    curve.name = "Curve: $radius;$xStart;$xStep;$accuracy"
}

private fun circlePoints(
    radius: Double,
    xStart: Double,
    xEnd: Double,
    xStep: Double,
    accuracy: Double,
    ycoord: Double,
    projection: DoubleArray
): RealMatrix {
    val (nx, ny, nz) = projection

    check(abs(xStart) < radius) { "Start point is outside the circle" }
    check(abs(xEnd) < radius) { "End point is outside the circle" }

    var zSense = 1.0
    var x = xStart
    val y = ycoord
    val xSteps = ((xEnd - xStart) / xStep).toInt()

    val points = mutableListOf<DoubleArray>()

    (0..xSteps).forEach {
        val zAbs = sqrt(radius * radius - x * x)
        val zSteps = (2 * zAbs / accuracy).toInt()
        val zStep = -zSense * 2 * zAbs / zSteps
        var z = zSense * zAbs
        (0..zSteps).forEach {
            val point = doubleArrayOf(x, y, z, nx, ny, nz)
            points.add(point)
            z += zStep
        }
        zSense *= -1.0
        x += xStep
    }
    return matrixOf(points)
}
