package gomoku.game.domain.moves.square

data class Row(val number: Int)

fun Int.toRow() = Row(this)