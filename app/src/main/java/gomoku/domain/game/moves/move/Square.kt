package gomoku.domain.game.moves.move

import gomoku.domain.game.moves.square.Column
import gomoku.domain.game.moves.square.Row
import gomoku.domain.game.moves.square.toColumn
import gomoku.domain.game.moves.square.toRow

/**
 * Represents a square on the board, which is a combination of a row and a column.
 */
@Suppress("DataClassPrivateConstructor")
data class Square constructor(val row: Row, val col: Column) {
    companion object {
        operator fun invoke(row: Int, col: Char): Square =
            Square(Row(row), Column(col))

        operator fun invoke(rowIndex: Int, colIndex: Int): Square =
            Square(rowIndex.toRow(), colIndex.toColumn())
    }
}