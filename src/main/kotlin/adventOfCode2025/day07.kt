package adventOfCode2025

import adventOfCode2025.utils.readResourceLines

fun main() {
    val fileName = "day07.txt"

    val lines: List<CharArray> = readResourceLines(fileName).map { it.toCharArray() }

    var splitCount = 0

    var nextLine = lines.first()
    for (row in 0 until lines.lastIndex) {
        val currentLine = nextLine
        nextLine = lines[row + 1]

        currentLine.forEachIndexed { col, cell ->
            when (cell) {
                'S' -> nextLine[col] = '|'
                '|' -> {
                    if (nextLine[col] == '^') {
                        nextLine[col - 1] = '|'
                        nextLine[col + 1] = '|'
                        splitCount++
                    } else {
                        nextLine[col] = '|'
                    }
                }
            }
        }
    }

    println(splitCount)
}