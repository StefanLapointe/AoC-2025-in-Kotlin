import java.io.File
import kotlin.math.pow

// PART 1

val boxes = File("inputs/Day8.txt")
    .readLines()
    .map { it.split(',') }
    .map { x -> x.map { it.toDouble() } }
    .map { Triple(it[0], it[1], it[2]) }

val pairs = (0..<boxes.size)
    .map { x -> (0..<x).map { Pair(it, x) } }
    .flatten()
    .map { boxes[it.first] to boxes[it.second] }

fun distance(pair : Pair<Triple<Double, Double, Double>, Triple<Double, Double, Double>>) : Double {
    return ((pair.second.first - pair.first.first).pow(2.0) +
            (pair.second.second - pair.first.second).pow(2.0) +
            (pair.second.third - pair.first.third).pow(2.0)).pow(0.5)
}

val closestPairs = pairs.sortedBy { distance(it) }.subList(0, 1000)

fun getAdjacentNodes(box : Triple<Double, Double, Double>) : List<Triple<Double, Double, Double>> {
    val tails = closestPairs
        .filter { it.first == box }
        .map { it.second }
    val heads = closestPairs
        .filter { it.second == box }
        .map { it.first }
    return heads + tails
}

val adjacencyLists = boxes.associateWith { getAdjacentNodes(it) }

val remainingBoxes = boxes.toMutableSet()

val componentSizes = mutableListOf<Int>()

// DFS to explore components
while (remainingBoxes.isNotEmpty()) {
    var componentSize = 0
    val queue = mutableListOf<Triple<Double, Double, Double>>(remainingBoxes.first())
    remainingBoxes.remove(queue.first())
    while (queue.isNotEmpty()) {
        val box = queue.removeFirst()
        componentSize++
        val neighbours = adjacencyLists
            .getOrElse(box) { listOf() }
            .filter { remainingBoxes.contains(it) }
        remainingBoxes.removeAll(neighbours)
        queue.addAll(neighbours)
    }
    componentSizes.add(componentSize)
}

val largestComponentSizes = mutableListOf<Int>()

for (i in 1..3) {
    val maxRemainingComponentSize = componentSizes.max()
    largestComponentSizes.add(maxRemainingComponentSize)
    componentSizes.remove(maxRemainingComponentSize)
}

println(
    largestComponentSizes.reduce { a, b -> a * b }
)

// PART 2

val components = boxes.map { setOf(it) }.toMutableList()

for (pair in pairs.sortedBy { distance(it) }) {
    val firstComponent = components.find { it.contains(pair.first) }
    val secondComponent = components.find { it.contains(pair.second) }
    if (firstComponent == secondComponent) continue;
    if (firstComponent == null || secondComponent == null) continue;
    val newComponent = firstComponent.union(secondComponent);
    components.remove(firstComponent)
    components.remove(secondComponent)
    components.add(newComponent)

    if (components.size == 1) {
        println(pair.first.first.toLong() * pair.second.first.toLong())
        break
    }
}