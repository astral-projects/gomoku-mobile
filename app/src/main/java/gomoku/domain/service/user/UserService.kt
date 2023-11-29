package gomoku.domain.service.user

import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo

interface UserService {
    suspend fun login(
        username: String,
        password: String
    ): UserInfo

    suspend fun register(
        username: String,
        email: String,
        password: String
    ): Int

    suspend fun searchUsers(term: Term): List<UserStats>
    suspend fun fetchUsersStats(page: Int): List<UserStats>
    suspend fun fetchUserStats(userId: Int): UserStats
}