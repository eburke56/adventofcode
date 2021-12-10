package year2021.day10

import util.readAllLines
import java.util.*

fun main() {
    part1()
    part2()
}

private val OPENERS = setOf('(', '{', '[', '<')
private val CLOSERS = setOf(')', '}', ']', '>')
private val OPENTOCLOSE = mapOf(
    '(' to ')',
    '{' to '}',
    '[' to ']',
    '<' to '>'
)

private fun part1() {
    val SCORES = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )

    val sum = readAllLines("input.txt").sumOf { line ->
        val stack = Stack<Char>()
        var score = 0

        for (it in line) {
            when {
                it in OPENERS-> stack.push(it)
                it in CLOSERS -> if (!checkClose(stack, it)) { score = SCORES[it]!!; break }
                else -> error("Illegal character in processing")
            }
        }

        score
    }

    println(sum)
}

private fun part2() {
    val SCORES = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    val scores = mutableListOf<Long>()

    readAllLines("input.txt").forEach { line ->
        var corrupt = false
        val stack = Stack<Char>()

        for (it in line) {
            when (it) {
                in OPENERS -> stack.push(it)
                in CLOSERS -> if (!checkClose(stack, it)) { corrupt = true; break }
                else -> error("Illegal character in processing")
            }
        }

        if (!corrupt) {
            var score = 0L
            while (stack.isNotEmpty()) {
                score = 5 * score + SCORES[OPENTOCLOSE[stack.pop()]]!!
            }
            scores.add(score)
        }
    }

    scores.sorted().also {
        println(it[it.size / 2])
    }
}

private fun checkClose(stack: Stack<Char>, it: Char): Boolean =
    when (it) {
        in OPENERS -> true
        in CLOSERS -> {
            if (it == OPENTOCLOSE[stack.lastElement()]) {
                stack.pop()
                true
            } else {
                false
            }
        }
        else -> error("Illegal character in checkClose")
    }
