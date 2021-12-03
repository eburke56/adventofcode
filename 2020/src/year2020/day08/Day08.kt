package year2020.day08

import util.readAllLines

private data class Instruction(
    val command: String,
    val arg: Int,
    var visited: Boolean = false
)

private fun loadProgram(filename: String): List<Instruction> =
    readAllLines(filename).map { line ->
        val (command, arg) = line.split(" ")
        val argVal = (if (arg[0] == '-') -1 else 1) * arg.substring(1).toInt()
        Instruction(command, argVal)
    }

private fun run(program: List<Instruction>, jumpToNopIndex: Int = -1): Pair<Boolean, Int> {
    var ip = 0
    var acc = 0

    program.forEach { it.visited = false }

    while (ip < program.size) {
        val instruction = program[ip]
        if (instruction.visited) {
            return Pair(false, acc)
        }

        var advance = 1
        val command = if (ip == jumpToNopIndex && instruction.command == "jmp") "nop" else instruction.command
        when (command) {
            "acc" -> acc += instruction.arg
            "jmp" -> advance = instruction.arg
        }

        instruction.visited = true
        ip += advance
    }

    return Pair(true, acc)
}

private fun part1(filename: String): Int {
    return run(loadProgram(filename)).second
}

private fun part2(filename: String): Int {
    val program = loadProgram(filename)

    program.mapIndexedNotNull { index, instruction ->
        if (instruction.command == "jmp") index else null
    }.forEach { jumpTestIndex ->
        run(program, jumpTestIndex).let { (terminatedNormally, accumulator) ->
            if (terminatedNormally) {
                return accumulator
            }
        }
    }

    return -1
}

fun main() {
    println("Test part 1 = ${part1("test.txt")}")
    println("Real part 1 = ${part1("input.txt")}")
    println("Test part 2 = ${part2("test.txt")}")
    println("Real part 2 = ${part2("input.txt")}")
}
