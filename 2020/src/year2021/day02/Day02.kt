package year2021.day02

import util.readAllLines
import util.readAllLinesAsInt

fun main() {
    part1()
    part2()
}

private fun part1() {
    var position = 0
    var depth = 0
    readAllLines("input.txt").forEach { line ->
        line.split(" ").let {
            val arg = it[1].toInt()
            when(it[0]) {
                "forward" -> position += arg
                "up" -> depth -= arg
                "down" -> depth += arg
            }
        }
    }
    println(position * depth)
}

private fun part2() {
    var position = 0
    var depth = 0
    var aim = 0
    readAllLines("input.txt").forEach { line ->
        line.split(" ").let {
            val arg = it[1].toInt()
            when(it[0]) {
                "forward" -> {
                    position += arg
                    depth += aim * arg
                }
                "up" -> aim -= arg
                "down" -> aim += arg
            }
        }
    }
    println(position * depth)
}