package gomoku.home.domain

/**
 * Represents data centralization for the home screen.
 */
object Home {
    const val GAME_NAME = "Gomoku Royale"
    fun welcome(username: String) = "Welcome back, $username!"
}