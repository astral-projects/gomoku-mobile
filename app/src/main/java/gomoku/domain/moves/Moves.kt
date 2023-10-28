package gomoku.domain.moves

import gomoku.domain.moves.move.Piece
import gomoku.domain.moves.move.Square

/**
 * Represents a [Square] with a [Piece] on the board.
 */
typealias Move = Pair<Square, Piece>

/**
 * Represents a list of all [Square]s with a [Piece] on the board.
 */
typealias Moves = Map<Square, Piece>