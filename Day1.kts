import java.io.File
import kotlin.math.abs

// I learned how to read files from https://www.baeldung.com/kotlin/read-file

// PART 1

var total = 0;
var dial = 50;
val lines = File("inputs/Day1.txt").readLines()
for (line in lines) {
    val magnitude = line.subSequence(1, line.length).toString().toInt()
    val change = when (line[0]) {
        'L' -> -magnitude
        'R' -> +magnitude
        else -> throw Error()
    }
    dial = (dial + change) % 100
    if (dial == 0) total++
}

println(total)

// PART 2

total = 0;
dial = 50;
// lines = File("inputs/Day1.txt").readLines()
for (line in lines) {
    val magnitude = line.subSequence(1, line.length).toString().toInt()
    val change = when (line[0]) {
        'L' -> -magnitude
        'R' -> +magnitude
        else -> throw Error()
    }
    if (change == 0) continue

    var zeros = 0
    if (line[0] == 'L' && dial == 0) zeros--
    dial += change
    var hundreds = dial / 100
    if (dial < 0 && dial % 100 != 0) hundreds-- // Account for weirdness in division
    zeros += abs(hundreds)
    dial -= 100 * hundreds
    if (line[0] == 'L' && dial == 0) zeros++
    println("$line: $zeros, $dial")

    total += zeros
}

println(total)
