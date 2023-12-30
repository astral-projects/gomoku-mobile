package gomoku.domain.game.moves

import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.game.moves.square.Column
import gomoku.domain.game.moves.square.Row

/**
 * Represents a [Square] with a [Piece] on the board.
 */
typealias Move = Pair<Square, Piece>

/**
 * Represents a list of all [Square]s with a [Piece] on the board.
 */
typealias Moves = Map<Square, Piece>

fun createMovesFromGrid(grid: List<String>): Moves {
    val movesMap = mutableMapOf<Square, Piece>()
    for (gridItem in grid) {
        val parts = gridItem.split("-")
        val squareName = parts[0]
        val columnChar = squareName.first()
        val column = Column(columnChar)

        val rowString = squareName.substring(1)
        val row = Row(rowString.toInt())

        val pieceInfo = parts[1].split("_")
        val piece = if (pieceInfo[0] == "w") {
            Piece(Player.W)
        } else {
            Piece(Player.B)
        }
        val square = Square(row, column)
        movesMap[square] = piece
    }
    return movesMap

}
