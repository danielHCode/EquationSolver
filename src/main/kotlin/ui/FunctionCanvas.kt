package ui

import equation.VarExpression
import java.awt.Canvas
import numbers.Number
import numbers.toNumber
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics

class FunctionCanvas(val function: VarExpression) : Canvas() {

    private val solutions = mutableMapOf<Number, Number>()

    private val width = 400
    private val height = 400

    private val step = 10
    private val range = 2

    init {
        preferredSize = Dimension(400, 400)
        calculate()
    }
    fun start() {
        paint(graphics)

    }

    private fun calculate() {
        for (i in -step*range..step*range) {
            val num = (i.toNumber() / step.toNumber())
            solutions[num] = function.forX(num)
        }
    }

    override fun paint(g: Graphics) {
        //background
        g.color = Color.GRAY
        g.fillRect(0, 0, width, height)

        //axis
        g.color = Color.BLACK
        g.drawLine(width/2, 0, width/2, height)
        g.drawLine(0, height/2, width, height/2)
        for (r in 0 until range*2) {
            val x = (width/(range*2))*r
            g.drawLine(x, (height/2)+3, x, (height/2)-3)
        }

        //function
        g.color = Color.BLUE
        val unit = width/(range/2)
        val xs = solutions.keys.map { (width/2)-(it*(unit/step).toNumber()).toInt() }
        val ys = solutions.values.map { (width/2)-(it*(unit/step).toNumber()).toInt() }
        g.drawPolyline(xs.toIntArray(), ys.toIntArray(), solutions.size)
        g.drawPolyline(xs.toIntArray(), ys.map { it-1 }.toIntArray(), solutions.size)
    }
}