package gomoku.domain.moves.square

import gomoku.domain.board.BOARD_DIM

/**
 * Represents a row instance defined by a unique [number].
 */
@JvmInline
value class Row private constructor(val number: Int) {
    val index get() = BOARD_DIM - number
    companion object {
        val values = List(BOARD_DIM) { idx -> Row(BOARD_DIM - idx) } // [... , 3, 2, 1]
        operator fun invoke(number: Int) = values.first { number == it.number }
    }
    override fun toString() = "$number"
}

// Extension functions:
/**
 * Evaluates if the given int is a match for a valid row.
 * @return The row it belongs to or null if none of the rows match.
 */
fun Int.toRowOrNull() = Row.values.firstOrNull { row -> this == row.number }

/**
 * Evaluates if the given int is a match for a valid row index.
 * @return The row it belongs to.
 * @throws [IndexOutOfBoundsException] if the index does not match any row.
 */
fun Int.indexToRow() = Row.values[this]