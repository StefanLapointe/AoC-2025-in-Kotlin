import java.io.File
import kotlin.math.max

// PART 1

val lines = File("inputs/Day5.txt").readLines()

val emptyLineNumber = lines.indexOf("")

val ranges = lines
    .subList(0, emptyLineNumber)
    .map { it.split("-") }
    .map { it.map { it.toLong() } }
    .map { it[0] to it[1] }

val ids = lines
    .subList(emptyLineNumber + 1, lines.size)
    .map { it.toLong() }

fun isFresh(id : Long) : Boolean {
    return ranges.any { it.first <= id && id <= it.second }
}

println(
    ids.count { isFresh(it) }
)

// PART 2

val sortedRanges = ranges.sortedBy { it.first }

var numFreshIds = 0L

var highestSeen = -1L

for (p in sortedRanges) {
    if (p.second <= highestSeen) continue
    numFreshIds +=
        if (p.first > highestSeen)
            p.second - p.first + 1
        else
            p.second - highestSeen
    highestSeen = max(highestSeen, p.second)
}

println(numFreshIds)
