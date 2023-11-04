package gomoku.game.domain.board

import gomoku.game.domain.Timer
import gomoku.game.domain.moves.move.Player

/**
 * Represents a turn in the game.
 * @param player the player that can make a move.
 * @param timer the timer that is running for the player.
 */
class BoardTurn(val player: Player, val timer: Timer)
