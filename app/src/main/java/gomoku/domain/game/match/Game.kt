package gomoku.domain.game.match


import gomoku.domain.game.board.Board
import gomoku.domain.game.moves.move.Player
import gomoku.domain.leaderboard.PlayerInfo

data class Game(
    val id: Int,
    val state: GameState,
    val variantId: Int,
    val board: Board,
    val host: PlayerInfo,
    val guest: PlayerInfo,
    val localPlayer: Player,
) : Match()