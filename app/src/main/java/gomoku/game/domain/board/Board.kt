package gomoku.game.domain.board

import gomoku.game.domain.moves.Moves
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square

data class Board(
    val moves: Moves,
    val turn: BoardTurn,
    val size: BoardSize
) {
    fun isPlayerTurn(player: Player) = turn.player === player
    fun getPiece(currSquare: Square): Piece? = moves[currSquare]
}
