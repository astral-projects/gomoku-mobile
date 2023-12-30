package gomoku.domain.game.board

import gomoku.domain.game.Timer
import gomoku.domain.game.moves.move.Player

/**
 * Represents a turn in the game.
 * @param player the player that can make a move.
 * @param timer the timer that is running for the player.
 */
data class BoardTurn(val player: Player, val timer: Timer) {
    fun other(): Player = when (player) {
        Player.W -> Player.B
        Player.B -> Player.W
    }
}

fun String.toBoardTurn(): BoardTurn {
    when (this) {
        "W" -> return BoardTurn(Player.W, Timer(0, 0))
        "B" -> return BoardTurn(Player.B, Timer(0, 0))
        else -> throw IllegalArgumentException("Invalid player.")
    }
}
