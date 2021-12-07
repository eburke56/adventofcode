package year2021.day07

import util.readAllLines
import kotlin.math.abs
import kotlin.math.min

fun main() {
    run(false)
    run(true)
}

private fun run(incrementCost: Boolean) {
    val input = readAllLines("input.txt").first().split(",").map { it.toInt() }.sorted()
    var result = Int.MAX_VALUE
    (input.first()..input.last()).forEach {
        result = min(result, calcFuel(input, it, incrementCost))
    }
    println(result)
}

private fun calcFuel(list: List<Int>, testValue: Int, incrementCost: Boolean): Int {
    if (list.isEmpty()) {
        return 0
    }

    var result: Int = Int.MAX_VALUE
    val size = list.size
    val mid = size / 2

    if (size == 1) {
        result = list.sumOf {
            val distance = abs(it - testValue)
            if (incrementCost) (distance * (distance + 1)) / 2 else distance
        }
    } else {
        val left = calcFuel(list.subList(0, mid), testValue, incrementCost)
        val right = calcFuel(list.subList(mid, size), testValue, incrementCost)
        result = min(result, left + right)
    }

    return result
}