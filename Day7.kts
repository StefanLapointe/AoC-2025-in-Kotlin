import java.io.File

// PART 1

val lines = File("inputs/Day7.txt").readLines()

var splits = 0

var beamIndices = setOf<Int>(lines[0].indexOf('S'))

for (line in lines.subList(1, lines.size)) {
    val splitterIndices = (0..<line.length).filter { line[it] == '^' }.toSet()
    val splitBeamIndices = beamIndices.filter { splitterIndices.contains(it) }
    val unsplitBeamIndices = beamIndices.filter { !splitterIndices.contains(it) }
    val leftNewBeamIndices = splitBeamIndices.map { it - 1 }
    val rightNewBeamIndices = splitBeamIndices.map { it + 1 }
    beamIndices = unsplitBeamIndices.union(leftNewBeamIndices).union(rightNewBeamIndices)
    splits += splitBeamIndices.size
}

println(splits)

// PART 2

var timelines = mapOf<Int, Long>(lines[0].indexOf('S') to 1L)

for (line in lines.subList(1, lines.size)) {
    val splitterIndices = (0..<line.length).filter { line[it] == '^' }.toSet()
    // Now we determine beamIndices from timeline map
    beamIndices = timelines.keys
    val splitBeamIndices = beamIndices.filter { splitterIndices.contains(it) }
    val unsplitBeamIndices = beamIndices.filter { !splitterIndices.contains(it) }
    val leftNewBeamIndices = splitBeamIndices.map { it - 1 }
    val rightNewBeamIndices = splitBeamIndices.map { it + 1 }
    beamIndices = unsplitBeamIndices.union(leftNewBeamIndices).union(rightNewBeamIndices)
    // Now we make a new timeline map with the new beam indices as keys
    val newTimelines = mutableMapOf<Int, Long>()
    for (i in beamIndices) {
        var total = 0L
        if (unsplitBeamIndices.contains(i)) total += timelines.getOrDefault(i, 0)
        if (leftNewBeamIndices.contains(i)) total += timelines.getOrDefault(i + 1, 0)
        if (rightNewBeamIndices.contains(i)) total += timelines.getOrDefault(i - 1, 0)
        newTimelines[i] = total
    }
    timelines = newTimelines
}

println(timelines.values.sum())
