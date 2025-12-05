package adventOfCode2025

import adventOfCode2025.utils.Range
import adventOfCode2025.utils.parseResourceLines

fun main() {
    val fileName = "day5.txt"

    val rangeRegex = Regex("""^((\d+)-(\d+))$""")
    val ranges: MutableList<Range> = parseResourceLines(fileName, rangeRegex) { match ->
        val (_, min, max) = match.destructured
        Pair(min.toLong(), max.toLong())
    }.toMutableList()

    fun areRangesOverlapping(range1: Range, range2: Range): Boolean {
        return range1.first <= range2.second && range1.second >= range2.first
    }

    fun mergeRanges(range1: Range, range2: Range): Range {
        return Range(minOf(range1.first, range2.first), maxOf(range1.second, range2.second))
    }

    do {
        for (i in ranges.indices) {
            for (j in i + 1 until ranges.size) {
                if (areRangesOverlapping(ranges[i], ranges[j])) {
                    ranges[i] = mergeRanges(ranges[i], ranges[j])
                    ranges[j] = Range(-1L, -1L)
                }
            }
        }
    } while (ranges.removeIf { it.first == -1L && it.second == -1L })

    println(ranges.sumOf { it.second - it.first + 1 })
}