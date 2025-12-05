import java.io.File

// PART 1

// My beautiful abstract art
val banks =
    File("inputs/Day3.txt")
        .readLines()
        .map {
            it
                .toCharArray()
                .map {
                    it
                        .toString()
                        .toLong()
                }
        }

fun maxJoltage(bank : List<Long>) : Long {
    val firstDigit = bank.subList(0, bank.size - 1).max()
    val index = bank.indexOf(firstDigit)
    val secondDigit = bank.subList(index + 1, bank.size).max()
    return 10 * firstDigit + secondDigit
}

println(
    banks.sumOf { maxJoltage(it) }
)

// PART 2

fun newMaxJoltage(bank : List<Long>, numBatteries : Int, acc : Long) : Long {
    if (numBatteries == 0) return acc
    val firstDigit = bank.subList(0, bank.size - numBatteries + 1).max()
    val index = bank.indexOf(firstDigit)
    val suffix = bank.subList(index + 1, bank.size)
    return newMaxJoltage(
        suffix,
        numBatteries - 1,
        10 * acc + firstDigit
    )
}

println(
    banks.sumOf { newMaxJoltage(it, 12, 0) }
)
