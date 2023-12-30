package gomoku.http.models.games

import gomoku.http.models.users.userInfo.Id


data class VariantOutputModel(
    val id: Id,
    val name: String,
    val openingRule: String,
    val boardSize: String,
)
