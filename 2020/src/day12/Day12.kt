package day12

import util.readAllLines
import java.lang.IllegalStateException
import kotlin.math.abs

data class Position(
    val facing: Char,
    val x: Int,
    val y: Int
)

private fun readInput(filename: String): List<Pair<Char, Int>> {
    val input = readAllLines(filename)
    return input.map { Pair(it[0], it.substring(1).toInt()) }
}

private fun message(message: String) {
    if (verbose) {
        println(message)
    }
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
        message("    new direction = $this")
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
    message("Proceed: pos = $currentPosition, command = $command, distance = $distance")

    val getNextDistance = { command: Char, distance: Int ->
        when (command) {
            'N' -> {
                nextY += distance
                message("    move N, new Y = $nextY")
            }
            'E' -> {
                nextX += distance
                message("    move E, new X = $nextX")
            }
            'S' -> {
                nextY -= distance
                message("    move S, new Y = $nextY")
            }
            'W' -> {
                nextX -= distance
                message("    move W, new X = $nextX")
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
    var currentPosition = Position('E', 0, 0)

    val input = readInput(filename)
    input.forEach { (command, distance) ->
        currentPosition = proceed(currentPosition, command, distance)
    }

    println("Manhattan $filename = ${abs(currentPosition.x) + abs(currentPosition.y)}")
}

private fun moveWithWaypoint(filename: String) {
    var waypointX = 10
    var waypointY = 1
    var currentPosition = Position('E', 0, 0)

    val input = readInput(filename)
    input.forEach { (command, distance) ->
        message("WAY: pos = $currentPosition, way = ($waypointX, $waypointY), command = $command, distance = $distance")
        when (command) {
            'N' -> {
                waypointY += distance
                message("    move waypoint N, new waypointY = $waypointY")
            }
            'E' -> {
                waypointX += distance
                message("    move waypoint E, new waypointX = $waypointX")
            }
            'S' -> {
                waypointY -= distance
                message("    move waypoint S, new waypointY = $waypointY")
            }
            'W' -> {
                waypointX -= distance
                message("    move waypoint W, new waypointX = $waypointX")
            }
            'F' -> {
                message("    move by ${distance * waypointX}, ${distance * waypointY}")
                currentPosition = proceed(currentPosition, 'E', distance * waypointX)
                currentPosition = proceed(currentPosition, 'N', distance * waypointY)
            }
            'R', 'L' -> {
                message("    rotate $command ${(distance / 2) % 4} times")
                for (i in 1..(distance / 2) % 4) {
                    val tmp = waypointX
                    waypointX = if (command == 'R') waypointY else -waypointY
                    waypointY = if (command == 'R') -tmp else tmp
                    message("    rotated to $waypointX, $waypointY")
                }
            }
            else -> throw IllegalStateException()
        }
    }

    println("Waypoint $filename = ${abs(currentPosition.x) + abs(currentPosition.y)}")
}

private val verbose = false

fun main() {
    manhattan("test.txt")
    manhattan("input.txt")
    moveWithWaypoint("test.txt")
    moveWithWaypoint("input.txt")
}
