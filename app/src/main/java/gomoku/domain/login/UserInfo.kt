package gomoku.domain.login

import gomoku.domain.leaderboard.PlayerInfo

data class UserInfo(
    val id: Int,
    val username: String,
    val email: String,
    val token: String,
    val iconId: Int
) {
    fun toPlayerInfo() = PlayerInfo(id, username, iconId)
}