import java.io.File

// PART 1

val tokens = File("inputs/Day6.txt")
    .readLines()
    .map { it ->
        it
            .split(" ")
            .filter { it != "" }
    }

val numProblems = tokens[0].size

val grandTotal = (0..<numProblems).sumOf { i ->
    if (tokens[4][i] == "+")
        (0..3).sumOf { tokens[it][i].toLong() }
    else
        (0..3)
            .map { tokens[it][i].toLong() }
            .fold(1) { x, y -> x * y }
}

println(grandTotal)

// PART 2

val reversedLines =
    File("inputs/Day6.txt")
        .readLines()
        .map { it.reversed() }

val targetLength = reversedLines.maxOf { it.length }

val paddedReversedLines = reversedLines.map { " ".repeat(targetLength - it.length) + it }

// This transformation is unnecessary but made thinking about the problem easier for me
val transpose =
    (0..<targetLength)
        .map { i ->
            (0..4)
                .map { j -> paddedReversedLines[j][i] }
                .joinToString("")
        }

val operationIndices = (0..<transpose.size).filter { transpose[it][4] != ' ' }

// -1 is an imaginary empty row
// -2 is an imaginary row containing an operation
val previousOperationIndices =
    listOf<Int>(-2) +
            operationIndices
                .subList(0, numProblems - 1)

val ranges =
    (0..<numProblems)
        .map { previousOperationIndices[it] to operationIndices[it] }

fun columnToNumber(col : Int): Long {
    return (0..3)
        .map { transpose[col][it] }
        .joinToString("")
        .trim()
        .toLong()
}

fun solveProblem(problemNumber : Int) : Long {
    val columnRange = ranges[problemNumber]
    val numbers =
        (columnRange.first + 2..columnRange.second)
            .map { columnToNumber(it) }
    return if (transpose[columnRange.second][4] == '+')
        numbers.sum()
    else
        numbers.fold(1) { x, y -> x * y }
}

println(
    (0..<numProblems).sumOf { solveProblem(it) }
)
