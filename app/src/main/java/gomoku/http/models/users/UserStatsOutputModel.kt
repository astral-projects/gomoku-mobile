package gomoku.http.models.users

import gomoku.http.models.users.userInfo.Email
import gomoku.http.models.users.userInfo.Id
import gomoku.http.models.users.userInfo.Username
import gomoku.http.models.users.userStats.Draws
import gomoku.http.models.users.userStats.GamesPlayed
import gomoku.http.models.users.userStats.Losses
import gomoku.http.models.users.userStats.Points
import gomoku.http.models.users.userStats.Rank
import gomoku.http.models.users.userStats.Wins

data class UserStatsOutputModel(
    val id: Id,
    val username: Username,
    val email: Email,
    val points: Points,
    val rank: Rank,
    val gamesPlayed: GamesPlayed,
    val wins: Wins,
    val draws: Draws,
    val losses: Losses
)
