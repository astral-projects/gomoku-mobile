package gomoku.login

data class User(
    val id: Int,
    val username: String,
    val token: String,
    val email: String,
)