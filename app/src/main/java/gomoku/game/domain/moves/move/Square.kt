package gomoku.game.domain.moves.move

import gomoku.game.domain.moves.square.Column
import gomoku.game.domain.moves.square.Row
import gomoku.game.domain.moves.square.toColumn
import gomoku.game.domain.moves.square.toRow

/**
 * Represents a square on the board, which is a combination of a row and a column.
 */
@Suppress("DataClassPrivateConstructor")
data class Square private constructor(val row: Row, val col: Column) {
    companion object {
        operator fun invoke(row: Int, col: Char): Square =
            Square(Row(row), Column(col))

        operator fun invoke(rowIndex: Int, colIndex: Int): Square =
            Square(rowIndex.toRow(), colIndex.toColumn())
    }
}