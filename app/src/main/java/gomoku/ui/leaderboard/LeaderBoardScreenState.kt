package gomoku.ui.leaderboard

import gomoku.domain.leaderboard.PaginatedResult
import gomoku.domain.leaderboard.RankingInfo
import gomoku.domain.leaderboard.UserStats

sealed class LeaderBoardScreenState {
    data object Idle : LeaderBoardScreenState()
    data class Loading(val usersStats: List<UserStats>? = null) : LeaderBoardScreenState()
    data class Loaded(val usersStats: PaginatedResult<UserStats>) :
        LeaderBoardScreenState()

    data class Error(val message: Throwable) : LeaderBoardScreenState()
    data object Logout : LeaderBoardScreenState()
}

fun LeaderBoardScreenState.extractUsersStats(): List<UserStats> =
    when (this) {
        is LeaderBoardScreenState.Loading -> usersStats ?: emptyList()
        is LeaderBoardScreenState.Loaded -> usersStats.items
        else -> emptyList()
    }

fun LeaderBoardScreenState.extractUsersWithRanking(): List<RankingInfo> =
    extractUsersStats().map { it.toRankingInfo() }
