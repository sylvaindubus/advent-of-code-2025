package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines
import kotlin.math.abs

fun main() {
    val fileName = "day09.txt"

    class Tile(val x: Int, val y: Int) {
        override fun toString(): String {
            return "$x - $y"
        }
    }

    val regex = Regex("""^(\d+),(\d+)$""")
    val redTiles: List<Tile> = parseResourceLines(fileName, regex) { match ->
        val (x, y) = match.destructured
        Tile(x.toInt(), y.toInt())
    }

    var largestArea = 0L
    for (i in 0 until redTiles.size - 1) {
        for (j in i + 1 until redTiles.size) {
            val tile1 = redTiles[i]
            val tile2 = redTiles[j]
            val area = (abs(tile1.x - tile2.x) + 1).toLong() * (abs(tile1.y - tile2.y) + 1).toLong()
            if (area > largestArea) largestArea = area
        }
    }

    println(largestArea)
}

