package day12

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

private fun findSequentialBuses(filename: String) {
    val input = readAllLines(filename)
    val buses = input[1].split(",").map { if (it == "x") 0 else it.toInt() }
    val filteredBuses = buses.filter { it > 0 }
    val maxBus = checkNotNull(filteredBuses.max())
    var timestamp = - buses.indexOf(maxBus).toLong()

    println ("Buses: ${buses.joinToString()}")
    println ("Bus 0: ${buses[0]}")
    println ("Filtered: ${filteredBuses.joinToString()}")

    var found = false
    while (!found) {
        timestamp += maxBus
        found = true
//        println ("Test: ts = $timestamp")
        for (currentBusId in filteredBuses) {
            val currentBusIndex = buses.indexOf(currentBusId).toLong()
//            println ("    testing id = $currentBusId, index = $currentBusIndex, mod = ${(timestamp + currentBusIndex) % currentBusId}")
            if ((timestamp + currentBusIndex) % currentBusId != 0L) {
//                println ("    bail")
                found = false
                break
            }
        }
    }

//    sortedOtherBuses.forEach { currentBusId ->
//        println("Testing bus $currentBusId with increment $increment")
//        val otherBusIndex = buses.indexOf(currentBusId).toLong()
//        while (true) {
//            timestamp += increment
////            println ("    Testing timestamp $timestamp: mod = ${timestamp % id}, obi = $otherBusIndex")
//            if (timestamp % currentBusId == otherBusIndex) {
//                // make sure no buses land in any X row between this row and the 0th row of this set
//                val zero = timestamp - otherBusIndex
//                xIndicies.forEach { xIndex ->
//                    sortedOtherBuses.any { otherBusId -> (zero + xIndex) % otherBusId == buses.indexOf(otherBusId).toLong() } )
//                }
//                println("    Found bus $currentBusId with index $otherBusIndex at timestamp $timestamp, isAlsoInAnX = $isAlsoInAnX")
//
//                if (!isAlsoInAnX) {
//                    break
//                }
//            }
//        }
//        increment = currentBusId
//        println ("    New increment is $increment")
//    }

    println ("Sequential $filename: $timestamp")
}

fun main() {
//    findFirstBus("test.txt")
//    findFirstBus("input.txt")
    findSequentialBuses("test.txt")
    findSequentialBuses("input.txt")
}
