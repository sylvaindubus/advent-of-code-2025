package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines

private const val BATTERY_SIZE = 12

fun main() {
    val fileName = "day3.txt"
    val regex = Regex("""^(\d+)$""")

    val banks: List<List<Int>> = parseResourceLines(fileName, regex) { match ->
        val (line) = match.destructured
        line.split("").filter { it.isNotEmpty() }.map { it.toInt() }
    }

    var total = 0L

    for (bank in banks) {
        val indexes = mutableListOf<Int>()
        for (i in 0 until BATTERY_SIZE) {
            val shift = if (indexes.isNotEmpty()) indexes.last() + 1 else 0
            val sublist = bank.subList(shift, (bank.size + 1 - BATTERY_SIZE + i))

            val max = sublist.maxOrNull() ?: 0
            val index = shift + sublist.indexOf(max)
            indexes.add(index)
        }

        val jolt = indexes.joinToString("") { bank[it].toString() }.toLong()
        total += jolt
    }

    println(total)
}
