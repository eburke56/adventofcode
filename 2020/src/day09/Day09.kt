package day08

import util.readAllLinesAsLong

private fun findFirstFailure(filename: String, preambleLength: Int): Pair<Long, List<Long>> {
    val original = readAllLinesAsLong(filename)
    val list = original.toMutableList()

    while (list.size > preambleLength) {
        val testList = list.take(preambleLength)
        val testValue = list[preambleLength]
        if (!testList.any { testList.contains(testValue - it) }) {
            return Pair(testValue, original)
        }
        list.removeAt(0)
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
