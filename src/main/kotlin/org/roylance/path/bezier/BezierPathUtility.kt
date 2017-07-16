package org.roylance.path.bezier

import org.roylance.path.IPathHandler
import java.util.*
import java.util.regex.Pattern

object BezierPathUtility {
    val matchPathRegex = Pattern.compile("([MmLlHhVvAaQqTtCcSsZz])|([-+]?((\\d*\\.\\d+)|(\\d+))([eE][-+]?\\d+)?)")!!

    fun parsePathString(path: String): IPathHandler {
        val bezierPath = BezierListProducer()
        val commandList = matchPathRegex.matcher(path)

        val tokens = LinkedList<String>()
        while (commandList.find()) {
            tokens.addLast(commandList.group())
        }

        var currentCommand = ZUpper
        var i = 0
        while (tokens.isNotEmpty()) {
            val currentToken = tokens.removeFirst()
            val initChar = currentToken[0]

            if ((initChar in AUpper..ZUpper) || (initChar in ALower..ZLower)) {
                currentCommand = initChar
            }
            else {
                tokens.addFirst(currentToken)
            }

            when (currentCommand) {
                MUpper -> {
                    i++
                    bezierPath.moveToAbsolute(nextFloat(tokens), nextFloat(tokens))
                    currentCommand = LUpper
                }
                MLower -> {
                    i++
                    bezierPath.moveToRelative(nextFloat(tokens), nextFloat(tokens))
                    currentCommand = LLower
                }
                LUpper -> {
                    i++
                    bezierPath.lineToAbsolute(nextFloat(tokens), nextFloat(tokens))
                }
                LLower -> {
                    i++
                    bezierPath.lineToRelative(nextFloat(tokens), nextFloat(tokens))
                }
                HUpper -> {
                    i++
                    bezierPath.lineToHorizontalAbsolute(nextFloat(tokens))
                }
                HLower -> {
                    i++
                    bezierPath.lineToHorizontalRelative(nextFloat(tokens))
                }
                VUpper -> {
                    i++
                    bezierPath.lineToVerticalAbsolute(nextFloat(tokens))
                }
                VLower -> {
                    i++
                    bezierPath.lineToVerticalRelative(nextFloat(tokens))
                }
                ALower, AUpper -> {
                    i++
                }
                QUpper -> {
                    i++
                    bezierPath.curveToQuadraticAbsolute(nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens))
                }
                QLower -> {
                    i++
                    bezierPath.curveToQuadraticRelative(nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens))
                }
                TUpper -> {
                    i++
                    bezierPath.curveToQuadraticSmoothAbsolute(nextFloat(tokens), nextFloat(tokens))
                }
                TLower -> {
                    i++
                    bezierPath.curveToQuadraticSmoothRelative(nextFloat(tokens), nextFloat(tokens))
                }
                CUpper -> {
                    i++
                    bezierPath.curveToCubicAbsolute(nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens))
                }
                CLower -> {
                    i++
                    bezierPath.curveToCubicRelative(nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens))
                }
                SUpper -> {
                    i++
                    bezierPath.curveToCubicSmoothAbsolute(nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens))
                }
                SLower -> {
                    i++
                    bezierPath.curveToCubicSmoothRelative(nextFloat(tokens), nextFloat(tokens), nextFloat(tokens), nextFloat(tokens))
                }
                ZUpper, ZLower -> {
                    i++
                    bezierPath.closePath()
                }
            }

        }

        return bezierPath
    }

    private fun nextFloat(l: LinkedList<String>): Float {
        return l.removeFirst().toFloat()
    }

    const val ZUpper = 'Z'
    const val ZLower = 'z'
    const val AUpper = 'A'
    const val ALower = 'a'
    const val MUpper = 'M'
    const val MLower = 'm'
    const val LUpper = 'L'
    const val LLower = 'l'
    const val HUpper = 'H'
    const val HLower = 'h'
    const val VUpper = 'V'
    const val VLower = 'v'
    const val QUpper = 'Q'
    const val QLower = 'q'
    const val TUpper = 'T'
    const val TLower = 't'
    const val CUpper = 'C'
    const val CLower = 'c'
    const val SUpper = 'S'
    const val SLower = 's'
}