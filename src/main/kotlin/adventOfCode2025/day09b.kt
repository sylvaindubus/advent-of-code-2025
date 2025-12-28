package adventOfCode2025

import adventOfCode2025.utils.parseResourceLines
import kotlin.math.abs

fun main() {
    val fileName = "day09.txt"

    class Point(val x: Int, val y: Int) {
        override fun toString(): String {
            return "x=$x,y=$y"
        }
    }

    class Segment(val p1: Point, val p2: Point) {
        override fun toString(): String {
            return "($p1 -> $p2)"
        }
    }

    val regex = Regex("""^(\d+),(\d+)$""")
    val redPoints: List<Point> = parseResourceLines(fileName, regex) { match ->
        val (x, y) = match.destructured
        Point(x.toInt(), y.toInt())
    }

    val allSegments: List<Segment> = redPoints.mapIndexed { index, point ->
        val prevPoint = if (index > 0) redPoints[index - 1] else redPoints.last()
        Segment(prevPoint, point)
    }

    val maxCol = redPoints.maxOf { it.x }
    val maxRow = redPoints.maxOf { it.y }

    // Draw the floor
    val floor = mutableListOf<CharArray>()
    repeat(maxRow + 1) {
        floor.add(CharArray(maxCol + 1) { '.' })
    }

    // Add red tiles
    redPoints.forEach { tile -> floor[tile.y][tile.x] = '#' }

    // Add lines of green tiles
    redPoints.forEachIndexed { index, tile ->
        floor[tile.y][tile.x] = '#'
        val prevTile = if (index > 0) redPoints[index - 1] else redPoints.last()
        // Draw line from prevTile to tile
        if (prevTile.x == tile.x) {
            for (y in Math.min(prevTile.y, tile.y) + 1 until Math.max(prevTile.y, tile.y)) {
                floor[y][tile.x] = 'X'
            }
        } else if (prevTile.y == tile.y) {
            for (x in Math.min(prevTile.x, tile.x) + 1 until Math.max(prevTile.x, tile.x)) {
                floor[tile.y][x] = 'X'
            }
        }
//        }
    }

    // Fill green tiles
    floor.forEachIndexed { row, lines ->
        val firstColored = lines.indexOfFirst { it == '#' || it == 'X' }
        val lastColored = lines.indexOfLast { it == '#' || it == 'X' }
        for (col in firstColored + 1 until lastColored) {
            if (floor[row][col] == '.') {
                floor[row][col] = 'X'
            }
        }
    }

    fun isRectangleValid(tile1: Point, tile2: Point): Boolean {
        val minY = Math.min(tile1.y, tile2.y)
        val maxY = Math.max(tile1.y, tile2.y)
        val minX = Math.min(tile1.x, tile2.x)
        val maxX = Math.max(tile1.x, tile2.x)

        for (y in minY..maxY) {
            if (floor[y][minX] == '.' || floor[y][maxX] == '.') {
                return false
            }
        }
        for (x in minX..maxX) {
            if (floor[minY][x] == '.' || floor[maxY][x] == '.') {
                return false
            }
        }
        return true
    }

//    fun areSegmentsIntersecting(s1: Segment, s2: Segment): Boolean {
//        val a = s1.p1
//        val b = s1.p2
//        val c = s2.p1
//        val d = s2.p2
//
//        // Both horizontal segments
//        if (a.y == b.y && c.y == d.y) return false
//
//        // Both vertical segments
//        if (a.x == b.x && c.x == d.x) return false
//
//        // S1 is vertical and S2 is horizontal
//        if (a.x == b.x) {
//            val x = a.x
//            val y = c.y
//
//            val s1MinY = minOf(a.y, b.y)
//            val s1MaxY = maxOf(a.y, b.y)
//
//            val s2MinX = minOf(c.x, d.x)
//            val s2MaxX = maxOf(c.x, d.x)
//
//            return x in s2MinX + 1..s2MaxX - 1 && y in s1MinY + 1..s1MaxY - 1
//        }
//
//        // S1 is horizontal and S2 is vertical
//        if (a.y == b.y) {
//            val x = c.x
//            val y = a.y
//
//            val s1MinX = minOf(a.x, b.x)
//            val s1MaxX = maxOf(a.x, b.x)
//
//            val s2MinY = minOf(c.y, d.y)
//            val s2MaxY = maxOf(c.y, d.y)
//
//            return x in s1MinX + 1..s1MaxX - 1 && y in s2MinY + 1..s2MaxY - 1
//        }
//
//        // Never happens
//        return false
//    }
//
//    fun isRectangleValid(p1: Point, p2: Point): Boolean {
//        val minY = Math.min(p1.y, p2.y)
//        val maxY = Math.max(p1.y, p2.y)
//        val minX = Math.min(p1.x, p2.x)
//        val maxX = Math.max(p1.x, p2.x)
//
//        val leftTop = Point(minX, minY)
//        val rightTop = Point(maxX, minY)
//        val rightBottom = Point(maxX, maxY)
//        val leftBottom = Point(minX, maxY)
//
//        val rectangleSegments = listOf(
//            Segment(leftTop, rightTop),
//            Segment(rightTop, rightBottom),
//            Segment(rightBottom, leftBottom),
//            Segment(leftBottom, leftTop),
//        )
//
////        println(rectangleSegments)
////        exitProcess(0)
//
//        return rectangleSegments.none { rectangleSegment ->
//            allSegments.any { segment ->
//                areSegmentsIntersecting(segment, rectangleSegment)
//            }
//        }
//    }
//
    var largestArea = 0L
    for (i in 0 until redPoints.size - 1) {
        for (j in i + 1 until redPoints.size) {
            val tile1 = redPoints[i]
            val tile2 = redPoints[j]
            val area = (abs(tile1.x - tile2.x) + 1).toLong() * (abs(tile1.y - tile2.y) + 1).toLong()
            if (area > largestArea && isRectangleValid(tile1, tile2)) {
                largestArea = area
            }
        }
    }

    println(largestArea)
}

