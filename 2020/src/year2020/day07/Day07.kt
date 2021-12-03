package year2020.day07

import util.readAllLines
import java.util.regex.Pattern

private fun getRules(filename: String): Map<String, Map<String, Int>> {
    val input = readAllLines(filename)
    val contentsPattern = Pattern.compile("([0-9])+ (.*)? bag.*")
    val map = mutableMapOf<String, MutableMap<String, Int>>()
    input.forEach { line ->
        val parts = line.split(" contain ")

        val target = parts[0].replace(" bags", "")
        if (!map.containsKey(target)) {
            map[target] = mutableMapOf()
        }

        val targetMap = checkNotNull(map[target])
        val contents = parts[1].split(", ")
        contents.forEach {
            val matcher = contentsPattern.matcher(it)
            val matches = matcher.matches()
            if (matches) {
                val number = matcher.group(1).toInt()
                val type = matcher.group(2)
                targetMap[type] = number
            }
        }
    }

    return map
}

private fun findBagType(parentMap: Map<String, Map<String, Int>>, parentType: String, type: String) : Boolean {
    parentMap[parentType]?.entries?.forEach { entry ->
        if (entry.key == type) {
            return true
        } else {
            if (findBagType(parentMap, entry.key, type)) {
                return true
            }
        }
    }

    return false
}

private fun recursiveCountBagType(total: Int, parentMap: Map<String, Map<String, Int>>, type: String) : Int {
    var nextTotal = total
    parentMap[type]?.entries?.forEach { entry ->
        val childTotal = recursiveCountBagType(total, parentMap, entry.key)
        nextTotal += entry.value * (1 + childTotal)
    }

    return nextTotal
}

fun main() {
    val map = getRules("input.txt")
    val count = map.entries.count {
        findBagType(map, it.key, "shiny gold")
    }
    println(count)
    val total = recursiveCountBagType(0, map, "shiny gold")
    println(total)
}
