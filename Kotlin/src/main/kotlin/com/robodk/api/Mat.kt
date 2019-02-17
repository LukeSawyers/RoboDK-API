package com.robodk.api

import com.robodk.api.exception.MatException

data class Mat(val rows: Int, val cols: Int) {

    companion object {
        /// <summary>
        /// Matrix class constructor for a double array. The array will be set as a column matrix.
        /// Example:
        ///     RDK.AddCurve(new Mat(new double[6] {{0,0,0, 0,0,1}}));
        /// </summary>
        /// <param name="point">Column array</param>
        /// <param name="isPose">if isPose is True then convert vector into a 4x4 Pose Matrix.</param>
        fun of(point: DoubleArray, isPose: Boolean): Mat {
            return if (isPose) {

                if (point.size < 16) {
                    throw MatException("Invalid array size to create a pose Mat") //raise Exception('Problems running function');
                }

                val mat = Mat(4, 4)

                // Convert a double array of arrays to a Mat object:
                for (r in 0 until mat.rows) {
                    for (c in 0 until mat.cols) {
                        mat[r][c] = point[r + c * 4]
                    }
                }
                mat
            } else {
                // Convert a double array of arrays to a Mat object:
                val mat = Mat(point.size, 1)
                for (r in 0 until mat.rows) {
                    mat[r][0] = point[r]
                }
                mat
            }
        }
    }

    /// <summary>
    ///     Matrix class constructor for a 4x4 homogeneous matrix
    /// </summary>
    /// <param name="nx">Position [0,0]</param>
    /// <param name="ox">Position [0,1]</param>
    /// <param name="ax">Position [0,2]</param>
    /// <param name="tx">Position [0,3]</param>
    /// <param name="ny">Position [1,0]</param>
    /// <param name="oy">Position [1,1]</param>
    /// <param name="ay">Position [1,2]</param>
    /// <param name="ty">Position [1,3]</param>
    /// <param name="nz">Position [2,0]</param>
    /// <param name="oz">Position [2,1]</param>
    /// <param name="az">Position [2,2]</param>
    /// <param name="tz">Position [2,3]</param>
    constructor(
        nx: Double,
        ox: Double,
        ax: Double,
        tx: Double,
        ny: Double,
        oy: Double,
        ay: Double,
        ty: Double,
        nz: Double,
        oz: Double,
        az: Double,
        tz: Double
    ) : this(4, 4) {
        mat[0][0] = nx
        mat[1][0] = ny
        mat[2][0] = nz
        mat[0][1] = ox
        mat[1][1] = oy
        mat[2][1] = oz
        mat[0][2] = ax
        mat[1][2] = ay
        mat[2][2] = az
        mat[0][3] = tx
        mat[1][3] = ty
        mat[2][3] = tz
        mat[3][0] = 0.0
        mat[3][1] = 0.0
        mat[3][2] = 0.0
        mat[3][3] = 1.0
    }

    /// <summary>
    ///     Matrix class constructor for a 4x4 homogeneous matrix as a copy from another matrix
    /// </summary>
    constructor(pose: Mat) : this(pose.rows, pose.cols) {
        for (r in 0 until pose.rows) {
            for (c in 0 until pose.cols) {
                mat[r][c] = pose[r][c]
            }
        }
    }

    /// <summary>
    ///     Matrix class constructor for a 4x1 vector [x,y,z,1]
    /// </summary>
    /// <param name="x">x coordinate</param>
    /// <param name="y">y coordinate</param>
    /// <param name="z">z coordinate</param>
    constructor(x: Double, y: Double, z: Double) : this(4,1) {
        mat[0][0] = x
        mat[1][0] = y
        mat[2][0] = z
        mat[3][0] = 1.0
    }

    private operator fun set(row: Int, value: DoubleArray) {
        mat[row] = value
    }

    private operator fun get(row: Int): DoubleArray = mat[row]

    private val mat: Array<DoubleArray> = Array(rows) { DoubleArray(cols) { 0.0 } }

}