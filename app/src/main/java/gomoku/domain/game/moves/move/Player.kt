package gomoku.domain.game.moves.move

/**
 * Represents a player in the game.
 */
enum class Player {
    W,
    B;
}

fun String.toPlayer(): Player = when (this) {
    "B" -> Player.B
    "W" -> Player.W
    else -> {
        throw IllegalArgumentException("Invalid player.")
    }
}