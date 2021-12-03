package year2020.day12

import util.readAllLines
import java.util.regex.Pattern

private fun printBits(value: ULong) {
    println(value.toString(radix = 2).padStart(Long.SIZE_BITS, '0'))
}

private fun processMask(mask: String): Pair<ULong, ULong> {
    var andWord = 0.toULong()
    var andBit = 1.toULong()
    var orWord = 0.toULong()
    var orBit = 1.toULong()
    mask.reversed().forEachIndexed { index, value ->
        when (value) {
            '0' -> andWord += andBit
            '1' -> orWord += orBit
            else -> {
            }
        }

        andBit = andBit shl 1
        orBit = orBit shl 1
    }

    andWord = andWord.inv()
    return Pair(andWord, orWord)
}

private fun processMask2(mask: String): List<Pair<ULong, ULong>> {
    var andWord = 0.toULong()
    var andBit = 1.toULong()
    var orWord = 0.toULong()
    var orBit = 1.toULong()
    val reversed = mask.reversed()
    val xIndexes = mutableListOf<Int>()
    reversed.forEachIndexed { index, value ->
        when (value) {
            '0' -> andWord += andBit
            '1' -> orWord += orBit
            'X' -> xIndexes.add(index)
            else -> {
            }
        }

        andBit = andBit shl 1
        orBit = orBit shl 1
    }

    val list = mutableListOf<Pair<ULong, ULong>>()
    list.add(Pair(andWord, orWord))
    xIndexes.forEach { index ->

    }

    andWord = andWord.inv()
    return listOf(Pair(andWord, orWord))
}

private val pattern = Pattern.compile("^mem.([0-9]+). = (.*)$")

private fun processInstruction(instruction: String): Pair<Long, ULong>? {
    pattern.matcher(instruction).let { matcher ->
        if (matcher.matches()) {
            val location = matcher.group(1).toLong()
            val value = matcher.group(2).toULong()
            return Pair(location, value)
        }
    }

    return null
}

private fun process(filename: String) {
    val input = readAllLines(filename)
    var andWord = 0.toULong().inv()
    var orWord = 0.toULong()
    val memory = mutableMapOf<Long, ULong>()

    input.forEach { line ->
        if (line.startsWith("mask = ")) {
            processMask(line.substring("mask = ".length)).let { maskValue ->
                andWord = maskValue.first
                orWord = maskValue.second
            }
        } else {
            processInstruction(line)?.let { (location, value) ->
                val newVal = (value and andWord) or orWord
                memory[location] = newVal
            }
        }
    }

    var sum = 0.toULong()
    memory.entries.forEach { (location, value) ->
        sum += value
    }
    println("Process $filename --> $sum")
}

private fun process2(filename: String) {
    val input = readAllLines(filename)
    var andWord = 0.toULong().inv()
    var orWord = 0.toULong()
    val memory = mutableMapOf<Long, ULong>()

    input.forEach { line ->
        if (line.startsWith("mask = ")) {
            processMask(line.substring("mask = ".length)).let { maskValue ->
                andWord = maskValue.first
                orWord = maskValue.second
            }
        } else {
            processInstruction(line)?.let { (location, value) ->
                val newVal = (value and andWord) or orWord
                memory[location] = newVal
            }
        }
    }

    var sum = 0.toULong()
    memory.entries.forEach { (location, value) ->
        sum += value
    }
    println("Process $filename --> $sum")
}

fun main() {
//    process("test.txt")
//    process("input.txt")
    process2("test.txt")
}
