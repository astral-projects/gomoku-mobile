package gomoku.leaderboard.user

import android.util.Log
import gomoku.leaderboard.user.domain.UserStats
import kotlinx.coroutines.delay

interface UserService {
    suspend fun fetchUsers(): List<UserStats>
    suspend fun fetchUser(userId: Int): UserStats
}

class FetchUserException(message: String, cause: Throwable? = null) : Exception(message, cause)

class FakeUserServices : UserService {

    private val users = listOf(
        UserStats(1, "John", "john@example.com", 1, 1, 1, 1, 1, 1),
        UserStats(2, "Wick", "wick@example.com", 1, 1, 1, 1, 1, 1),
        UserStats(3, "Peter", "peter@example.com", 1, 1, 1, 1, 1, 1),
        UserStats(4, "Parker", "parker@example.com", 1, 1, 1, 1, 1, 1),
        UserStats(1, "Tony", "stark@example.com", 1, 1, 1, 1, 1, 1),
        UserStats(1, "William", "jesus@example.com", 1, 1, 1, 1, 1, 1),
        UserStats(1, "Neymar", "juniir@example.com", 1, 1, 1, 1, 1, 1),

        )

    override suspend fun fetchUser(userId: Int): UserStats {
        Log.v("User", "fetching user...")
        delay(3000)
        Log.v("User", "fetched user")
        return users.first { it.id == userId }

    }

    override suspend fun fetchUsers(): List<UserStats> {
        Log.v("Users", "fetching users...")
        delay(5000)
        Log.v("Users", "fetched users")
        return users
    }

}
