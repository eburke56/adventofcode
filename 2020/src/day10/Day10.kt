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

private fun findPossibleArrangements(filename: String) {
    val input = readInput(filename)

    val counts = input.mapIndexed { index, value ->
        var count = 1
        for (i in index + 2 until input.size) {
            if (input[i] - value <= 3) {
                count++
            } else {
                break
            }
        }
        count
    }

    val combinations = counts.fold(1) { product, item ->
        product * item
    }

    println(combinations)
}

fun main() {
//    findOnesAndThrees("test.txt")
//    findOnesAndThrees("test2.txt")
//    findOnesAndThrees("input.txt")
    findPossibleArrangements("test.txt")
}
