package org.roylance.path.bezier

import org.roylance.path.models.Vector
import java.util.*
import java.util.regex.Pattern


object BezierPathUtility {
    fun parsePathString(list: String, tolerance: Float = 0.0001f): List<Vector> {
        val path = BezierListProducer()
        val matchPathCmd = Pattern.compile("([MmLlHhVvAaQqTtCcSsZz])|([-+]?((\\d*\\.\\d+)|(\\d+))([eE][-+]?\\d+)?)").matcher(list)

        val tokens = LinkedList<String>()
        while (matchPathCmd.find()) {
            tokens.addLast(matchPathCmd.group())
        }

        var curCmd = 'Z'
        while (tokens.size != 0) {
            val curToken = tokens.removeFirst()
            val initChar = curToken[0]
            if (initChar in 'A'..'Z' || initChar in 'a'..'z') {
                curCmd = initChar
            } else {
                tokens.addFirst(curToken)
            }

            when (curCmd) {
                'M' -> {
                    path.movetoAbs(nextFloat(tokens), nextFloat(tokens))
                    curCmd = 'L'
                }
                'm' -> {
                    path.movetoRel(nextFloat(tokens), nextFloat(tokens))
                    curCmd = 'l'
                }
                'L' -> path.linetoAbs(nextFloat(tokens), nextFloat(tokens))
                'l' -> path.linetoRel(nextFloat(tokens), nextFloat(tokens))
                'H' -> path.linetoHorizontalAbs(nextFloat(tokens))
                'h' -> path.linetoHorizontalRel(nextFloat(tokens))
                'V' -> path.linetoVerticalAbs(nextFloat(tokens))
                'v' -> path.linetoVerticalAbs(nextFloat(tokens))
                'A', 'a' -> {
                }
                'Q' -> path.curvetoQuadraticAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens))
                'q' -> path.curvetoQuadraticAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens))
                'T' -> path.curvetoQuadraticSmoothAbs(nextFloat(tokens), nextFloat(tokens))
                't' -> path.curvetoQuadraticSmoothRel(nextFloat(tokens), nextFloat(tokens))
                'C' -> path.curvetoCubicAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens))
                'c' -> path.curvetoCubicRel(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens))
                'S' -> path.curvetoCubicSmoothAbs(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens))
                's' -> path.curvetoCubicSmoothRel(nextFloat(tokens), nextFloat(tokens),
                        nextFloat(tokens), nextFloat(tokens))
                'Z', 'z' -> path.closePath()
                else -> throw RuntimeException("Invalid path element")
            }
        }

        return produceVectors(tolerance, path)
    }

    private fun eval(interp: Float, path: org.roylance.path.bezier.BezierListProducer): Vector {
        val point = Vector(0f, 0f)
        var curLength = (path.curveLength * interp).toDouble()
        val it = path.bezierSegs.iterator()
        while (it.hasNext()) {
            val bez = it.next()

            val bezLength = bez.length.toDouble()
            if (curLength < bezLength) {
                val param = curLength / bezLength
                bez.eval(param, point)
                break
            }

            curLength -= bezLength
        }

        return point
    }

    private fun produceVectors(tolerance: Float = 0.0001f, path: org.roylance.path.bezier.BezierListProducer): List<Vector> {
        val returnVectors = ArrayList<Vector>()
        var runningTolerance = 0f
        while (runningTolerance < 1f) {
            runningTolerance += tolerance
            returnVectors.add(eval(runningTolerance, path))
        }

        return returnVectors
    }

    private fun nextFloat(l: LinkedList<String>): Float {
        val s = l.removeFirst()
        return java.lang.Float.parseFloat(s)
    }
}