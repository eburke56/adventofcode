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

private fun findFirstFailure(filename: String, preambleLength: Int): Long {
    val input = readInput(filename)

    while (input.size > preambleLength) {
        val testList = input.take(preambleLength)
        val testValue = input[preambleLength]
        if (!isSumOfTwoNumbers(testList, testValue)) {
            return testValue
        }
        input.removeAt(0)
    }

    return -1
}

fun main() {
    println("Test: ${findFirstFailure("test.txt", 5)}")
    println("Read: ${findFirstFailure("input.txt", 25)}")
}
