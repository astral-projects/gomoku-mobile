package gomoku.game.domain.moves.square

/**
 * Represents a row on the board.
 * @param number the number of the row.
 */
@JvmInline
value class Row(val number: Int) {
    init {
        require(number >= 0) { "Row number must be non-negative." }
    }
}

/**
 * Converts an integer to a row.
 */
fun Int.toRow() = Row(this)