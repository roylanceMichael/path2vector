package org.roylance.path.bezier

import org.roylance.path.IPathHandler
import org.roylance.path.models.Vector


class BezierListProducer: IPathHandler {
    private val segments = ArrayList<Bezier>()
    val coordinates = FloatArray(6).toTypedArray()
    val history = BezierHistory()

    var curveLength = 0f

    override fun startPath() {
        curveLength = 0f
        segments.clear()
    }

    override fun endPath() {
        history.setLast(history.startPoint.x, history.startPoint.y)
        history.setLastKnot(history.startPoint.x, history.startPoint.y)
    }

    override fun moveToRelative(x: Float, y: Float) {
        moveToAbsolute(history.lastPoint.x + x, history.lastPoint.y + y)
    }

    override fun moveToAbsolute(x: Float, y: Float) {
        history.setLast(x, y)
    }

    override fun closePath() {
    }

    override fun lineToRelative(x: Float, y: Float) {
        lineToAbsolute(history.lastPoint.x + x, history.lastPoint.y + y)
    }

    override fun lineToAbsolute(x: Float, y: Float) {
        coordinates[0] = x
        coordinates[1] = y
        coordinates[2] = 0f
        coordinates[3] = 0f
        coordinates[4] = 0f
        coordinates[5] = 0f

        val b = Bezier()
        b.setCoordinates(history.lastPoint.x, history.lastPoint.y, coordinates, 1)
        segments.add(b)
        curveLength += b.length

        history.setLast(x, y)
        history.setLastKnot(x, y)
    }

    override fun lineToHorizontalRelative(x: Float) {
        lineToAbsolute(x + history.lastPoint.x, history.lastPoint.y)
    }

    override fun lineToHorizontalAbsolute(x: Float) {
        lineToAbsolute(x, history.lastPoint.y)
    }

    override fun lineToVerticalRelative(y: Float) {
        lineToAbsolute(history.lastPoint.x, y + history.lastPoint.y)
    }

    override fun lineToVerticalAbsolute(y: Float) {
        lineToAbsolute(history.lastPoint.x, y)
    }

    override fun curveToCubicRelative(x1: Float, y1: Float, x2: Float, y2: Float, x: Float, y: Float) {
        curveToCubicAbsolute(x1 + history.lastPoint.x,
                y1 + history.lastPoint.y,
                x2 + history.lastPoint.x,
                y2 + history.lastPoint.y,
                x + history.lastPoint.x,
                y + history.lastPoint.y)
    }

    override fun curveToCubicAbsolute(x1: Float, y1: Float, x2: Float, y2: Float, x: Float, y: Float) {
        coordinates[0] = x1
        coordinates[1] = y1
        coordinates[2] = x2
        coordinates[3] = y2
        coordinates[4] = x
        coordinates[5] = y

        val bezier = Bezier()
        bezier.setCoordinates(history.lastPoint.x, history.lastPoint.y, coordinates, 3)
        segments.add(bezier)
        curveLength += bezier.length
        history.setLast(x, y)
        history.setLastKnot(x2, y2)
    }

    override fun curveToCubicSmoothRelative(x2: Float, y2: Float, x: Float, y: Float) {
        curveToCubicSmoothAbsolute(x2 + history.lastPoint.x,
                y2 + history.lastPoint.y,
                x + history.lastPoint.x,
                y + history.lastPoint.y)
    }

    override fun curveToCubicSmoothAbsolute(x2: Float, y2: Float, x: Float, y: Float) {
        val oldKx = history.lastKnot.x
        val oldKy = history.lastKnot.y
        val oldX = history.lastPoint.x
        val oldY = history.lastPoint.y
        //Calc knot as reflection of old knot
        val k1x = oldX * 2f - oldKx
        val k1y = oldY * 2f - oldKy

        coordinates[0] = k1x
        coordinates[1] = k1y
        coordinates[2] = x2
        coordinates[3] = y2
        coordinates[4] = x
        coordinates[5] = y

        val bezier = Bezier()
        bezier.setCoordinates(history.lastPoint.x, history.lastPoint.y, coordinates, 3)
        segments.add(bezier)
        curveLength += bezier.length

        history.setLast(x, y)
        history.setLastKnot(x2, y2)
    }

    override fun curveToQuadraticRelative(x1: Float, y1: Float, x: Float, y: Float) {
        curveToQuadraticAbsolute(x1 + history.lastPoint.x,
                y1 + history.lastPoint.y,
                x + history.lastPoint.x,
                y + history.lastPoint.y)
    }

    override fun curveToQuadraticAbsolute(x1: Float, y1: Float, x: Float, y: Float) {
        coordinates[0] = x1
        coordinates[1] = y1
        coordinates[2] = x
        coordinates[3] = y
        coordinates[4] = 0f
        coordinates[5] = 0f

        val bezier = Bezier()
        bezier.setCoordinates(history.lastPoint.x, history.lastPoint.y, coordinates, 2)
        segments.add(bezier)
        curveLength += bezier.length

        history.setLast(x, y)
        history.setLastKnot(x1, y1)
    }

    override fun curveToQuadraticSmoothRelative(x: Float, y: Float) {
        curveToQuadraticSmoothAbsolute(
                x + history.lastPoint.x,
                y + history.lastPoint.y)
    }

    override fun curveToQuadraticSmoothAbsolute(x: Float, y: Float) {
        curveToQuadraticAbsolute(history.lastKnot.x, history.lastKnot.y, x, y)
    }

    override fun arcRelative(rx: Float, ry: Float, xAxisRotation: Float, largeArcFlag: Boolean, sweefFlag: Boolean, x: Float, y: Float) {
    }

    override fun arcAbsolute(rx: Float, ry: Float, xAxisRotation: Float, largeArcFlag: Boolean, sweefFlag: Boolean, x: Float, y: Float) {
    }

    override fun generateVectors(tolerance: Float): List<Vector> {
        val returnList = ArrayList<Vector>()
        var runningInterpolation = 0.0f
        while (runningInterpolation <= 1.0) {
            runningInterpolation += tolerance
            returnList.add(evaluate(runningInterpolation))
        }

        return returnList
    }

    private fun evaluate(interpolation: Float): Vector {
        var returnPoint = Vector(0f, 0f)

        var tempCurveLength = curveLength * interpolation
        for(segment in segments) {
            if (tempCurveLength < segment.length) {
                returnPoint = segment.eval((tempCurveLength / segment.length), returnPoint)
                break
            }
            tempCurveLength -= segment.length
        }

        return returnPoint
    }
}