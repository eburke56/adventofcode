package year2021.day01

import util.readAllLinesAsInt

fun main() {
    day01a()
    day01b()
}

private fun day01a() {
    val input = readAllLinesAsInt("input.txt")

    var last = Int.MAX_VALUE
    val increases = input.count { depth ->
        (depth > last).also {
            last = depth
        }
    }
    println("Number of increases: $increases")
}

private fun day01b() {
    val input = readAllLinesAsInt("input.txt")

    var last0 = Int.MIN_VALUE
    var last1 = Int.MIN_VALUE
    var last2 = Int.MIN_VALUE
    var lastSum = 0
    var increases = 0
    input.forEach { depth ->
        when {
            last0 < 0 -> last0 = depth
            last1 < 0 -> last1 = depth
            last2 < 0 -> {
                last2 = depth
                lastSum = last0 + last1 + last2
            }
            else -> {
                val currentSum = last1 + last2 + depth
                if (currentSum > lastSum) {
                    increases++
                }
                last0 = last1
                last1 = last2
                last2 = depth
                lastSum = currentSum
            }
        }
    }

    println("Number of window increases: $increases")
}
