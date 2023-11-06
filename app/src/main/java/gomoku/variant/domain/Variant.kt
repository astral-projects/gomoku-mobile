package gomoku.variant.domain

/**
 * Represents data and functionality related to the variant's screen.
 */
object Variant {
    const val TITLE: String = "Variants"
    const val SUBMIT_BUTTON_TEXT: String = "Play"
    const val NOT_VARIANTS_FOUND: String =
        "No variants are currently available. Please try again later."
    private const val BOARD_SIZE = "Board size: "
    private const val OPENING_RULE = "Opening rule: "
    private const val BOARD_SIZE_SEPARATOR = "x"

    fun getDescriptionFormat(variant: VariantConfig): String =
        "$BOARD_SIZE${variant.boardSize.value}$BOARD_SIZE_SEPARATOR${variant.boardSize.value}\n" +
                "$OPENING_RULE${variant.openingRule.name}"
}
