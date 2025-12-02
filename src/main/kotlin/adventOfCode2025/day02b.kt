package adventOfCode2025

import adventOfCode2025.utils.readResourceLine

fun getRepeatPatternSizes(n: Int): List<Int> {
    return (1..n / 2).mapNotNull {
        if (n % it == 0) it else null
    }
}

fun containsRepeatedPattern(input: String): Boolean {
    return getRepeatPatternSizes(input.length).any {
        val regex = Regex("""^(${input.take(it)}){${input.length / it}}$""")
        input.matches(regex)
    }
}


fun main() {
    val fileName = "day2.txt"

    val input = readResourceLine(fileName)

    var total = 0L

    for (range in input.split(",")) {
        val (min, max) = range.split("-").map { it.toLong() }
        for (i in min..max) {
            if (containsRepeatedPattern(i.toString())) {
                total += i
            }
        }
    }

    println(total)
}

