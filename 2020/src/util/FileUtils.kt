package util

import java.io.File

fun readAllLines(filename: String) =
    File(filename).readLines()

fun readAllLinesAsInt(filename: String): List<Int> =
    readAllLines(filename).map { it.toInt() }

fun readAllLinesAsLong(filename: String): List<Long> =
    readAllLines(filename).map { it.toLong() }
