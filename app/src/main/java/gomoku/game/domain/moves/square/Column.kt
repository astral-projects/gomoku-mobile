package gomoku.game.domain.moves.square

data class Column(val letter: Char)

// TODO("revisit this code")
fun Int.toColumn() = Column('a' + this)