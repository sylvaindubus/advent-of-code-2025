package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines

fun main() {
    val fileName = "day06.txt"

    val numberRegex = Regex("""^(\s*[+*(\d+)])+$""")
    val lines: List<List<String>> = parseResourceLines(fileName, numberRegex) { match ->
        match.value.split(" ").filter { it.isNotEmpty() }
    }

    val numberLists = lines.dropLast(1).map { it.map { it.toLong() } }
    val symbols = lines.last()

    var total = 0L
    symbols.forEachIndexed { index, symbol ->
        val initial = when (symbol) {
            "+" -> 0L
            "*" -> 1L
            else -> 0L
        }
        total += numberLists.map { it[index] }.fold(initial) { acc, number ->
            when (symbol) {
                "+" -> acc + number
                "*" -> acc * number
                else -> acc
            }
        }
    }

    println(total)
}