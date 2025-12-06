package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines
import kotlin.collections.map

private const val MAX_ROLLS = 4

typealias Rows = MutableList<MutableList<Boolean>>

class Grid(val rows: Rows) {
    fun countAdjacentRolls(row: Int, col: Int): Int {
        var count = 0
        for (i in row - 1..row + 1) {
            for (j in col - 1..col + 1) {
                if (i == row && j == col) continue
                if (get(i, j)) count++
            }
        }
        return count
    }


    fun display() {
        rows.forEach { row ->
            row.forEach { isRoll ->
                print(if (isRoll) "@" else ".")
            }
            println()
        }
    }

    fun set(row: Int, col: Int, value: Boolean) {
        rows[row][col] = value
    }

    fun get(row: Int, col: Int): Boolean = rows.getOrNull(row)?.getOrNull(col) ?: false
}

fun main() {
    val fileName = "day04.txt"
    val regex = Regex("""^(.+)$""")

    val baseRows: Rows = parseResourceLines(fileName, regex) { match ->
        val (line) = match.destructured
        line.split("").filter { it.isNotEmpty() }.map { it == "@" }.toMutableList()
    }.toMutableList()
    var grid = Grid(baseRows)

    var totalRemoved = 0

    do {
        var removedThisStep = 0
        val interGrid = Grid(grid.rows)

        grid.rows.forEachIndexed { row, line ->
            line.forEachIndexed { col, isRoll ->
                if (isRoll && grid.countAdjacentRolls(row, col) < MAX_ROLLS) {
                    interGrid.set(row, col, false)
                    removedThisStep++
                }
            }
        }

        grid = interGrid
        totalRemoved += removedThisStep
    } while (removedThisStep > 0)

    println(totalRemoved)
}
