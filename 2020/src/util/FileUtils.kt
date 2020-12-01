package util

import java.io.File

fun readAllLines(filename: String) =
    File(filename).readLines()

fun <T> readAllLinesAs(filename: String, transform: (String) -> T): List<T> =
    readAllLines(filename).map(transform)

fun readAllLinesAsInt(filename: String): List<Int> =
    readAllLinesAs(filename) { it.toInt() }
