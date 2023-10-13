package gomoku.domain.moves.move

import gomoku.domain.board.BOARD_DIM
import gomoku.domain.moves.square.Column
import gomoku.domain.moves.square.Row
import gomoku.domain.moves.square.indexToColumn
import gomoku.domain.moves.square.indexToRow

/**
 * Represents a square instance defined by a unique combination of a [Row] and [Column].
 */
class Square private constructor(val row: Row, val col: Column) {

    companion object {
        // If MAX_SQUARES == 4, values = [Square(2, a), Square(2, b), Square(1, a), Square(1, b)]
        val values = List(BOARD_DIM * BOARD_DIM) { idx ->
            Square((idx / BOARD_DIM).indexToRow(), (idx % BOARD_DIM).indexToColumn())
        }

        operator fun invoke(rowIndex: Int, colIndex: Int) = values[rowIndex * BOARD_DIM + colIndex]

        operator fun invoke(row: Row, col: Column) = values[row.index * BOARD_DIM + col.index]

    }

    override fun toString() = "${row.number}${col.letter}"
}

/**
 * Evaluates if the given string is a match for a valid square.
 * @return The square it belongs to or null if none of the squares match.
 */
fun String.toSquareOrNull() = Square.values.firstOrNull { sqr -> sqr.toString() == this }