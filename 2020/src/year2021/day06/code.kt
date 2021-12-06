package year2021.day06

import util.readAllLines

private class Lanternfish(initialValue: Int) {
    var value: Int = initialValue
        private set

    fun advance(): Lanternfish? {
        if (value == 0) {
            value = 6
            return Lanternfish(8)
        }

        value -= 1
        return null
    }
}

fun main() {
    part1()
    part2()
}

private fun part1() {
    run2(80, "test.txt")
}

private fun part2() {
    val times = 256
    val file = "input.txt"
    run2(times, file)
}

private fun run(times: Int, file: String) {
    val school = mutableListOf<Lanternfish>().apply {
        addAll(readAllLines(file).first().split(",").map { Lanternfish(it.toInt()) })
    }
    (1..times).forEach { iteration ->
        val newFish = mutableListOf<Lanternfish>()
        school.forEach { fish ->
            fish.advance()?.let { newFish.add(it) }
        }
        school.addAll(newFish)
    }
    println("Algo 1: ${school.size}")
}

private fun run2(times: Int, file: String) {
    val size = readAllLines(file).first().split(",")
        .map { it.toInt() }
        .sumOf { value ->
            (1 + countSpawned(times, value % 7))
        }
    println("Algo 2: $size")
}

private val cache = mutableMapOf<Int, Long>()

private fun countSpawned(totalTimes: Int, startTime: Int, indent: String = ""): Long =
    when {
        startTime > totalTimes -> 0
        else -> {
            var result = 0L
            for (i in startTime until totalTimes step 7) {
                val nextTime = i + 9
                val spawned = if (cache.containsKey(nextTime)) {
                    cache[nextTime]!!
                } else {
                    countSpawned(totalTimes, nextTime, "$indent    ").also { cache[nextTime] = it }
                }
                result += 1L + spawned
            }
            result
        }
    }
