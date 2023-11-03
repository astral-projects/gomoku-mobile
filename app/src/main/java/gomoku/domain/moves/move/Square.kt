package gomoku.domain.moves.move

import gomoku.domain.moves.square.Column
import gomoku.domain.moves.square.Row
import gomoku.domain.moves.square.toColumn
import gomoku.domain.moves.square.toRow

/**
 * Represents a square instance defined by a unique combination of a [Row] and [Column].
 */
data class Square private constructor(val row: Row, val col: Column) {
    companion object {
        operator fun invoke(row: Int, col: Char): Square = Square(Row(row), Column(col))
        operator fun invoke(rowIndex: Int, colIndex: Int): Square = Square(rowIndex.toRow(), colIndex.toColumn())
    }
}