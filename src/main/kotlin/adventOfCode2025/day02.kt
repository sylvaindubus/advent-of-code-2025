package adventOfCode2025

import adventOfCode2025.utils.readResourceLine

fun main() {
    val fileName = "day2.txt"

    val input = readResourceLine(fileName)

    var total = 0L

    val ranges = input.split(",")
    for (range in ranges) {
        val (min, max) = range.split("-").map { it.toLong() }
        for (i in min..max) {
            val id = i.toString()
            if (id.length % 2 == 0) {
                val firstHalf = id.take(id.length / 2)
                val secondHalf = id.substring(id.length / 2)
                if (firstHalf == secondHalf) {
                    total += i
                }
            }
        }
    }

    println(total)
}

