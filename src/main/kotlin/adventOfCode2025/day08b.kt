package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val fileName = "day08.txt"

    class Point(val x: Double, val y: Double, val z: Double) {
        override fun toString(): String {
            return "$x,$y,$z"
        }
    }

    fun measureDistance(p1: Point, p2: Point): Double {
        val x = (p1.x - p2.x).pow(2)
        val y = (p1.y - p2.y).pow(2)
        val z = (p1.z - p2.z).pow(2)
        return sqrt(x + y + z)
    }

    val regex = Regex("""^(\d+),(\d+),(\d+)$""")
    val points: List<Point> = parseResourceLines(fileName, regex) { match ->
        val (x, y, z) = match.destructured
        Point(x.toDouble(), y.toDouble(), z.toDouble())
    }

    val distances = mutableMapOf<Pair<Point, Point>, Double>()
    for (i in 0 until points.size - 1) {
        for (j in i + 1 until points.size) {
            val pair = Pair(points[i], points[j])
            distances[pair] = measureDistance(points[i], points[j])
        }
    }

    val sortedDistances = distances.toSortedMap { p1, p2 ->
        val dist1 = distances[p1]!!
        val dist2 = distances[p2]!!
        dist1.compareTo(dist2)
    }

    val circuits = mutableListOf<MutableList<Point>>()
    var lastJunction: Pair<Point, Point> = Pair(Point(0.0, 0.0, 0.0), Point(0.0, 0.0, 0.0))

    for (i in 0 until sortedDistances.size) {
        // When all points are in the same circuit, we're done
        if (circuits.isNotEmpty() && circuits.first().size == points.size) {
            break
        }

        val (p1, p2) = sortedDistances.keys.elementAt(i)
        val indexP1 = circuits.indexOfFirst { it.contains(p1) }
        val indexP2 = circuits.indexOfFirst { it.contains(p2) }

        if (indexP1 != -1 && indexP2 == -1) {
            // Add p2 to circuit
            circuits[indexP1].add(p2)
            lastJunction = Pair(p1, p2)
            continue
        }

        if (indexP1 == -1 && indexP2 != -1) {
            // Add p1 to circuit
            circuits[indexP2].add(p1)
            lastJunction = Pair(p1, p2)
            continue
        }

        if (indexP1 != -1 && indexP2 != -1) {
            if (indexP1 != indexP2) {
                // Merge circuits together
                circuits[indexP1].addAll(circuits[indexP2])
                circuits.removeAt(indexP2)
                lastJunction = Pair(p1, p2)
            }
            // Otherwise, they are already in the same circuit
            continue
        }

        // Create new circuit
        circuits.add(mutableListOf(p1, p2))
    }

    println((lastJunction.first.x * lastJunction.second.x).toLong())
}

