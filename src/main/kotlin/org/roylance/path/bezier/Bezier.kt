package org.roylance.path.bezier

import org.roylance.path.models.Vector



class Bezier(sx: Float, sy: Float, coords: FloatArray, numCoords: Int) {
    var length: Float = 0.toFloat()
        internal set
    internal var coord: FloatArray = kotlin.FloatArray(0)

    init {
        setCoords(sx, sy, coords, numCoords)
    }

    fun setCoords(sx: Float, sy: Float, coords: FloatArray, numCoords: Int) {
        coord = FloatArray(numCoords * 2 + 2)
        coord[0] = sx
        coord[1] = sy
        for (i in 0..numCoords - 1) {
            coord[i * 2 + 2] = coords[i * 2]
            coord[i * 2 + 3] = coords[i * 2 + 1]
        }

        calcLength()
    }

    private fun calcLength() {
        length = 0f
        var i = 2
        while (i < coord.size) {
            length += lineLength(coord[i - 2], coord[i - 1], coord[i], coord[i + 1])
            i += 2
        }
    }

    private fun lineLength(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val dx = x2 - x1
        val dy = y2 - y1
        return Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

    fun eval(param: Double, point: Vector): Vector {
        point.x = 0f
        point.y = 0f
        val numKnots = coord.size / 2

        for (i in 0..numKnots - 1) {
            val scale = bernstein(numKnots - 1, i, param)
            point.x = (point.x + coord[i * 2] * scale).toFloat()
            point.y = (point.y + coord[i * 2 + 1] * scale).toFloat()
        }

        return point
    }

    private fun bernstein(numKnots: Int, knotNo: Int, param: Double): Double {
        val iParam = 1 - param

        when (numKnots) {
            0 -> return 1.0
            1 -> {
                when (knotNo) {
                    0 -> return iParam
                    1 -> return param
                }
            }
            2 -> {
                when (knotNo) {
                    0 -> return iParam * iParam
                    1 -> return 2.0 * iParam * param
                    2 -> return param * param
                }
            }
            3 -> {
                when (knotNo) {
                    0 -> return iParam * iParam * iParam
                    1 -> return 3.0 * iParam * iParam * param
                    2 -> return 3.0 * iParam * param * param
                    3 -> return param * param * param
                }
            }
        }

        var retVal = 1.0
        for (i in 0..knotNo - 1) {
            retVal *= param
        }
        for (i in 0..numKnots - knotNo - 1) {
            retVal *= iParam
        }
        retVal *= choose(numKnots, knotNo).toDouble()

        return retVal
    }


    private fun choose(num: Int, denomOriginal: Int): Int {
        var denom = denomOriginal
        var denom2 = num - denom
        if (denom < denom2) {
            val tmp = denom
            denom = denom2
            denom2 = tmp
        }

        var prod = 1
        for (i in num downTo denom + 1) {
            prod *= num
        }

        for (i in 2..denom2) {
            prod /= i
        }

        return prod
    }
}