package day12

import util.readAllLines
import kotlin.math.min

private fun readInput(filename: String): Pair<Int, List<Int>> {
    val input = readAllLines(filename)
    return Pair(
        input[0].toInt(),
        input[1].split(",").mapNotNull { it.toIntOrNull() }
    )
}

private fun findFirstBus(filename: String) {
    val (timestamp, buses) = readInput(filename)
    var bestId = -1
    var bestArrival = Int.MAX_VALUE
    buses.forEach { id ->
        val nextArrival = timestamp + id - (timestamp % id)
        println ("Next arrival for bus $id --> $nextArrival")
        if (nextArrival < bestArrival) {
            bestId = id
            bestArrival = nextArrival
        }
    }
    println ("First bus $filename: ts = $timestamp, id = $bestId, nextArrival = $bestArrival --> ${bestId * (bestArrival - timestamp)}")
}

fun main() {
    findFirstBus("test.txt")
    findFirstBus("input.txt")
}
