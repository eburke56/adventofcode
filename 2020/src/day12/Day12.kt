package day12

import util.readAllLines
import java.lang.IllegalStateException
import kotlin.math.abs

data class Waypoint(
    val x: Int,
    val y: Int
)

data class Position(
    val facing: Char,
    val x: Int,
    val y: Int
)

private fun readInput(filename: String): List<Pair<Char, Int>> {
    val input = readAllLines(filename)
    return input.map { Pair(it[0], it.substring(1).toInt()) }
}

private fun makeTurn(currentDirection: Char, turnDirection: Char, degrees: Int): Char {
    val loop = listOf('N', 'E', 'S', 'W')
    val index = loop.indexOf(currentDirection)
    var newIndex = index
    for (i in 1..(degrees / 2) % 4) {
        newIndex += if (turnDirection == 'L') -1 else 1
        if (newIndex < 0) newIndex += 4
    }
    return loop.get(newIndex % 4).apply {
        println ("    new direction = $this")
    }
}

private fun proceed(
    currentPosition: Position,
    command: Char,
    distance: Int
): Position {
    var nextFacing = currentPosition.facing
    var nextX = currentPosition.x
    var nextY = currentPosition.y
    println ("Pos = $currentPosition, command = $command, distance = $distance")

    val getNextDistance = { command: Char, distance: Int ->
        when (command) {
            'N' -> {
                nextY += distance
                println ("    move N, new Y = $nextY")
            }
            'E' -> {
                nextX += distance
                println ("    move E, new X = $nextX")
            }
            'S' -> {
                nextY -= distance
                println ("    move S, new Y = $nextY")
            }
            'W' -> {
                nextX -= distance
                println ("    move W, new X = $nextX")
            }
            else -> throw IllegalStateException()
        }
    }

    when (command) {
        'N', 'E', 'S', 'W' -> getNextDistance(command, distance)
        'F' -> getNextDistance(currentPosition.facing, distance)
        'R', 'L' -> nextFacing = makeTurn(currentPosition.facing, command, distance)
        else -> throw IllegalStateException()
    }

    return Position(nextFacing, nextX, nextY)
}

private fun manhattan(filename: String) {
    val input = readInput(filename)
    var currentPosition = Position('E', 0, 0)

    input.forEach { (command, distance) ->
        currentPosition = proceed(currentPosition, command, distance)
    }

    println("Manhattan $filename = ${abs(currentPosition.x) + abs(currentPosition.y)}")
}

fun main() {
//    manhattan("test.txt")
    manhattan("input.txt")
}
