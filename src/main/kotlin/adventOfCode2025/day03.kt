package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines

fun main() {
    val fileName = "day03.txt"
    val regex = Regex("""^(\d+)$""")

    val banks: List<List<Int>> = parseResourceLines(fileName, regex) { match ->
        val (line) = match.destructured
        line.split("").filter { it.isNotEmpty() }.map { it.toInt() }
    }

    var total = 0

    for (bank in banks) {
        var firstDigit: Int
        var secondDigit: Int

        val maxDigit = bank.maxOrNull() ?: 0
        val index = bank.indexOf(maxDigit)
        if (index < bank.size - 1) {
            firstDigit = maxDigit
            secondDigit = bank.subList(index + 1, bank.size).toMutableList().maxOrNull() ?: 0
        } else {
            firstDigit = bank.subList(0, bank.size - 1).toMutableList().maxOrNull() ?: 0
            secondDigit = maxDigit
        }

        total += (firstDigit.toString() + secondDigit.toString()).toInt()
    }

    println(total)
}
