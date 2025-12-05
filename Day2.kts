import java.io.File
import kotlin.math.pow

// Presumably the ranges don't overlap

// PART 1

val text = File("inputs/Day2.txt").readLines()[0]

val ranges = text.split(",").map {s ->
    val endpoints = s.split("-")
    endpoints[0] to endpoints[1]
}

// Get all invalid codes with n digits
fun nDigitInvalidIds(n : Long) : List<Long> {
    if (n % 2 != 0L) return listOf()
    val m = (n / 2).toInt()
    val start = 10.0.pow(m - 1).toLong()
    val end = start * 10 - 1
    val multiplier = start * 10 + 1
    return (start..end).map { x -> x * multiplier }
}

fun getInvalidIds(startString : String, endString : String) : List<Long> {
    val start = startString.toLong()
    val end = endString.toLong()
    val startLength = startString.length.toLong()
    val endLength = endString.length.toLong()
    return (startLength..endLength)
        .map(::nDigitInvalidIds)
        .flatten()
        .filter { it >= start && it <= end }
}

val total = ranges
    .map { getInvalidIds(it.first, it.second) }
    .sumOf { it.sum() }

println(total)

// PART 2

fun isInvalid(n : Long) : Boolean {
    val s = n.toString()
    val l = s.length
    if (l == 1) return false
    for (i in 1..l / 2) {
        if (l % i != 0) continue
        if (s == s.subSequence(0, i).repeat(l / i)) return true
    }
    return false
}

val newSum = ranges
    .map { it.first.toLong()..it.second.toLong() }
    .flatten()
    .filter { isInvalid(it) }
    .sum()

println(newSum)
