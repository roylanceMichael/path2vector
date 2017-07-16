package org.roylance.path.bezier

import com.piro.bezier.ParseException
import com.piro.bezier.PathHandler
import java.util.*


class BezierListProducer : PathHandler {

    internal val bezierSegs = ArrayList<Bezier>()
    internal var coords = FloatArray(6)
    internal var curveLength = 0f
    internal var hist = BezierHistory()

    @Throws(ParseException::class)
    override fun startPath() {
        curveLength = 0f
        bezierSegs.clear()
    }

    @Throws(ParseException::class)
    override fun movetoRel(x: Float, y: Float) {
        val offx = hist.lastPoint.x
        val offy = hist.lastPoint.y

        movetoAbs(offx + x, offy + y)
    }

    @Throws(ParseException::class)
    override fun movetoAbs(x: Float, y: Float) {
        hist.setLastPoint(x, y)
    }

    @Throws(ParseException::class)
    override fun closePath() {
        //command(SVGPathSeg.PATHSEG_CLOSEPATH);
    }

    @Throws(ParseException::class)
    override fun linetoRel(x: Float, y: Float) {
        val offx = hist.lastPoint.x
        val offy = hist.lastPoint.y

        linetoAbs(offx + x, offy + y)
    }

    @Throws(ParseException::class)
    override fun linetoAbs(x: Float, y: Float) {

        coords[0] = x
        coords[1] = y

        val b = Bezier(hist.lastPoint.x, hist.lastPoint.y, coords, 1)
        bezierSegs.add(b)
        curveLength += b.length

        hist.setLastPoint(x, y)
        hist.setLastKnot(x, y)

    }

    @Throws(ParseException::class)
    override fun linetoHorizontalRel(x: Float) {
        linetoAbs(x + hist.lastPoint.x, hist.lastPoint.y)
    }

    @Throws(ParseException::class)
    override fun linetoHorizontalAbs(x: Float) {
        linetoAbs(x, hist.lastPoint.y)
    }

    @Throws(ParseException::class)
    override fun linetoVerticalRel(y: Float) {
        linetoAbs(hist.lastPoint.x, y + hist.lastPoint.y)
    }

    @Throws(ParseException::class)
    override fun linetoVerticalAbs(y: Float) {
        linetoAbs(hist.lastPoint.x, y)
    }

    @Throws(ParseException::class)
    override fun curvetoCubicRel(x1: Float, y1: Float,
                                 x2: Float, y2: Float,
                                 x: Float, y: Float) {
        val offx = hist.lastPoint.x
        val offy = hist.lastPoint.y

        curvetoCubicAbs(x1 + offx, y1 + offy,
                x2 + offx, y2 + offy,
                x + offx, y + offy)
    }

    @Throws(ParseException::class)
    override fun curvetoCubicAbs(x1: Float, y1: Float,
                                 x2: Float, y2: Float,
                                 x: Float, y: Float) {

        coords[0] = x1
        coords[1] = y1
        coords[2] = x2
        coords[3] = y2
        coords[4] = x
        coords[5] = y

        val b = Bezier(hist.lastPoint.x, hist.lastPoint.y, coords, 3)
        bezierSegs.add(b)
        curveLength += b.length
        hist.setLastPoint(x, y)
        hist.setLastKnot(x2, y2)
    }

    @Throws(ParseException::class)
    override fun curvetoCubicSmoothRel(x2: Float, y2: Float,
                                       x: Float, y: Float) {
        val offx = hist.lastPoint.x
        val offy = hist.lastPoint.y

        curvetoCubicSmoothAbs(x2 + offx, y2 + offy, x + offx, y + offy)
    }

    @Throws(ParseException::class)
    override fun curvetoCubicSmoothAbs(x2: Float, y2: Float,
                                       x: Float, y: Float) {

        val oldKx = hist.lastKnot.x
        val oldKy = hist.lastKnot.y
        val oldX = hist.lastPoint.x
        val oldY = hist.lastPoint.y
        //Calc knot as reflection of old knot
        val k1x = oldX * 2f - oldKx
        val k1y = oldY * 2f - oldKy

        coords[0] = k1x
        coords[1] = k1y
        coords[2] = x2
        coords[3] = y2
        coords[4] = x
        coords[5] = y

        val b = Bezier(hist.lastPoint.x, hist.lastPoint.y, coords, 3)
        bezierSegs.add(b)
        curveLength += b.length
        hist.setLastPoint(x, y)
        hist.setLastKnot(x2, y2)
    }

    @Throws(ParseException::class)
    override fun curvetoQuadraticRel(x1: Float, y1: Float,
                                     x: Float, y: Float) {
        val offx = hist.lastPoint.x
        val offy = hist.lastPoint.y

        curvetoQuadraticAbs(x1 + offx, y1 + offy, x + offx, y + offy)
    }

    @Throws(ParseException::class)
    override fun curvetoQuadraticAbs(x1: Float, y1: Float,
                                     x: Float, y: Float) {

        coords[0] = x1
        coords[1] = y1
        coords[2] = x
        coords[3] = y

        val b = Bezier(hist.lastPoint.x, hist.lastPoint.y, coords, 2)
        bezierSegs.add(b)
        curveLength += b.length

        hist.setLastPoint(x, y)
        hist.setLastKnot(x1, y1)
    }

    override fun curvetoQuadraticSmoothRel(x: Float, y: Float) {
        val offx = hist.lastPoint.x
        val offy = hist.lastPoint.y

        curvetoQuadraticSmoothAbs(x + offx, y + offy)
    }

    @Throws(ParseException::class)
    override fun curvetoQuadraticSmoothAbs(x: Float, y: Float) {

        curvetoQuadraticAbs(hist.lastKnot.x, hist.lastKnot.y, x, y)
    }

    @Throws(ParseException::class)
    override fun arcRel(rx: Float, ry: Float,
                        xAxisRotation: Float,
                        largeArcFlag: Boolean, sweepFlag: Boolean,
                        x: Float, y: Float) {

    }

    @Throws(ParseException::class)
    override fun arcAbs(rx: Float, ry: Float,
                        xAxisRotation: Float,
                        largeArcFlag: Boolean, sweepFlag: Boolean,
                        x: Float, y: Float) {

    }

    @Throws(ParseException::class)
    override fun endPath() {
        hist.setLastPoint(hist.startPoint.x, hist.startPoint.y)
        hist.setLastKnot(hist.startPoint.x, hist.startPoint.y)
    }
}