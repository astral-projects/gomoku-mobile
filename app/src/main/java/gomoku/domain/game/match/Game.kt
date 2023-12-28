package gomoku.domain.game.match

import gomoku.domain.game.board.Board
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.variant.VariantConfig

data class Game(
    val id: Int,
    val variant: VariantConfig,
    val board: Board,
    val host: PlayerInfo,
    val guest: PlayerInfo,
) : Match()