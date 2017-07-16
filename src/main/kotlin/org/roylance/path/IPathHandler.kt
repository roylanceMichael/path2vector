package org.roylance.path

import org.roylance.path.models.Vector

interface IPathHandler {
    fun startPath()
    fun endPath()
    fun moveToRelative(x: Float, y: Float)
    fun moveToAbsolute(x: Float, y: Float)
    fun closePath()
    fun lineToRelative(x: Float, y: Float)
    fun lineToAbsolute(x: Float, y: Float)
    fun lineToHorizontalRelative(x: Float)
    fun lineToHorizontalAbsolute(x: Float)
    fun lineToVerticalRelative(y: Float)
    fun lineToVerticalAbsolute(y: Float)
    fun curveToCubicRelative(x1: Float, y1: Float,
                             x2: Float, y2: Float,
                             x: Float, y: Float)
    fun curveToCubicAbsolute(x1: Float, y1: Float,
                             x2: Float, y2: Float,
                             x: Float, y: Float)
    fun curveToCubicSmoothRelative(x2: Float, y2: Float,
                                   x: Float, y: Float)
    fun curveToCubicSmoothAbsolute(x2: Float, y2: Float,
                                   x: Float, y: Float)
    fun curveToQuadraticRelative(x1: Float, y1: Float,
                                 x: Float, y: Float)
    fun curveToQuadraticAbsolute(x1: Float, y1: Float,
                                 x: Float, y: Float)
    fun curveToQuadraticSmoothRelative(x: Float, y: Float)
    fun curveToQuadraticSmoothAbsolute(x: Float, y: Float)
    fun arcRelative(rx: Float, ry: Float,
                    xAxisRotation: Float,
                    largeArcFlag: Boolean, sweefFlag: Boolean,
                    x: Float, y: Float)

    fun arcAbsolute(rx: Float, ry: Float,
                    xAxisRotation: Float,
                    largeArcFlag: Boolean, sweefFlag: Boolean,
                    x: Float, y: Float)

    fun generateVectors(tolerance: Float = 0.0001f): List<Vector>
}