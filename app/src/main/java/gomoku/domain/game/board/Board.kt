package gomoku.domain.game.board

import gomoku.domain.game.moves.Moves
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.leaderboard.PlayerInfo

/**
 * Represents a board in the game.
 * @param moves the moves that have been made in the game.
 * @param turn the current turn.
 * @param size the size of the board.
 */
data class Board(
    var moves: Moves,
    val turn: BoardTurn? = null,
    val winner: PlayerInfo? = null,
    val size: BoardSize,
) {
    fun isPlayerTurn(player: Player) = turn?.player == player
    fun getPiece(currSquare: Square): Piece? = moves[currSquare]
}
