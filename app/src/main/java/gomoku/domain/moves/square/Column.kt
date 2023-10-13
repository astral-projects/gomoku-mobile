package gomoku.domain.moves.square

import gomoku.domain.board.BOARD_DIM

@JvmInline
/**
 * Represents a column instance defined by a [letter].
 */
value class Column private constructor(val letter: Char) {
    val index get() = letter.code - 'a'.code
    companion object {
        val values = List(BOARD_DIM) { idx -> Column((idx + 'a'.code).toChar()) } // [a, b, c, ...]
        operator fun invoke(letter: Char) = values.first { letter == it.letter }
    }
    override fun toString() = "$letter"
}

/**
 * Evaluates if the given char is a match for a valid column.
 * @return The column it belongs to or null if none of the columns match.
 */
fun Char.toColumnOrNull() = Column.values.firstOrNull { col -> this == col.letter }

/**
 * Evaluates if the given int is a match for a valid column index.
 * @return The column it belongs to.
 * @throws [IndexOutOfBoundsException] if the index does
 * not match any column.
 */
fun Int.indexToColumn() = Column.values[this]