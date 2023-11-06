package gomoku.variant.domain

import gomoku.game.domain.board.BoardSize

data class VariantConfig(val name: VariantName, val openingRule: OpeningRule, val boardSize: BoardSize)