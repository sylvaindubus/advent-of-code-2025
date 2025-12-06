package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines

private const val START_POS = 50
private const val LOCK_SIZE = 100

fun main() {
    val fileName = "day01.txt"
    val regex = Regex("""^([LR])(\d+)$""")

    data class Action(val direction: String, val shift: Int)

    val actions: List<Action> = parseResourceLines(fileName, regex) { match ->
        val (direction, shift) = match.destructured
        Action(direction, shift.toInt())
    }

    var pos = START_POS
    var countPosToZero = 0

    actions.forEach {
        pos = when (it.direction) {
            "R" -> pos + it.shift
            "L" -> pos - it.shift
            else -> pos
        }

        pos = ((pos % LOCK_SIZE) + LOCK_SIZE) % LOCK_SIZE

        if (pos == 0) countPosToZero++
    }

    println(countPosToZero)
}
