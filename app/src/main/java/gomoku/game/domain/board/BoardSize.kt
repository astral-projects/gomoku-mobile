package gomoku.game.domain.board

/**
 * Represents the size of a board.
 * @param value the actual value of the size.
 */
enum class BoardSize(val value: Int) {
    FIFTEEN(15),
    NINETEEN(19)
}