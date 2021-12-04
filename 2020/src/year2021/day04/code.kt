package year2021.day04

import util.readAllLines

private data class Node(val value: Int, var marked: Boolean = false)

private class Board(values: List<String>) {
    private val nodes = mutableMapOf<Int, Node>()
    private val rows =
        mutableListOf<MutableList<Node>>().apply {
            repeat(5) {
                add(mutableListOf<Node>().apply {
                    repeat(5) {
                        add(Node(0))
                    }
                })
            }
        }

    private val cols =
        mutableListOf<MutableList<Node>>().apply {
            repeat(5) {
                add(mutableListOf<Node>().apply {
                    repeat(5) {
                        add(Node(0))
                    }
                })
            }
        }

    init {
        values.mapIndexed { rowIndex, row ->
            row.trim().split(Regex("\\s+")).mapIndexed { colIndex, stringValue ->
                val value = stringValue.toInt()
                val node = Node(value)
                nodes[value] = node
                rows[rowIndex][colIndex] = node
                cols[colIndex][rowIndex] = node
            }
        }
    }

    val score: Int
        get() = nodes.values.sumOf {
            if (it.marked)
                0
            else
                it.value
        }

    val isWinner: Boolean
        get() = rows.any { row -> row.all { it.marked } } || cols.any { col -> col.all { it.marked } }

    fun mark(value: Int) {
        nodes[value]?.marked = true
    }
}

fun main() {
    part1()
    part2()
}

private fun part1() {
    val input = readAllLines("input.txt")
    val boards = createBoard(input)

    input[0].split(",").map { it.toInt() }.forEach { called ->
        boards.forEach {
            it.mark(called)
            if (it.isWinner) {
                println(it.score * called)
                return
            }
        }
    }

    error("No winner found")
}

private fun part2() {
    val input = readAllLines("input.txt")
    val boards = createBoard(input)

    input[0].split(",").map { it.toInt() }.forEach { called ->
        var size = boards.size
        boards.forEach {
            it.mark(called)
        }
        for (i in 0 until size) {
            val board = boards[i]
            if (board.isWinner) {
                if(size == 1) {
                    println(board.score * called)
                    return
                } else {
                    boards.remove(board)
                    size -= 1
                }
            }
        }
    }

    error("No last winner found!")
}

private fun createBoard(input: List<String>): MutableList<Board> {
    val boards = mutableListOf<Board>()
    for (i in 2 until input.size step 6) {
        boards.add(Board(input.subList(i, i + 5)))
    }
    return boards
}