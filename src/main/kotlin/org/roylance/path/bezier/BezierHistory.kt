package org.roylance.path.bezier

import org.roylance.path.models.Vector


class BezierHistory {
    val startPoint = Vector(0f, 0f)
    val lastPoint = Vector(0f, 0f)
    val lastKnot = Vector(0f, 0f)

    fun setLast(x: Float, y: Float) {
        lastPoint.x = x
        lastPoint.y = y
    }

    fun setLastKnot(x: Float, y: Float) {
        lastKnot.x = x
        lastKnot.y = y
    }
}