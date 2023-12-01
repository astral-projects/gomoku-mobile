package gomoku.domain.login

data class UserInfo(
    val id: Int,
    val username: String,
    val email: String,
    val token: String
)