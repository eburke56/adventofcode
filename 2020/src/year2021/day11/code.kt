package year2021.day11

import util.readAllLines

private fun countFlashes(grid: List<List<Octopus>>, iteration: Int, maxIterations: Int): Int {
    if (iteration > maxIterations) return 0
    var numFlashed = 0
    var justFlashed = initialStep(grid)

    while (justFlashed.isNotEmpty()) {
        numFlashed += justFlashed.size
        justFlashed = getJustFlashed(grid, justFlashed)
    }

    return numFlashed + countFlashes(grid, iteration + 1, maxIterations)
}

private fun findSynced(grid: List<List<Octopus>>, iteration: Int) {
    var justFlashed = initialStep(grid)

    while (justFlashed.isNotEmpty()) {
        justFlashed = getJustFlashed(grid, justFlashed)
    }

    if (grid.sumOf { row -> row.count { it.value > 0 } } == 0) {
        println(iteration)
    } else {
        findSynced(grid, iteration + 1)
    }
}

private fun initialStep(grid: List<List<Octopus>>): MutableSet<Pair<Int, Int>> {
    grid.forEach { row -> row.forEach { octopus -> octopus.increment() } }

    return grid.flatMapIndexed { r, row ->
        row.mapIndexedNotNull { c, octopus ->
            if (octopus.value == 0) Pair(r, c) else null
        }
    }.toMutableSet()
}

private fun getJustFlashed(
    grid: List<List<Octopus>>,
    justFlashed: MutableSet<Pair<Int, Int>>
): MutableSet<Pair<Int, Int>> {
    val maxRow = grid.size - 1
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
                        if (justFlashed.contains(Pair(r, c))) {
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

    return newJustFlashed
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
    part1()
    part2()
}

private fun part1() {
    val grid = readAllLines("input.txt").map { line ->
        line.map { char -> Octopus(char.digitToInt()) }
    }

    println(countFlashes(grid, 1, 100))
}

private fun part2() {
    val grid = readAllLines("input.txt").map { line ->
        line.map { char -> Octopus(char.digitToInt()) }
    }

    findSynced(grid, 1)
}
