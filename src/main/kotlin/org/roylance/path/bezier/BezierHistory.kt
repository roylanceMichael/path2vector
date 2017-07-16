package org.roylance.path.bezier

import org.roylance.path.models.Vector


class BezierHistory {

    internal var startPoint = Vector(0f, 0f)
    internal var lastPoint = Vector(0f, 0f)
    internal var lastKnot = Vector(0f, 0f)

    fun setStartPoint(x: Float, y: Float) {
        startPoint.x = x
        startPoint.y = y
    }

    fun setLastPoint(x: Float, y: Float) {
        lastPoint.x = x
        lastPoint.y = y
    }

    fun setLastKnot(x: Float, y: Float) {
        lastKnot.x = x
        lastKnot.y = y
    }
}