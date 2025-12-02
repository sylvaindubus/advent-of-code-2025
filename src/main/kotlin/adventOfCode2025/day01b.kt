package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines
import kotlin.math.abs

private const val START_POS = 50
private const val LOCK_SIZE = 100

fun main() {
    val fileName = "day1.txt"
    val regex = Regex("""^([LR])(\d+)$""")

    data class Action(val direction: String, val shift: Int)

    val actions: List<Action> = parseResourceLines(fileName, regex) { match ->
        val (direction, shift) = match.destructured
        Action(direction, shift.toInt())
    }

    var pos = START_POS
    var countPosToZero = 0

    actions.forEach {
        val prevPos = pos
        pos = when (it.direction) {
            "R" -> pos + it.shift
            "L" -> pos - it.shift
            else -> pos
        }

        if (it.direction == "R" && pos > 100) {
            countPosToZero += (pos - 1) / LOCK_SIZE
        }
        if (it.direction == "L" && pos < -100) {
            countPosToZero += abs((pos + 1) / LOCK_SIZE)
        }
        if (prevPos > 0 && pos < 0) countPosToZero++

        pos = ((pos % LOCK_SIZE) + LOCK_SIZE) % LOCK_SIZE

        if (pos == 0) countPosToZero++
    }

    println(countPosToZero)
}
