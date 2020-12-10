package day10

import util.readAllLinesAsInt

private fun readInput(filename: String) =
    readAllLinesAsInt(filename).sorted().toMutableList().apply {
        add(0, 0)
        add(checkNotNull(max()) + 3)
    }

private fun findOnesAndThrees(filename: String) {
    val input = readInput(filename)
    input.mapIndexed { index, value ->
        if (index > 0) value - input[index-1] else 0
    }.apply {
        val ones = count { it == 1 }
        val threes = count { it == 3 }
        println(ones * threes)
    }
}

private fun traverse(map: Map<Int, List<Int>>, start: Int): Int {
    val list = checkNotNull(map[start])
    return if (list.isNotEmpty()) {
        list.sumBy {
            traverse(map, it)
        }
    } else {
        1
    }
}

private fun findPossibleArrangements(filename: String) {
    val input = readInput(filename)
    val map = mutableMapOf<Int, MutableList<Int>>()

    input.forEachIndexed { index, value ->
        val list = mutableListOf<Int>()
        map[index] = list

        for (i in index + 1 until input.size) {
            if (input[i] - value <= 3) {
                list.add(i)
            } else {
                break
            }
        }
    }

    val combinations = traverse(map, 0)

    println(combinations)
}

fun main() {
//    findOnesAndThrees("test.txt")
//    findOnesAndThrees("test2.txt")
//    findOnesAndThrees("input.txt")
    findPossibleArrangements("test.txt")
    findPossibleArrangements("test2.txt")
    findPossibleArrangements("input.txt")
}
