package gomoku.domain.home

/**
 * Represents a button's properties.
 */
data class ButtonData(
    val label: String,
    val iconId: Int,
    val onClick: () -> Unit = {}
)
