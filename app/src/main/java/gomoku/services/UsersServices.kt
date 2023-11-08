package gomoku.services

import gomoku.leaderboard.domain.PlayerInfo
import gomoku.leaderboard.domain.RankingInfo

interface UsersServices {
    suspend fun fetchUsers(): List<PlayerInfo>
    suspend fun fetchUser(id: Int): PlayerInfo
    suspend fun fetchUsersStats(): List<RankingInfo>
}