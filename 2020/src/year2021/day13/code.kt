package year2021.day13

import util.readAllLines
import kotlin.math.max

data class Dot(val x: Int, val y: Int)
data class Fold(val x: Int, val y: Int)

fun main() {
    val dots = mutableSetOf<Dot>()
    val folds = mutableListOf<Fold>()

    readAllLines("input.txt").forEach { line ->
        if (line.startsWith("fold")) {
            line.removePrefix("fold along ").split("=").also { (axis, value) ->
                val v = value.toInt()
                val fold = when (axis) {
                    "x" -> Fold(v, 0)
                    "y" -> Fold(0, v)
                    else -> error("Illegal axis $axis")
                }
                folds.add(fold)
            }
        } else if (line.isNotEmpty()) {
            line.split(",").let { (x, y) -> dots.add(Dot(x.toInt(), y.toInt())) }
        }
    }

    folds.forEach { fold ->
        val removed = mutableSetOf<Dot>()
        val added = mutableSetOf<Dot>()
        if (fold.x > 0) {
            dots.filter { it.x > fold.x }.forEach {
                removed.add(it)
                added.add(Dot(fold.x + fold.x - it.x, it.y))
            }
        } else if (fold.y > 0) {
            dots.filter { it.y > fold.y }.forEach {
                removed.add(it)
                added.add(Dot(it.x, fold.y + fold.y - it.y))
            }
        }
        dots.apply {
            removeAll(removed)
            addAll(added)
        }
        println(dots.size)
    }

    var maxX = Int.MIN_VALUE
    var maxY = Int.MIN_VALUE
    dots.forEach {
        maxX = max(maxX, it.x)
        maxY = max(maxY, it.y)
    }
    (0..maxY).forEach { y ->
        (0..maxX).forEach { x ->
            print(if(dots.contains(Dot(x, y))) "#" else ".")
        }
        println()
    }
}
