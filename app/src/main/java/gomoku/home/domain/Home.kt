package gomoku.home.domain

object Home {
    const val GAME_NAME = "Gomoku Royale"
    fun welcome(username: String) = "Welcome back, $username!"
}