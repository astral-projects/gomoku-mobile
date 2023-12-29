package gomoku.ui.leaderboard

import gomoku.domain.leaderboard.RankingInfo
import gomoku.domain.leaderboard.UserStats

sealed class LeaderBoardScreenState {
    data object Idle : LeaderBoardScreenState()
    data class Loading(val usersStats: List<UserStats>? = null) : LeaderBoardScreenState()
    data class UsersStatsLoaded(val usersStats: List<UserStats>) :
        LeaderBoardScreenState()

    data class Error(val message: Throwable) : LeaderBoardScreenState()
    data object Logout : LeaderBoardScreenState()
}

fun LeaderBoardScreenState.extractUsers(): List<RankingInfo> =
    when (this) {
        is LeaderBoardScreenState.Loading -> usersStats ?: emptyList()
        is LeaderBoardScreenState.UsersStatsLoaded -> usersStats
        else -> emptyList()
    }.map { it.toRankingInfo() }
