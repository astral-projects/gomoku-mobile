package gomoku.leaderboard.user.domain

class UserStats(
    val id: Int,
    val username: String,
    val email: String,
    val points: Int,
    val rank: Int,
    val gamesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int
)