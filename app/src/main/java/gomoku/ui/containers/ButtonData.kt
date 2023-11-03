package gomoku.ui.containers

/**
 * Represents a button's properties.
 */
data class ButtonData(
    val label: String,
    val iconId: Int,
    val onClick: () -> Unit = {},
    val validationParameter: (String)->Boolean ={true}
)
