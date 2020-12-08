package day05

import util.readAllLines

private fun findHighestSeat(seats: Set<Int>): Int {
    return seats.max() ?: -1
}

private fun findMySeat(seats: Set<Int>): Int {
    for (i in 0 until 8*128) {
        if (!seats.contains(i) && seats.contains(i - 1) && seats.contains(i + 1)) {
            return i
        }
    }

    return -1
}

private fun getSeats(filename: String): Set<Int> =
    readAllLines(filename).map { line ->
        var rowRange = 0..127
        var seatRange = 0..7

        line.forEach {
            when (it) {
                'F' -> rowRange = rowRange.first..(rowRange.last - rowRange.count() / 2)
                'B' -> rowRange = (rowRange.first + rowRange.count() / 2)..rowRange.last
                'L' -> seatRange = seatRange.first..(seatRange.last - seatRange.count() / 2)
                'R' -> seatRange = (seatRange.first + seatRange.count() / 2)..seatRange.last
            }
        }

        (rowRange.first * 8 + seatRange.first).also {
            println("$line -> $it")
        }
    }.toSet()

fun main() {
    val seats = getSeats("input.txt")

    println("Highest: ${findHighestSeat(seats)}")
    println("My: ${findMySeat(seats)}")
}
