package year2021.day11

import util.readAllLines
import kotlin.math.max

//private fun dumpGrid(grid: List<List<Octopus>>, label: Any) {
//    if (label.toString().isNotEmpty())
//        println("Iteration $label:")
//    else
//        println()
//
//    grid.forEach { row ->
//        print("    ")
//        row.forEach { octopus ->
//            print(octopus.value)
//        }
//        println()
//    }
//}

private fun step(grid: List<List<Octopus>>, iteration: Int, maxIterations: Int): Int {
    if (iteration == 0) return 0

    grid.forEach { row ->
        row.forEach { octopus ->
            octopus.increment()
        }
    }

    return stepFlashed(grid, iteration, maxIterations)
}

private fun stepFlashed(grid: List<List<Octopus>>, iteration: Int, maxIterations: Int): Int {
    if (iteration == 0) return 0

    var numFlashed = 0
    var justFlashed = grid.flatMapIndexed { r, row ->
        row.mapIndexedNotNull { c, octopus ->
            if (octopus.value == 0) Pair(
                r,
                c
            ) else null
        }
    }.toMutableSet()

    while (justFlashed.isNotEmpty()) {
        val maxRow = grid.size - 1
        numFlashed += justFlashed.size
        val newJustFlashed = mutableSetOf<Pair<Int, Int>>()

        grid.forEachIndexed { rowIndex, row ->
            val maxCol = row.size - 1
            row.forEachIndexed { colIndex, octopus ->
                if (octopus.value > 0) {
                    var numAdjacentFlashed = 0
                    for (r in rowIndex - 1..rowIndex + 1) {
                        for (c in colIndex - 1..colIndex + 1) {
                            if (r == rowIndex && c == colIndex) continue
                            if (r < 0 || r > maxRow) continue
                            if (c < 0 || c > maxCol) continue
                            if (justFlashed.contains(Pair(r, c)) && grid[r][c].value == 0) {
                                numAdjacentFlashed += 1
                            }
                        }
                    }
                    if (octopus.increment(numAdjacentFlashed)) {
                        newJustFlashed.add(Pair(rowIndex, colIndex))
                    }
                }
            }
        }

        justFlashed = newJustFlashed
    }

    if (grid.sumOf { row -> row.count { it.value > 0 } } == 0) {
        println("ALL SYNCED AT ${maxIterations - iteration}")
    }

    return numFlashed + step(grid, iteration - 1, maxIterations)
}

class Octopus(initialValue: Int) {
    var value = initialValue
        private set

    fun increment(amount: Int = 1): Boolean {
        value += amount
        return if (value > 9) { value = 0; true } else false
    }
}

fun main() {
//    part1()
    part2()
}

private fun part1() {
    val grid = readAllLines("input.txt").mapIndexed { rowIndex, line ->
        line.mapIndexed { colIndex, char -> Octopus(char.digitToInt()) }
    }

    println(step(grid, 100, 100))
}

private fun part2() {
    val grid = readAllLines("test.txt").mapIndexed { rowIndex, line ->
        line.mapIndexed { colIndex, char -> Octopus(char.digitToInt()) }
    }

    println(step(grid, 100, 100))
}
