package year2020.day10

import util.readAllLinesAsInt

private fun readInput(filename: String) =
    readAllLinesAsInt(filename).sorted().toMutableList().apply {
        add(0, 0)
        add(checkNotNull(maxOrNull()) + 3)
    }

private fun findOnesAndThrees(filename: String) {
    val input = readInput(filename)
    input.mapIndexed { index, value ->
        if (index > 0) value - input[index - 1] else 0
    }.apply {
        val ones = count { it == 1 }
        val threes = count { it == 3 }
        println(ones * threes)
    }
}

private fun traverse(
    map: Map<Int, List<Int>>,
    cache: MutableMap<Int, Long>,
    start: Int
): Long {
    if (cache.containsKey(start)) {
        return checkNotNull(cache[start])
    }

    val list = checkNotNull(map[start])
    return if (list.isNotEmpty()) {
        var sum = 0L
        list.forEach {
            sum += traverse(map, cache, it)
        }
        sum
    } else {
        1
    }.also {
        cache[start] = it
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

    val cache = mutableMapOf<Int, Long>()
    val combinations = traverse(map, cache, 0)
    println(combinations)
}

fun main() {
    findOnesAndThrees("test.txt")
    findOnesAndThrees("test2.txt")
    findOnesAndThrees("input.txt")
    findPossibleArrangements("test.txt")
    findPossibleArrangements("test2.txt")
    findPossibleArrangements("input.txt")
}
