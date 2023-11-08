package gomoku.game.domain

import gomoku.game.domain.board.Board
import gomoku.leaderboard.domain.PlayerInfo

data class Game(
    val id: String,
    val state: String,
    val variant: String,
    val board: Board,
    val createdAt: String,
    val updatedAt: String,
    val blackPlayer: PlayerInfo,
    val whitePlayer: PlayerInfo,
)