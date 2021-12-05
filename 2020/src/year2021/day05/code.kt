package year2021.day05

import util.readAllLines
import kotlin.math.max
import kotlin.math.min

private class Node(initialValue: Int) {
    var value: Int = initialValue
        private set

    fun increment() { value += 1 }
}

private class Board(values: List<String>, val allowDiagonal: Boolean = false) {
    private val data = mutableMapOf<Pair<Int, Int>, Node>()

    init {
        values.forEach { line ->
            val points = line.split(Regex(" -> ")).map { it.split(",") }
            val (x1, y1) = points[0].map { it.toInt() }
            val (x2, y2) = points[1].map { it.toInt() }
            val xmin = min(x1, x2)
            val xmax = max(x1, x2)
            val ymin = min(y1, y2)
            val ymax = max(y1, y2)

            if (allowDiagonal && (ymax - ymin == xmax - xmin)) {
                val xstep = (if (x2 > x1) 1 else -1)
                val ystep = (if (y2 > y1) 1 else -1)
                var key = Pair(x1, y1)
                repeat((xmin..xmax).count()) {
                    incrementNode(key)
                    key = Pair(key.first + xstep, key.second + ystep)
                }
            } else if (xmin == xmax) {
                (ymin..ymax).forEach {
                    incrementNode(Pair(x1, it))
                }
            } else if (ymin == ymax) {
                (xmin..xmax).forEach {
                    incrementNode(Pair(it, ymin))
                }
            }
        }
    }

    private fun incrementNode(key: Pair<Int, Int>) {
        if (!data.containsKey(key)) {
            data[key] = Node(1)
        } else {
            data[key]?.increment()
        }
    }

    fun countNodesGreaterThan(value: Int): Int {
        return data.values.count { it.value > value }
    }
}

fun main() {
    part1()
    part2()
}

private fun part1() {
    val input = readAllLines("input.txt")
    val board = Board(input)
    println(board.countNodesGreaterThan(1))
}

private fun part2() {
    val input = readAllLines("input.txt")
    val board = Board(input, true)
    println(board.countNodesGreaterThan(1))
}
