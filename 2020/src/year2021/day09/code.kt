package year2021.day09

import util.readAllLines

data class Cell(val row: Int, val col: Int)

fun main() {
    part1()
    part2()
}

private fun part1() {
    val grid = readGrid("input.txt")
    val lows = findLows(grid).sumOf { grid[it.row][it.col] + 1 }
    println(lows)
}

private fun part2() {
    val grid = readGrid("input.txt")
    val lows = findLows(grid)
    val maxRow = grid.size - 1
    val maxCol = grid[0].size - 1
    val basins = mutableSetOf<Set<Cell>>()

    while (lows.isNotEmpty()) {
        val basin = mutableSetOf<Cell>()
        testCell(grid, lows, maxRow, maxCol, basin)
        basins.add(basin)
    }

    var product = 1
    basins.sortedByDescending { it.size }.take(3).forEach { product *= it.size }
    println(product)
}

private fun readGrid(filename: String) = readAllLines(filename).map { line -> line.toCharArray().map { it.code - '0'.code } }

private fun testCell(grid: List<List<Int>>,
                     lows: MutableSet<Cell>,
                     maxRow: Int,
                     maxCol: Int,
                     basin: MutableSet<Cell>,
                     cellUnderTest: Cell? = null) {
    val cell = cellUnderTest ?: lows.first()
    val row = cell.row
    val col = cell.col
    val currentValue = grid[row][col]

    // stop at the top
    if (currentValue == 9) {
        return
    }

    basin.add(cell)

    // don't re-test this cell if it's also a low
    if (lows.contains(cell)) {
        lows.remove(cell)
    }

    if (row > 0) {
        val nextCell = Cell(row - 1, col)
        if (!basin.contains(nextCell) && grid[row - 1][col] > currentValue) {
            testCell(grid, lows, maxRow, maxCol, basin, nextCell)
        }
    }
    if (row < maxRow) {
        val nextCell = Cell(row + 1, col)
        if (!basin.contains(nextCell) && grid[row + 1][col] > currentValue) {
            testCell(grid, lows, maxRow, maxCol, basin, nextCell)
        }
    }
    if (col > 0) {
        val nextCell = Cell(row, col - 1)
        if (!basin.contains(nextCell) && grid[row][col - 1] > currentValue) {
            testCell(grid, lows, maxRow, maxCol, basin, nextCell)
        }
    }
    if (col < maxCol) {
        val nextCell = Cell(row, col + 1)
        if (!basin.contains(nextCell) && grid[row][col + 1] > currentValue) {
            testCell(grid, lows, maxRow, maxCol, basin, nextCell)
        }
    }
}

private fun findLows(grid: List<List<Int>>): MutableSet<Cell> {
    val lows = mutableSetOf<Cell>()
    val maxRow = grid.size - 1
    for (i in 0..maxRow) {
        val maxCol = grid[i].size - 1
        for (j in 0..maxCol) {
            if ((i == 0 || grid[i][j] < grid[i - 1][j])
                && (i == maxRow || grid[i][j] < grid[i + 1][j])
                && (j == 0 || grid[i][j] < grid[i][j - 1])
                && (j == maxCol || grid[i][j] < grid[i][j + 1])
            ) {

                lows.add(Cell(i, j))
            }
        }
    }
    return lows
}
