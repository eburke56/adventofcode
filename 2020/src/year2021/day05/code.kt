package year2021.day05

import util.readAllLines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private class Node(initialValue: Int) {
    var value: Int = initialValue
        private set

    fun increment() { value += 1 }
}

private class Board(values: List<String>, val allowDiagonal: Boolean = false) {
    private val rows = mutableMapOf<Int, MutableMap<Int, Node>>()

    init {
        values.forEach { line ->
            val points = line.split(Regex(" -> ")).map { it.split(",") }
            val (x1, y1) = points[0].map { it.toInt() }
            val (x2, y2) = points[1].map { it.toInt() }
            val xmin = min(x1, x2)
            val xmax = max(x1, x2)
            val ymin = min(y1, y2)
            val ymax = max(y1, y2)

            println("Testing $x1,$y1 --> $x2,$y2")
            val slope = if (xmax - xmin != 0) abs((ymax - ymin) / (xmax - xmin)) else 0
            if (allowDiagonal && slope == 1) {
                val xstep = (if (x2 > x1) 1 else -1)
                val ystep = (if (y2 > y1) 1 else -1)
                println("     Diagonal, xstep = $xstep, ystep = $ystep")
                var c = x1
                var r = y1
                for (i in 0 until (xmax-xmin)) {
                    if (!rows.containsKey(c)) { rows[c] = mutableMapOf() }
                    val row = rows[c] ?: error("Bad row")
                    if (!row.containsKey(r)) {
                        row[r] = Node(1)
                    } else {
                        row[r]?.increment()
                    }
                    println("        Set $c,$r --> ${row[r]?.value}")
                    c += xstep
                    r += ystep
                }
            } else if (xmin == xmax) {
                println("     Vertical")
                if (!rows.containsKey(xmin)) { rows[xmin] = mutableMapOf() }
                val row = rows[xmin] ?: error("Bad row")

                for (i in ymin..ymax) {
                    if (!row.containsKey(i)) {
                        row[i] = Node(1)
                    } else {
                        row[i]?.increment()
                    }
                    println("        Set $xmin,$i --> ${row[i]?.value}")
                }
            } else if (ymin == ymax) {
                println("     Horizontal")
                for (i in xmin..xmax) {
                    if (!rows.containsKey(i)) { rows[i] = mutableMapOf() }
                    val row = rows[i] ?: error("Bad row")

                    if (!row.containsKey(ymin)) {
                        row[ymin] = Node(1)
                    } else {
                        row[ymin]?.increment()
                    }
                    println("        Set $i,$ymin --> ${row[ymin]?.value}")
                }
            }
        }
    }

    fun countNodesGreaterThan(value: Int): Int {
        return rows
            .flatMap { row -> row.value.values.filter { it.value > value } }
            .count()
    }
}

fun main() {
    part1()
//    part2()
}

private fun part1() {
    val input = readAllLines("input.txt")
    val board = Board(input)
    println(board.countNodesGreaterThan(1))
}

private fun part2() {
    val input = readAllLines("test.txt")
    val board = Board(input, true)
    println(board.countNodesGreaterThan(1))
}
