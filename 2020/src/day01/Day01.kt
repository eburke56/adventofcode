package day01

import util.readAllLinesAsInt

fun day01a() {
    val input = readAllLinesAsInt("input.txt")

    val map = mutableSetOf<Int>()
    input.forEach {
        if (map.contains(2020 - it)) {
            println("ANSWER: $it * ${2020 - it} = ${it * (2020 - it)}")
            return
        }

        map.add(it)
    }
}

fun day01b() {
    val input = readAllLinesAsInt("input.txt")

    val map = mutableMapOf<Int, Pair<Int, Int>>()

    (0 until input.size - 1).forEach { a ->
        (a until input.size).forEach { b ->
            val i = input[a]
            val j = input[b]
            if (i + j < 2020) {
                println("ADD $i + $j = ${i + j}")
                map[i + j] = Pair(i, j)
            } else {
                println("SKIP $i + $j = ${i + j}")
            }
        }
    }

    input.forEach { item ->
        if (map.contains(2020 - item)) {
            val (i, j) = checkNotNull(map[2020 - item])
            println("ANSWER: $item * $i * $j = ${item * i * j}")
            return
        }
    }
}

fun main() {
    day01b()
}
