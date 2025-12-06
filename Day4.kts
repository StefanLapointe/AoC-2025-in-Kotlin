import java.io.File

// PART 1

val grid = File("inputs/Day4.txt").readLines().map { it.toCharArray() }

val offsets = (-1..1)
    .map {
        x -> (-1..1).map {
            y -> Pair(x, y)
        }
    }
    .flatten()
    .filter { it.first != 0 || it.second != 0 }

val m = grid.size
val n = grid[0].size

val indices = (0..<m)
    .map {
        x -> (0..<n)
        .map {
            y -> Pair(x, y)
        }
    }
    .flatten()

fun isInBounds(p : Pair<Int, Int>) : Boolean {
    return 0 <= p.first && p.first < m && 0 <= p.second && p.second < n
}

fun numNeighbours(p : Pair<Int, Int>) : Int {
    return offsets
        .map { Pair(it.first + p.first, it.second + p.second) }
        .filter { isInBounds(it) }
        .count { grid[it.first][it.second] == '@' }
}

println(
    indices
        .filter { grid[it.first][it.second] == '@' }
        .count { numNeighbours(it) < 4 }
)

// PART 2

fun newNumNeighbours(p : Pair<Int, Int>, gridState : List<CharArray>) : Int {
    return offsets
        .map { Pair(it.first + p.first, it.second + p.second) }
        .filter { isInBounds(it) }
        .count { gridState[it.first][it.second] == '@' }
}

fun removeRolls(gridState : List<CharArray>) : Int {
    val accessibleRolls = indices
        .filter { gridState[it.first][it.second] == '@' }
        .filter { newNumNeighbours(it, gridState) < 4 }
        .toSet()
    if (accessibleRolls.isEmpty()) return 0
    val newGrid = gridState
        .toMutableList()
        .map { it.clone() }
    for (p in accessibleRolls) newGrid[p.first][p.second] = '.'
    return accessibleRolls.size + removeRolls(newGrid)
}

println(
    removeRolls(grid)
)
