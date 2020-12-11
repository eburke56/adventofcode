package day11

import util.readAllLines

private fun readInput(filename: String): Array<CharArray> {
    val input = readAllLines(filename)
    return input.map { it.toCharArray() }.toTypedArray()
}

private fun findSteadyState(filename: String) {
    val input = readInput(filename)
    val getCell = { map: Array<CharArray>, row: Int, col: Int ->
        if (row in map.indices && col in map[row].indices) {
            map[row][col]
        } else {
            '.'
        }
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
        map.forEach {
            println (it.joinToString(""))
        }
        println()
    }

    val cloneArray = { map: Array<CharArray> ->
        Array(map.size) { map.get(it).clone() }
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

fun main() {
    findSteadyState("test.txt")
    findSteadyState("input.txt")
}
