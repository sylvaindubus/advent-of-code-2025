package adventOfCode2025

import adventOfCode2025.utils.readResourceLines
import kotlin.collections.forEachIndexed
import kotlin.text.toCharArray

fun main() {
    val fileName = "day07.txt"

    val lines: List<CharArray> = readResourceLines(fileName).map { it.toCharArray() }

    val timelinesCount = lines.last().indices.associateWith { 0L }.toMutableMap()

    var nextLine = lines.first()
    for (row in 0 until lines.lastIndex) {
        val currentLine = nextLine
        nextLine = lines[row + 1]

        currentLine.forEachIndexed { col, cell ->
            when (cell) {
                'S' -> {
                    nextLine[col] = '|'
                    timelinesCount[col] = 1
                }
                '|' -> {
                    if (nextLine[col] == '^') {
                        nextLine[col - 1] = '|'
                        nextLine[col + 1] = '|'
                        timelinesCount[col - 1] = timelinesCount.getValue(col - 1) + timelinesCount.getValue(col)
                        timelinesCount[col + 1] = timelinesCount.getValue(col + 1) + timelinesCount.getValue(col)
                        timelinesCount[col] = 0
                    } else {
                        nextLine[col] = '|'
                    }
                }
            }
        }

    }

    println(timelinesCount.values.sum())

    // This recursive solution is too slow, but it works for small inputs:

    /*
        val cache = emptyMap<String, Int>().toMutableMap()

        fun openTimeline(row: Int, col: Int): Int {
            val cachedValue = cache["$row-$col"]
            if (cachedValue != null) {
                return cachedValue
            }
            if (row == lines.lastIndex) {
                return 1
            }

            return if (lines[row + 1][col] == '^') {
                val left = openTimeline(row + 1, col - 1)
                cache["${row + 1}${col - 1}"] = left
                val right = openTimeline(row + 1, col + 1)
                cache["${row + 1}${col + 1}"] = right
                left + right
            } else {
                val res = openTimeline(row + 1, col)
                cache["${row + 1}${col}"] = res
                res
            }
        }

        val firstRow = 1
        val firstCol = lines.first().indexOfFirst { it == 'S' }

        println(openTimeline(firstRow, firstCol))
    */
}