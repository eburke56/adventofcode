package day08

import util.readAllLinesAs

private fun readInput(filename: String): MutableList<Long> =
    mutableListOf<Long>().apply { addAll(readAllLinesAs(filename) { it.toLong() }) }

private fun isSumOfTwoNumbers(list: List<Long>, number: Long): Boolean {
    list.forEach {
        if (list.contains(number - it)) {
            return true
        }
    }

    return false
}

private fun findFirstFailure(filename: String, preambleLength: Int): Pair<Long, List<Long>> {
    val input = readInput(filename)
    val inputCopy = input.toList()

    while (input.size > preambleLength) {
        val testList = input.take(preambleLength)
        val testValue = input[preambleLength]
        if (!isSumOfTwoNumbers(testList, testValue)) {
            return Pair(testValue, inputCopy)
        }
        input.removeAt(0)
    }

    return Pair(-1, listOf())
}

private fun findEncryptionFailure(filename: String, preambleLength: Int): Long {
    val (failValue, list) = findFirstFailure(filename, preambleLength)
    var total = list[0]
    var start = 0

    for (i in 1 until list.size) {
        if (total == failValue) {
            return list.subList(start, i).let {
                checkNotNull(it.min()) + checkNotNull(it.max())
            }
        }

        total += list[i]

        while (total > failValue) {
            total -= list[start]
            start++
        }
    }

    return -1L
}

fun main() {
    println("Test: ${findFirstFailure("test.txt", 5).first}")
    println("Real: ${findFirstFailure("input.txt", 25).first}")
    println("Test: ${findEncryptionFailure("test.txt", 5)}")
    println("Real: ${findEncryptionFailure("input.txt", 25)}")
}
