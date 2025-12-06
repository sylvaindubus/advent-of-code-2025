package adventOfCode2025.utils

fun readResourceText(fileName: String): String =
    requireNotNull(
        object {}::class.java.classLoader.getResourceAsStream(fileName)
    ) { "Resource not found: $fileName" }
        .bufferedReader()
        .use { it.readText() }

fun readResourceLines(fileName: String): List<String> =
    readResourceText(fileName).lineSequence().toList()

fun <T> parseResourceLines(
    fileName: String,
    regex: Regex,
    mapper: (MatchResult) -> T?
): List<T> =
    readResourceLines(fileName)
        .mapNotNull { line ->
            regex.matchEntire(line)?.let(mapper)
        }
