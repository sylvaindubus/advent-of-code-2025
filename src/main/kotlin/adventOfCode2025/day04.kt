package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines

private const val MAX_ROLLS = 4

fun main() {
    val fileName = "day4.txt"
    val regex = Regex("""^(.+)$""")


    val grid: List<List<Boolean>> = parseResourceLines(fileName, regex) { match ->
        val (line) = match.destructured
        line.split("").filter { it.isNotEmpty() }.map { it == "@" }
    }

    fun countAdjacentRolls(lineIndex: Int, colIndex: Int): Int {
        var count = 0
        for (i in lineIndex - 1..lineIndex + 1) {
            for (j in colIndex - 1..colIndex + 1) {
                if (i == lineIndex && j == colIndex) continue
                if (grid.getOrNull(i)?.getOrNull(j) ?: false) {
                    count++
                }
            }
        }
        return count
    }

    var totalRemoved = 0

    grid.forEachIndexed { lineIndex, line ->
        line.forEachIndexed { colIndex, isRoll ->
            if (isRoll && countAdjacentRolls(lineIndex, colIndex) < MAX_ROLLS) {
                totalRemoved++
            }
        }
    }

    println(totalRemoved)
}
