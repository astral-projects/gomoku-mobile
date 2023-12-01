package gomoku.domain.game

data class Lobby(
    val id: Int,
    val playerId: Int,
    val variantId: Int,
    val createdAt: String,
)
