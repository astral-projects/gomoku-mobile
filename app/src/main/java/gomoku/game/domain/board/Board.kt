package gomoku.game.domain.board

import gomoku.game.domain.moves.Moves
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square

/**
 * Represents a board in the game.
 * @param moves the moves that have been made in the game.
 * @param turn the current turn.
 * @param size the size of the board.
 */
data class Board(
    val moves: Moves,
    val turn: BoardTurn,
    val size: BoardSize
) {
    fun isPlayerTurn(player: Player) = turn.player === player
    fun getPiece(currSquare: Square): Piece? = moves[currSquare]
}
