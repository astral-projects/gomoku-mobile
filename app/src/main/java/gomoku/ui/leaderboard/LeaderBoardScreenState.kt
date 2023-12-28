package gomoku.ui.leaderboard

import gomoku.domain.leaderboard.UserStats

sealed class LeaderBoardScreenState {
    data object Idle : LeaderBoardScreenState()
    data class Loading(val page: Int) : LeaderBoardScreenState()
    data class UsersStatsLoaded(val usersStats: List<UserStats>, val isLastPage: Boolean) :
        LeaderBoardScreenState()

    data class UserStatsLoaded(val userStats: UserStats) : LeaderBoardScreenState()
    data class SearchUsers(val term: String) : LeaderBoardScreenState()
    data class Error(val message: Throwable) : LeaderBoardScreenState()
}
