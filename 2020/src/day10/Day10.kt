package day10

import util.readAllLinesAsInt
import java.lang.IllegalStateException

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
    println (input.joinToString { "%3s".format(it) })

    var product = 1L
    var tempProduct = 1

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

    println (counts.joinToString { "%3s".format(it) })

    var numThrees = 0

    counts.forEach { value ->
        when (value) {
            1 -> {
                product *= tempProduct
                numThrees = 0
                tempProduct = 1
            }
            2 -> {
                tempProduct *= when (numThrees) {
                    0 -> 2
                    1 -> 4
                    2 -> 8
                    else -> throw IllegalStateException()
                }
                product *= tempProduct
                numThrees = 0
                tempProduct = 1
            }
            3 -> {
                numThrees++
            }
            else -> throw IllegalStateException()
        }
    }

    println(product)
}

fun main() {
//    findOnesAndThrees("test.txt")
//    findOnesAndThrees("test2.txt")
//    findOnesAndThrees("input.txt")
    findPossibleArrangements("test.txt")
    findPossibleArrangements("test2.txt")
//    findPossibleArrangements("input.txt")
}
