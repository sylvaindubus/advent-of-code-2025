package adventOfCode2025.utils

fun <T> parseResourceLines(
    fileName: String,
    regex: Regex,
    mapper: (MatchResult) -> T?
): List<T> {
    val stream = {}::class.java.classLoader.getResourceAsStream(fileName)
        ?: error("File not found: $fileName")

    return stream.bufferedReader().useLines { lines ->
        lines.mapNotNull { line ->
            val match = regex.matchEntire(line) ?: return@mapNotNull null
            mapper(match)
        }.toList()
    }
}

fun readResourceLine(fileName: String): String {
    return {}::class.java.classLoader
        .getResourceAsStream(fileName)
        ?.bufferedReader()
        ?.readText()
        ?: error("File not found: $fileName")
}

fun readResourceLines(fileName: String): List<String> {
    return readResourceLine(fileName).lines()
}