package adventOfCode2025

import adventOfCode2025.utils.readResourceLines

fun main() {
    val fileName = "day06.txt"

    val lines: List<String> = readResourceLines(fileName)
    val numberLines = lines.dropLast(1)
    val symbolLine = lines.last()

    var currentSymbol: Char = Char.MIN_VALUE;
    var total = 0L
    var subtotal = 0L

    for (index in 0 until numberLines.maxOf { it.length }) {
        val symbol = symbolLine.getOrNull(index)
        if (symbol != null && !symbol.isWhitespace()) {
            currentSymbol = symbol
            total += subtotal
            subtotal = when (currentSymbol) {
                '+' -> 0L
                '*' -> 1L
                else -> error("Unknown symbol $currentSymbol at index $index")
            }
        }

        val string = numberLines
            .map { it.getOrNull(index) }
            .filter { it != null && it.isDigit() }
            .joinToString("") { it.toString() }

        // Skip empty columns
        if (string.isEmpty()) {
            continue
        }

        val number = string.toLong()

        when (currentSymbol) {
            '+' -> subtotal += number
            '*' -> subtotal *= number
            else -> error("Unknown symbol $currentSymbol at index $index")
        }
    }

    // Add the last subtotal
    total += subtotal

    println(total)
}