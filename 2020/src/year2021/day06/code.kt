package year2021.day06

import util.readAllLines

fun main() {
    part1()
    part2()
}

private fun part1() {
    run(80, "input.txt")
}

private fun part2() {
    run(256, "input.txt")
}

private fun run(times: Int, file: String) {
    val size = readAllLines(file).first().split(",")
        .map { it.toInt() }
        .sumOf { value ->
            (1 + countSpawned(times, value % 7))
        }
    println(size)
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
