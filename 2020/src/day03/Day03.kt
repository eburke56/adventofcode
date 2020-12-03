package day03

import util.readAllLines

fun findTrees(dx: Int, dy: Int): Int {
    val input = readAllLines("input.txt")
    val height = input.size
    val width = input[0].length
    val matrix: Array<MutableList<Char>> = Array(height) { mutableListOf<Char>() }

    for (i in 0 until height) {
        input[i].forEach { char ->
            matrix[i].add(char)
        }
    }

    matrix.forEach {
        it.forEach { char ->
            print(char)
        }
        println()
    }

    var trees = 0
    var x = 0
    var y = 0
    while (y < height) {
        if (matrix[y][x] == '#') {
            trees++
        }
        x = (x + dx) % width
        y += dy
    }

    return trees
}

fun main() {
    val trees = findTrees(3, 1)
    println(trees)

    val mult = findTrees(1, 1) *
        findTrees(3, 1) *
        findTrees(5, 1) *
        findTrees(7, 1) *
        findTrees(1, 2)
    println(mult)
}
