package gomoku.variant.domain

import gomoku.game.domain.board.BoardSize

/**
 * Represents a variant configuration.
 * @param name The name of the variant.
 * @param openingRule The opening rule of the variant.
 * @param boardSize The board size of the variant.
 */
data class VariantConfig(
    val id: Int,
    val name: VariantName,
    val openingRule: OpeningRule,
    val boardSize: BoardSize
)