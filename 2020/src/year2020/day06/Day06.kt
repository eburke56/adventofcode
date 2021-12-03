package year2020.day06

import util.readAllLines

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

fun main() {
    var count: Int

    count = getAnswers("test.txt").sumBy { it.size }
    println(count)

    count = getAnswers("input.txt").sumBy { it.size }
    println(count)

    count = getEveryoneAnswers("test.txt").sumBy { it.size }
    println(count)

    count = getEveryoneAnswers("input.txt").sumBy { it.size }
    println(count)
}
