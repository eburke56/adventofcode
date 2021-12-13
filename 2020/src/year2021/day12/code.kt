package year2021.day12

import util.readAllLines

enum class Size {
    SMALL, LARGE;
}

data class Node(val name: String) {
    val size = if (name[0].isUpperCase()) Size.LARGE else Size.SMALL
    val paths = mutableMapOf<Node, MutableSet<MutableList<Node>>>()
    val isVisitable: Boolean
        get() = (size == Size.LARGE || numVisits == 0 || (isSpecial && numVisits < 2))
    var isSpecial = false
    var numVisits = 0

    override fun toString() = "Node '${name}'"
    override fun hashCode() = name.hashCode()
    override fun equals(other: Any?) = (other is Node && other.name == name)
}

class CaveMap: MutableMap<Node, MutableSet<Node>> by mutableMapOf() {
    fun add(node1Name: String, node2Name: String) {
        val node1 = node(node1Name) ?: Node(node1Name)
        val node2 = node(node2Name) ?: Node(node2Name)
        put(node1, (get(node1) ?: mutableSetOf()).also { if (!it.contains(node2)) it.add(node2) })
        put(node2, (get(node2) ?: mutableSetOf()).also { if (!it.contains(node1)) it.add(node1) })
    }

    val smallCaves: Set<Node>
        get() = keys.filter { it.size == Size.SMALL && it.name != START && it.name != END }.toSet()

    fun makeSpecial(node: Node) = keys.forEach { it.isSpecial = it == node }

    fun reset() {
        values.forEach {
            it.forEach {
                it.numVisits = 0
            }
        }
    }

    fun findPaths(start: String, end: String): Set<MutableList<Node>> =
        findPaths(checkNotNull(node(start)), checkNotNull(node(end)))

    private fun node(name: String) = keys.firstOrNull { it.name == name }

    private fun findPaths(start: Node, end: Node, indent: String = ""): Set<MutableList<Node>> {
        val nextIndent = "$indent  "
        val result = mutableSetOf<MutableList<Node>>()

        if (!start.isVisitable) {
//            println("${indent}--> $start is not visitable")
        }

        start.numVisits++

        when {
            start == end -> {
                result.add(mutableListOf())
            }
            else -> {
                val connections = get(start)
                val alreadyVisited = mutableMapOf<Node, Int>()
                caves.values.forEach { it.forEach { alreadyVisited[it] = it.numVisits } }
                checkNotNull(connections)
                    .forEach { node ->
                        if (node.isVisitable) {
                            findPaths(node, end, nextIndent)
                                .onEach {
                                    it.add(0, node)
                                    result.add(it)
                                }

                            caves.reset()
                            alreadyVisited.forEach { (node, numVisits) -> node.numVisits = numVisits }
                        }
                    }
            }
        }

        return result
    }
}

private const val START = "start"
private const val END = "end"

val caves = CaveMap()

fun main() {
    readAllLines("test1.txt").map { line ->
        line.split('-').also { parts ->
            caves.add(parts[0], parts[1])
        }
    }

    part1()
    part2()
}

private fun part1() {
    val paths = caves.findPaths(START, END)
    println(paths.size)
}

private fun part2() {
    val paths = caves.smallCaves.flatMap {
        caves.makeSpecial(it)
        caves.reset()
        caves.findPaths(START, END)
    }

    println(paths.size)
}
