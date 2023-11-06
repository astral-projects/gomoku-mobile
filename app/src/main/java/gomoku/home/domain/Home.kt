package gomoku.home.domain

/**
 * Represents data and functionality related to the home screen.
 */
object Home {
    const val GAME_NAME = "Gomoku Royale"
    const val FIND_GAME = "Find a Match"
    const val LEADERBOARD = "Leaderboard"
    const val ABOUT = "About"
    const val LOGOUT = "Logout"
    fun welcome(username: String) = "Welcome back, $username!"
}