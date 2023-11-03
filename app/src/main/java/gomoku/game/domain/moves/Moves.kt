package gomoku.game.domain.moves

import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Square

/**
 * Represents a [Square] with a [Piece] on the board.
 */
typealias Move = Pair<Square, Piece>

/**
 * Represents a list of all [Square]s with a [Piece] on the board.
 */
typealias Moves = Map<Square, Piece>