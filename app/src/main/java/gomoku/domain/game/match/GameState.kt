package gomoku.domain.game.match

enum class GameState {
    IN_PROGRESS,
    FINISHED,
}

fun String.toGameState(): GameState {
    return when (this) {
        "in_progress" -> GameState.IN_PROGRESS
        "finished" -> GameState.FINISHED
        else -> throw IllegalArgumentException("Invalid game state")
    }
}

