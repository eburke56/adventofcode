package year2020.day04

import util.readAllLines
import java.util.regex.Pattern

private data class Passport(
    val byr: String?,
    val iyr: String?,
    val eyr: String?,
    val hgt: String?,
    val hcl: String?,
    val ecl: String?,
    val pid: String?,
    val cid: String?) {

    constructor(map: Map<String, String>) :
        this(
            map["byr"],
            map["iyr"],
            map["eyr"],
            map["hgt"],
            map["hcl"],
            map["ecl"],
            map["pid"],
            map["cid"]
        )

    val isValidFast: Boolean
        get() =
            !byr.isNullOrBlank()
                && !iyr.isNullOrBlank()
                && !eyr.isNullOrBlank()
                && !hgt.isNullOrBlank()
                && !hcl.isNullOrBlank()
                && !ecl.isNullOrBlank()
                && !pid.isNullOrBlank()

    val isValidHeight = when (hgt) {
        null -> false
        else -> if (hgt.endsWith("cm")) {
            hgt.substringBefore("cm").toIntOrNull()?.let { it in 150..193 } ?: false
        } else if (hgt.endsWith("in")) {
            hgt.substringBefore("in").toIntOrNull()?.let { it in 59..76 } ?: false
        } else {
            false
        }
    }

    val pattern: Pattern = Pattern.compile("#[0-9a-f]{6}")
    val isValid: Boolean
        get() =
            isValidFast
                && byr?.toIntOrNull()?.let { it in 1920..2002 } ?: false
                && iyr?.toIntOrNull()?.let { it in 2010..2020 } ?: false
                && eyr?.toIntOrNull()?.let { it in 2020..2030 } ?: false
                && isValidHeight
                && checkNotNull(hcl).let { pattern.matcher(it).matches() }
                && checkNotNull(ecl).let { setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(it) }
                && checkNotNull(pid).let { it.length == 9 && it.toIntOrNull() != null  }
}

private fun findValidPassportsFast(filename: String): Int {
    return readPassports(filename).count { it.isValidFast }
}

private fun findValidPassports(filename: String): Int {
    return readPassports(filename).count { it.isValid }
}

private fun readPassports(filename: String): MutableList<Passport> {
    val input = readAllLines(filename)
    val list = mutableListOf<Passport>()

    val currentMap = mutableMapOf<String, String>()
    input.forEach { line ->
        if (line.isBlank()) {
            list.add(Passport(currentMap))
            currentMap.clear()
        } else {
            line.split(" ", "\t").forEach { part ->
                part.split(":").let {
                    currentMap[it[0]] = it[1]
                }
            }
        }
    }

    if (currentMap.isNotEmpty()) {
        list.add(Passport(currentMap))
    }
    return list
}

fun main() {
    val validFast = findValidPassportsFast("input.txt")
    println(validFast)

    val invalid = findValidPassports("test-invalid.txt")
    println(invalid)

    val validtest = findValidPassports("test-valid.txt")
    println(validtest)

    val valid = findValidPassports("input.txt")
    println(valid)
}
