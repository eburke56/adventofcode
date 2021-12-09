package year2021.day08

import util.readAllLines

fun main() {
    part1()
    part2()
}

val DIGITS = mutableMapOf<Int, String>().apply {
    put(1, "cf")
    put(4, "bcdf")
    put(7, "acf")
    put(8, "abcdefg")

    put(2, "acdeg") // common with 1: c, 4: cd, 7: ac
    put(3, "acdfg") // common with 1: cf, 4: cdf, 7: acf
    put(5, "abdfg") // common with 1: f, 4: bdf, 7: af

    put(0, "abcefg") // common with 1: cf, 4: bcf, 7: acf
    put(6, "abdefg") // common with 1: f, 4: bdf, 7: af
    put(9, "abcdfg") // common with 1: cf, 4: bcdf, 7: acf
}

private fun part1() {
    val outputs = mutableListOf<List<Set<Char>>>()
    readAllLines("input.txt").map { line ->
        val (wire, digits) = line.split(" | ")
            .map { part -> part.split(" ").map { word -> word.toSortedSet() } }
        outputs.add(digits)
    }

    val num = outputs.sumOf { digits ->
        digits.count { digit ->
            lengthMatches(digit, 1) || lengthMatches(digit, 4) || lengthMatches(digit, 7) || lengthMatches(digit, 8)
        }
    }

    println(num)
}

private fun part2() {
    val unique = setOf(1, 4, 7, 8)

    val sum = readAllLines("input.txt").sumOf { line ->
        val (wire, digits) = line.split(" | ")
            .map { part -> part.split(" ").map { word -> word.toSortedSet() } }
        val mapDigitStringToNumber = mutableMapOf<Set<Char>, Int>()
        val mapNumberToDigitString = mutableMapOf<Int, Set<Char>>()

        val matchUnique = { digit: Set<Char> ->
            unique.firstOrNull { lengthMatches(digit, it) }
        }

        val mapUniqueStrings = { digit: Set<Char> ->
            val match = matchUnique(digit)
            when {
                match == null -> false
                mapDigitStringToNumber.contains(digit) -> true
                else -> {
                    mapDigitStringToNumber[digit] = match
                    mapNumberToDigitString[match] = digit
                    true
                }
            }
        }

        val all = wire + digits
        val ambiguousDigits = mutableListOf<Set<Char>>()

        all.forEach {
            if (!mapUniqueStrings(it)) {
                ambiguousDigits.add(it)
            }
        }

        for (digit in ambiguousDigits) {
            val digitLength = digit.size
            val common = unique.map {
                digit.intersect(mapNumberToDigitString[it]!!)
            }

            val numInCommonWith1 = common[0].size
            val numInCommonWith4 = common[1].size

            val number = when {
                numInCommonWith1 == 1 && digitLength == 6 -> 6
                numInCommonWith1 == 1 && numInCommonWith4 == 2 && digitLength == 5 -> 2
                numInCommonWith1 == 1 && numInCommonWith4 == 3 && digitLength == 5 -> 5
                numInCommonWith1 == 2 && digitLength == 5 -> 3
                numInCommonWith1 == 2 && numInCommonWith4 == 3 && digitLength == 6 -> 0
                numInCommonWith1 == 2 && numInCommonWith4 == 4 && digitLength == 6 -> 9
                else -> error("Unknown number")
            }
            mapDigitStringToNumber[digit] = number
            mapNumberToDigitString[number] = digit

            if (mapNumberToDigitString.size == 10) {
                break
            }
        }

        digits.map { mapDigitStringToNumber[it]!! }.joinToString("").toInt()
    }

    println(sum)
}

private fun lengthMatches(value: Set<Char>, testValue: Int) = value.size == DIGITS[testValue]!!.length