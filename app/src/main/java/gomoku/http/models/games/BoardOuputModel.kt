package gomoku.http.models.games

import gomoku.http.models.Turn

data class BoardOutputModel(
    val grid: List<String>,
    val turn: Turn?,
    val winner: String?,
)
