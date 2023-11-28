package gomoku.leaderboard.user

import android.util.Log
import gomoku.leaderboard.user.domain.UserStats
import gomoku.login.UserInfo
import gomoku.login.domain.Username
import kotlinx.coroutines.delay

interface UserService {
    suspend fun fetchLogin(username: String, password: String): UserInfo
    suspend fun fetchUsers(): List<UserStats>
    suspend fun fetchUser(userId: Int): UserStats
    suspend fun fetchUsersStats(userId: Int): List<UserStats>
    suspend fun fetchUsersStats(): List<UserStats>

    suspend fun fetchCreateUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): UserInfo
}

class FetchUserException(message: String, cause: Throwable? = null) : Exception(message, cause)

class FakeUserServices : UserService {

    private var users = mutableListOf(
        UserStats(1, "John Loa", 1, 1, 1, 1, 1, 1),
        UserStats(2, "Wick Main", 1, 1, 1, 1, 1, 1),
        UserStats(3, "Peter Pan", 1, 1, 1, 1, 1, 1),
        UserStats(4, "Jeffery Dahmer", 1, 1, 1, 1, 1, 1),
        UserStats(5, "Walter White", 1, 1, 1, 1, 1, 1),
        UserStats(6, "William Carvalho", 1, 1, 1, 1, 1, 1),
        UserStats(7, "Neymar Chorão", 1, 1, 1, 1, 1, 1),
    )

    private var usersTokens = mutableListOf(
        UserInfo(1, Username("John Loa"), "qweqweqwewqeeqw", "john@example.com"),
        UserInfo(2, Username("Wick Main"), "qweqweqwEGGREGER", "wick@example.com"),
        UserInfo(3, Username("Peter Pan"), "fweqg345212", "peter@example.com"),
        UserInfo(4, Username("Jeffery Dahmer"), "IeatY@example.com", "IeatY@gaymail.com"),
        UserInfo(5, Username("Walter White"), "qweqweq213632tfreqw", "bestCleaner@gmail.com"),
        UserInfo(
            6,
            Username("William Carvalho"),
            "qw34265436eqwewqeeqw",
            "fasterThanFlash@gamil.com"
        ),
        UserInfo(7, Username("Neymar Chorão"), "4352tr", "juniir@example.com"),

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

    override suspend fun fetchUsersStats(): List<UserStats> {
        Log.v("Users Stats", "fetching users rankings...")
        delay(5000)
        Log.v("Users", "fetched users rankings")
        val l = users.map { userStats ->
            UserStats(
                id = userStats.id,
                username = userStats.username,
                rank = userStats.rank,
                points = userStats.points,
                wins = userStats.wins,
                losses = userStats.losses,
                draws = userStats.draws,
                gamesPlayed = userStats.gamesPlayed
            )
        }
        return l
    }

    override suspend fun fetchUsersStats(userId: Int): List<UserStats> {
        TODO("Not yet implemented")
    }

    //TODO(Password is not being checked)
    override suspend fun fetchLogin(username: String, password: String): UserInfo {
        Log.v("User", "logging in...")
        delay(4000)
        Log.v("User", "logged in")
        return usersTokens.first { it.username.value == username }
    }

    override suspend fun fetchCreateUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ): UserInfo {
        Log.v("User", "creating user...")
        delay(4000)
        Log.v("User", "created user")
        val newId = users.last().id + 1
        users.add(UserStats(newId, username, 0, 0, 0, 0, 0, 0))
        val token = password.hashCode().toString()
        usersTokens.add(UserInfo(newId, Username(username), token, email))
        if (username == "Joao Andre") throw FetchUserException("User already exists")
        return UserInfo(newId, Username(username), token, email)
    }

}
