package day01

import util.readAllLines
import util.readAllLinesAsInt

fun day02a() {
    val input = readAllLines("input.txt")
    var validCount = 0

    input.forEach { line ->
        val parts = line.split(" ")
        val range = parts[0].split("-").let {
            IntRange(it[0].toInt(), it[1].toInt())
        }
        val required = parts[1][0]
        val password = parts[2]
        val count = password.count { it == required }
        if (range.contains(count)) {
            validCount++
        }
    }

    println(validCount)
}

fun day02b() {
    val input = readAllLines("input.txt")
    var validCount = 0

    input.forEach { line ->
        val parts = line.split(" ")
        val range = parts[0].split("-").let {
            IntRange(it[0].toInt() - 1, it[1].toInt() - 1)
        }
        val required = parts[1][0]
        val password = parts[2]
        val valid = (password[range.first] == required) xor (password[range.last] == required)
        if (valid) {
            validCount++
        }
    }

    println(validCount)
}

fun main() {
    day02b()
}
