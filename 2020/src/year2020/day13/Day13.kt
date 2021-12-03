package year2020.day12

import util.readAllLines

private fun findFirstBus(filename: String) {
    val input = readAllLines(filename)
    val timestamp = input[0].toInt()
    val buses = input[1].split(",").mapNotNull { it.toIntOrNull() }
    var bestId = -1
    var bestArrival = Int.MAX_VALUE
    buses
        .forEach { id ->
            val nextArrival = timestamp + id - (timestamp % id)
            println("Next arrival for bus $id --> $nextArrival")
            if (nextArrival < bestArrival) {
                bestId = id
                bestArrival = nextArrival
            }
        }
    println("First bus $filename: ts = $timestamp, id = $bestId, nextArrival = $bestArrival --> ${bestId * (bestArrival - timestamp)}")
}

private fun findSequentialBuses(
    start: Long,
    increment: Int,
    allBuses: List<Int>,
    filteredBuses: List<Int>
): Long {
    if (filteredBuses.isEmpty()) {
        return start
    }

    var timestamp = start
    var result = 0L

    while (result == 0L) {
//        println("Testing $timestamp with buses ${filteredBuses.joinToString()}")
        val currentBusId = filteredBuses[0]
        val currentBusIndex = allBuses.indexOf(currentBusId).toLong()
//            println("    testing id = $currentBusId, index = $currentBusIndex, mod = ${(timestamp + currentBusIndex) % currentBusId}")
        val mod = (timestamp + currentBusIndex) % currentBusId
        if (mod != 0L) {
//                println("    bail")
            result = 0L
            break
        } else {
//                println("    found, recurse with increment ${filteredBuses[0]}, list [${filteredBuses.subList(1, filteredBuses.size).joinToString()}]")
            result = findSequentialBuses(timestamp, increment, allBuses, filteredBuses.subList(1, filteredBuses.size))
        }

        timestamp += increment
    }

//    println("    exit with ts $result")
    return result
}

private fun findSequentialBuses(filename: String) {
    val buses = readAllLines(filename)[1].split(",").map { if (it == "x") 0 else it.toInt() }
    val filteredBuses = buses.filter { it > 0 }.sortedDescending()
    val maxBus = checkNotNull(filteredBuses.maxOrNull())
    val timestamp = findSequentialBuses((maxBus - buses.indexOf(maxBus)).toLong(), maxBus, buses, filteredBuses)
    println("Sequential $filename: $timestamp")
}

fun main() {
//    findFirstBus("test.txt")
//    findFirstBus("input.txt")
    findSequentialBuses("test.txt")
    findSequentialBuses("input.txt")
}
