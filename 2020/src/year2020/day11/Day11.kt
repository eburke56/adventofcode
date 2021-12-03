package year2020.day11

import util.readAllLines

private fun readInput(filename: String): Array<CharArray> {
    val input = readAllLines(filename)
    return input.map { it.toCharArray() }.toTypedArray()
}

val getCell = { map: Array<CharArray>, row: Int, col: Int ->
    if (row in map.indices && col in map[row].indices) {
        map[row][col]
    } else {
        '.'
    }
}

val countOccupied = { map: Array<CharArray> ->
    var occupied = 0
    for (r in map.indices) {
        for (c in map[r].indices) {
            if (getCell(map, r, c) == '#') {
                occupied++
            }
        }
    }
    occupied
}

val dumpMap = { map: Array<CharArray> ->
    println("---------------------------")
    map.forEach {
        println (it.joinToString(""))
    }
    println()
}

private fun findSteadyState1(filename: String) {
    val input = readInput(filename)

    val cloneArray = { map: Array<CharArray> ->
        Array(map.size) { map.get(it).clone() }
    }

    val countAdjacentOccupied = { map: Array<CharArray>, row: Int, col: Int ->
        var occupied = 0
        for (r in row-1..row+1) {
            for (c in col-1..col+1) {
                if (r == row && c == col) continue
                if (getCell(map, r, c) == '#') {
                    occupied++
                }
            }
        }
        occupied
    }

    var changed = true
    var map: Array<CharArray> = cloneArray(input)

    while (changed) {
        //dumpMap(map)
        val newMap = cloneArray(map)
        changed = false
        for (row in map.indices) {
            for (col in map[row].indices) {
                val occupied = countAdjacentOccupied(map, row, col)
                var newVal = getCell(map, row, col)

                if (newVal == '#') {
                    if (occupied >= 4) {
                        newVal = 'L'
                        changed = true
                    }
                } else if (newVal == 'L') {
                    if (occupied == 0) {
                        newVal = '#'
                        changed = true
                    }
                }

                newMap[row][col] = newVal
            }
        }
        map = newMap
    }

    println ("Steady: $filename -> ${countOccupied(map)}")
}

private fun findSteadyState2(filename: String) {
    val input = readInput(filename)

    val cloneArray = { map: Array<CharArray> ->
        Array(map.size) { map.get(it).clone() }
    }

    val countAdjacentOccupied = { map: Array<CharArray>, row: Int, col: Int ->
        var occupied = 0
        var iteration = 1

        val alreadySeen = Array(3) { BooleanArray(3) { false } }
        alreadySeen[1][1] = true // skip the cell we're looking at

        // if we've already hit a seat in every direction, we're done
        while (alreadySeen.any { rc -> rc.any { cell -> !cell } }) {

            val minrow = row - iteration
            val maxrow = row + iteration
            val mincol = col - iteration
            val maxcol = col + iteration

            // if the range is completely out of bounds, we're done
            if (minrow !in map.indices && maxrow !in map.indices &&
                mincol !in map[0].indices && maxcol !in map[0].indices) {
                break
            }

            val rowRange = minrow..maxrow
            val colRange = mincol..maxcol

            for (r in rowRange step iteration) {
                for (c in colRange step iteration) {
                    // skip the cell
                    if (r == row && c == col) {
                        continue
                    }

                    // this puts i,j in [-1,1].  could do the outer loop better but I'm lazy
                    val i = (r - minrow) / iteration
                    val j = (c - mincol) / iteration

                    if (i in alreadySeen.indices && j in alreadySeen[i].indices) {
                        // if this cell is not in bounds, we're done in that direction
                        if (r !in map.indices || c !in map[0].indices) {
                            alreadySeen[i][j] = true
                        }

                        // already tested this direction
                        if (alreadySeen[i][j]) {
                            continue
                        }

                        when (getCell(map, r, c)) {
                            '#' -> {
                                occupied++
                                alreadySeen[i][j] = true
                            }
                            'L' -> {
                                alreadySeen[i][j] = true
                            }
                            '.' -> {
                            }
                        }
                    }
                }
            }
            iteration++
        }
        occupied
    }

    var changed = true
    var map: Array<CharArray> = cloneArray(input)

    while (changed) {
        val newMap = cloneArray(map)
        changed = false
        for (row in map.indices) {
            for (col in map[row].indices) {
                val occupied = countAdjacentOccupied(map, row, col)
                var newVal = getCell(map, row, col)

                if (newVal == '#') {
                    if (occupied >= 5) {
                        newVal = 'L'
                        changed = true
                    }
                } else if (newVal == 'L') {
                    if (occupied == 0) {
                        newVal = '#'
                        changed = true
                    }
                }

                newMap[row][col] = newVal
            }
        }
        map = newMap
    }

    println ("Steady 2: $filename -> ${countOccupied(map)}")
}

fun main() {
    findSteadyState1("test.txt")
    findSteadyState1("input.txt")
    findSteadyState2("test.txt")
    findSteadyState2("input.txt")
}
