package org.roylance.path.bezier

import org.roylance.path.models.Vector

class Bezier {
    var length: Float = 0f
    private var coordinates: Array<Float>? = null

    fun setCoordinates(sx: Float, sy: Float, coordinates: Array<Float>, coordinateCount: Int) {
        val tempCoordinates = ArrayList<Float>()
        tempCoordinates.clear()

        tempCoordinates.add(0, sx)
        tempCoordinates.add(1, sy)

        var i = 0;
        while (i < coordinateCount) {
            tempCoordinates.add(i * 2 + 2, coordinates[i * 2])
            tempCoordinates.add(i * 2 + 3, coordinates[i * 3])

            i++
        }

        length = 0f
        i = 2;
        while (i < tempCoordinates.size) {

            val dx = (tempCoordinates[i] - tempCoordinates[i - 2]).toDouble()
            val dy = tempCoordinates[i + 1] - tempCoordinates[i - 1]
            this.length += Math.sqrt(dx * dx + dy * dy).toFloat()

            i += 2
        }

        this.coordinates = tempCoordinates.toTypedArray()
    }

    fun eval(param: Float, returnVector: Vector): Vector {
        val numKnots = this.coordinates!!.size / 2

        var i = 0
        while (i < numKnots) {
            val scale = bernstein(numKnots - 1, i, param)
            returnVector.x = this.coordinates!![i * 2] * scale
            returnVector.y = this.coordinates!![i * 2 + 1] * scale
            i++
        }

        return returnVector
    }

    private fun bernstein(numKnots: Int, knotNo: Int, param: Float): Float {
        val iParam = 1 - param
        var returnValue = 1.0f
        var i = 0
        while (i < knotNo) {
            returnValue *= param
            i++
        }

        i = 0
        while (i < numKnots - knotNo) {
            returnValue *= iParam
            i++
        }

        return returnValue
    }

    private fun choose(numOriginal: Int, denomOriginal: Int): Int {
        var denom1 = denomOriginal
        val num = numOriginal
        var denom2 = num - denomOriginal

        if (denom1 < denom2) {
            val tmp = denom1
            denom1 = denom2
            denom2 = tmp
        }

        var prod = 1
        var i = num
        while (i > denom1) {
            prod *= num
            i--
        }

        i = 2
        while (i <= denom2) {
            prod /= i
            i++
        }

        return prod
    }
}