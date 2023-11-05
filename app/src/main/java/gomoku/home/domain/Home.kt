package gomoku.home.domain

/**
 * Represents data and functionality related to the home screen.
 */
object Home {
    const val GAME_NAME = "Gomoku Royale"
    fun welcome(username: String) = "Welcome back, $username!"
}