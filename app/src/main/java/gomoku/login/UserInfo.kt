package gomoku.login

import gomoku.login.domain.Username

data class UserInfo(
    val id: Int,
    val username: Username,
    val email: String,
    val token: String
)