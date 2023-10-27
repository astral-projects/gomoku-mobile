package gomoku.domain.board

import gomoku.domain.Turn
import gomoku.domain.moves.Moves

const val BOARD_DIM = 15

data class Board(
    val moves: Moves,
    val turn: Turn
)

