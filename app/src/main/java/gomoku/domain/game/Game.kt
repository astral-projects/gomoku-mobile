package gomoku.domain.game

import gomoku.domain.game.board.Board
import gomoku.domain.leaderboard.PlayerInfo

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