package adventOfCode2025

import adventOfCode2025.utils.Range
import adventOfCode2025.utils.parseResourceLines

fun main() {
    val fileName = "day05.txt"

    val rangeRegex = Regex("""^((\d+)-(\d+))$""")
    val ranges: List<Range> = parseResourceLines(fileName, rangeRegex) { match ->
        val (_, min, max) = match.destructured
        Pair(min.toLong(), max.toLong())
    }

    val ingredientRegex = Regex("""^(\d+)$""")
    val ingredients: List<Long> = parseResourceLines(fileName, ingredientRegex) { match ->
        val (line) = match.destructured
        line.toLong()
    }

    var total = 0
    for (ingredient in ingredients) {
        if (ranges.any { ingredient >= it.first && ingredient <= it.second }) {
            total++
        }
    }

    println(total)
}