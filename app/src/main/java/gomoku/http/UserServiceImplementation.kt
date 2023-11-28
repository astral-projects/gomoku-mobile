package gomoku.http

import gomoku.leaderboard.user.UserService
import gomoku.leaderboard.user.domain.UserStats
import gomoku.login.UserInfo

class UserServiceImplementation(
    private val provider: List<UserService>
) : UserService {
    override suspend fun fetchUser(userId: Int): UserStats {
        val users = fetchUsers()
        return users.first { it.id == userId }
    }

    override suspend fun fetchUsersStats(userId: Int): List<UserStats> {
        val users = fetchUsersStats()
        return users.filter { it.id == userId }
    }

    override suspend fun fetchUsersStats(): List<UserStats> {
        val index = provider.indices.random()
        return provider[index].fetchUsersStats()
    }

    override suspend fun fetchUsers(): List<UserStats> {
        val index = provider.indices.random()
        return provider[index].fetchUsers()
    }

    override suspend fun fetchLogin(username: String, password: String): UserInfo {
        val index = provider.indices.random()
        return provider[index].fetchLogin(username, password)
    }

    override suspend fun fetchCreateUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): UserInfo {
        val index = provider.indices.random()
        return provider[index].fetchCreateUser(username, email, password, confirmPassword)
    }
}