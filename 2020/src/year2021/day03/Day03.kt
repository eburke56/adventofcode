package year2021.day03

import util.readAllLines

fun main() {
    //part1()
    part2()
}

private fun part1() {
    val input = readAllLines("test.txt")
    val length = input[0].length
    val zeroes = mutableListOf<Int>().also { list ->
        for(i in 0 until length){
            list.add(0)
        }
    }

    val ones = mutableListOf<Int>().also { list ->
        for(i in 0 until length){
            list.add(0)
        }
    }

    input.forEach{ line ->
        line.forEachIndexed { index, digit ->
            when (digit) {
                '0' -> zeroes[index]++
                '1' -> ones[index]++
            }
        }
    }

    var gamma = 0
    var epsilon = 0
    for (i in 0 until length) {
        gamma = gamma shl 1
        epsilon = epsilon shl 1
        if (ones[i] > zeroes[i]) {
            gamma = gamma or 1
        } else {
            epsilon = epsilon or 1
        }
    }

    println(gamma * epsilon)
}

private fun part2() {
    val func = { listInput: List<String>, block: (MutableList<MutableList<String>>, MutableList<MutableList<String>>, Int) -> MutableList<String> ->
        var list = mutableListOf<String>().apply { addAll(listInput) }
        var currentBit = 0
        while (list.size > 1) {
            val length = list[0].length
            val zeroes = mutableListOf<MutableList<String>>().also {
                for (i in 0 until length) {
                    it.add(mutableListOf())
                }
            }

            val ones = mutableListOf<MutableList<String>>().also {
                for (i in 0 until length) {
                    it.add(mutableListOf())
                }
            }

            list.forEach { line ->
                line.forEachIndexed { index, digit ->
                    when (digit) {
                        '0' -> zeroes[index].add(line)
                        '1' -> ones[index].add(line)
                    }
                }
            }

            list = block(zeroes, ones, currentBit)
            currentBit++
        }
        list.first()
    }

    val input = readAllLines("input.txt")

    val listO2 = func(input) { zeroes, ones, i ->
        if (ones[i].size >= zeroes[i].size) ones[i] else zeroes[i]
    }
    val valO2 = listO2.toIntBinary()

    val listC02 = func(input) { zeroes, ones, i ->
        if (ones[i].size < zeroes[i].size) ones[i] else zeroes[i]
    }
    val valC02 = listC02.toIntBinary()

    println(valO2 * valC02)
}

private fun String.toIntBinary(): Int {
    var value = 0
    for (i in 0 until length) {
        value = value shl 1
        when (this[i]) {
            '1' -> value = value or 1
        }
    }
    return value
}