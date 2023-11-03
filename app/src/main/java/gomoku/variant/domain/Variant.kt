package gomoku.variant.domain

import gomoku.game.domain.board.BoardSize

data class Variant(val name: VariantName, val openingRule: OpeningRule, val boardSize: BoardSize)