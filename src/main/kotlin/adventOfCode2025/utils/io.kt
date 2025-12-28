package adventOfCode2025.utils

fun readResourceText(fileName: String): String {
    val classLoader = Thread.currentThread().contextClassLoader

    val stream =
        classLoader.getResourceAsStream(fileName)
            ?: object {}.javaClass.getResourceAsStream("/$fileName")

    return requireNotNull(stream) {
        "Resource not found: $fileName (is build/resources/main on the classpath?)"
    }.bufferedReader().use { it.readText() }
}

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
