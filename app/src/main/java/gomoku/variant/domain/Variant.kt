package gomoku.variant.domain

/**
 * Represents data and functionality related to the variant's screen.
 */
object Variant {
    const val TITLE: String = "Variants"
    const val SUBMIT_BUTTON_TEXT: String = "Play"

    fun getDescriptionFormat(variant: VariantConfig): String =
        "Board size: ${variant.boardSize.value}" + "x" + "${variant.boardSize.value}\n" +
        "Opening rule: ${variant.openingRule.name}"
}
