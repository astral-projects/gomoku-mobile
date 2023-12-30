package gomoku.domain.game.board

/**
 * Represents the size of a board.
 * @param value the actual value of the size.
 */
enum class BoardSize(val value: Int) {
    FIFTEEN(15),
    NINETEEN(19)
}

fun String.toBoardSize(): BoardSize = when (this) {
    "15" -> BoardSize.FIFTEEN
    "19" -> BoardSize.NINETEEN
    else -> throw IllegalArgumentException("Invalid board size")
}