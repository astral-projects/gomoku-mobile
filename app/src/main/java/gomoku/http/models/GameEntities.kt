package gomoku.http.models

data class GameEntities(
    val id: Int,
    val state: GameState,
    val variant: GameVariant,
    val board: GameBoard,
    val createdAt: String,
    val updatedAt: String,
    val hostId: Int,
    val guestId: Int
)

data class GameState(
    val name: String
)

data class GameVariant(
    val id: Int,
    val name: String,
    val openingRule: String,
    val boardSize: Int
)

data class GameBoard(
    val grid: Array<String>,
    val turn: Turn?,
    val winner: String?
)

data class Turn(
    val player: String,
    val timeLeftInSec: TimeValue
)

data class TimeValue(
    val value: Int
)
