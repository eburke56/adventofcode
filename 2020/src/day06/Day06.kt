package day03

import util.readAllLines

private fun findHighestSeat(seats: Set<Int>): Int {
    return seats.max() ?: -1
}

private fun getAnswers(filename: String): List<Set<Char>> {
    val result = mutableListOf<Set<Char>>()
    val set = mutableSetOf<Char>()

    readAllLines(filename).map { line ->
        if (line.isBlank()) {
            if (set.isNotEmpty()) {
                result.add(set.toSet())
                set.clear()
            }
        } else {
            line.forEach { set.add(it) }
        }
    }

    if (set.isNotEmpty()) {
        result.add(set.toSet())
    }

    return result
}

private fun getAnswersCount(filename: String): Int {
    val list = getAnswers(filename)
    return list.sumBy { it.size }
}

private fun getEveryoneAnswers(filename: String): List<Set<Char>> {
    val result = mutableListOf<Set<Char>>()
    var set: Set<Char> = setOf()
    var lineSet: Set<Char>
    var firstPass = true

    readAllLines(filename).map { line ->
        if (line.isBlank()) {
            result.add(set)
            firstPass = true
        } else {
            lineSet = line.toSet()

            if (firstPass) {
                set = lineSet
                firstPass = false
            } else if (set.isNotEmpty()) {
                set = set.intersect(lineSet)
            }
        }
    }

    result.add(set)

    return result
}

private fun getEveryoneCount(filename: String): Int {
    val list = getEveryoneAnswers(filename)
    return list.sumBy { it.size }
}

fun main() {
    var count: Int

    count = getAnswersCount("test.txt")
    println(count)

    count = getAnswersCount("input.txt")
    println(count)

    count = getEveryoneCount("test.txt")
    println(count)

    count = getEveryoneCount("input.txt")
    println(count)
}
