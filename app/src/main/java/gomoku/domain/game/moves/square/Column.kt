package gomoku.domain.game.moves.square

/**
 * Represents a column on the board.
 * @param letter the letter of the column.
 */
@JvmInline
value class Column(val letter: Char) {
    init {
        require(letter in 'a'..'z') { "Column letter must be between a and z." }
    }
}

/**
 * Converts an integer to a column.
 */
fun Int.toColumn() = Column('a' + this)