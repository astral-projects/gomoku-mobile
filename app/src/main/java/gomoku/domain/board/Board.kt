package gomoku.domain.board

import gomoku.domain.moves.Moves
import gomoku.domain.moves.move.Piece
import gomoku.domain.moves.move.Player
import gomoku.domain.moves.move.Square

data class Board(
    val moves: Moves,
    val turn: BoardTurn,
    val size: BoardSize
) {
    fun isPlayerTurn(player: Player) = turn.player === player
    fun getPiece(currSquare: Square): Piece? = moves[currSquare]
}
