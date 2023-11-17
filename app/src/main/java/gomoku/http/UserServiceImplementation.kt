package gomoku.http

import gomoku.leaderboard.user.UserService
import gomoku.leaderboard.user.domain.UserStats

class UserServiceImplementation(
    private val provider: List<UserService>
) : UserService {
    override suspend fun fetchUser(userId: Int): UserStats {
        val users = fetchUsers()
        return users.first { it.id == userId }
    }

    override suspend fun fetchUsers(): List<UserStats> {
        val index = provider.indices.random()
        return provider[index].fetchUsers()
    }
}