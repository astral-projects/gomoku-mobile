package gomoku.domain.service.user

import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.errors.FetchUserException
import kotlinx.coroutines.delay
import pdm.gomoku.R

class FakeUserService : UserService {

    private val admin = UserInfo(1, "admin", "admin", "admin@gmail.com")

    private var users: MutableList<UserInfo> = (1..NR_PLAYERS)
        .map { playerId ->
            if (playerId == admin.id) return@map admin
            val playerInfo = fakePlayers[(playerId - 1) % fakePlayers.size]
            UserInfo(
                id = playerId,
                username = playerInfo.name,
                token = "token-${playerInfo.name}",
                email = "email@${playerInfo.name}.com"
            )
        }.toMutableList()
        .also { println(it) }

    private val userStats: List<UserStats>
        get() = users.toList().fold(listOf()) { userStats, user ->
            val nPlayers = users.size
            val points = nPlayers - user.id
            val playedGames = nPlayers - user.id
            val wins = playedGames / 2
            val losses = playedGames - wins
            val draws = losses / 2
            userStats + UserStats(
                id = user.id,
                username = user.username,
                points = points,
                rank = user.id,
                gamesPlayed = playedGames,
                wins = wins,
                draws = draws,
                losses = losses
            )
        }

    override suspend fun login(
        username: String,
        password: String
    ): UserInfo {
        delay(FETCH_DELAY)
        return users.findOrThrow { it.username == username }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Int {
        delay(FETCH_DELAY)
        val newId = userStats.last().id + 1
        val token = password.hashCode().toString()
        users.add(UserInfo(newId, username, token, email))
        return newId
    }

    override suspend fun searchUsers(term: Term): List<UserStats> {
        delay(FETCH_DELAY)
        return userStats.filter { it.username.contains(term.value, true) }
    }

    override suspend fun fetchUserStats(userId: Int): UserStats {
        delay(FETCH_DELAY)
        return userStats.findOrThrow { it.id == userId }
    }

    override suspend fun fetchUsersStats(page: Int): List<UserStats> {
        delay(FETCH_DELAY)
        return paginatedRankingInfo(userStats, page)
    }

    companion object {
        private const val FETCH_DELAY = 3000L
        private const val PAGE_SIZE = 20
        private const val NR_PLAYERS = 300

        /**
         * A list of fake players.
         */
        val fakePlayers = listOf(
            PlayerInfo("Hosea Matthews", R.drawable.man),
            PlayerInfo("John Marston", R.drawable.man2),
            PlayerInfo("Arthur Morgan", R.drawable.man3),
            PlayerInfo("Dutch van der Linde", R.drawable.man4),
            PlayerInfo("Bill Williamson", R.drawable.man5),
            PlayerInfo("Abigail Roberts", R.drawable.woman),
            PlayerInfo("Sadie Adler", R.drawable.woman2),
            PlayerInfo("Karen Jones", R.drawable.woman3),
            PlayerInfo("Mary-Beth Gaskill", R.drawable.woman4),
            PlayerInfo("Tilly Jackson", R.drawable.woman5)
        )

        /**
         * Returns a sublist of [list] with [PAGE_SIZE] elements starting at [page] number.
         * @param list The list to be paginated.
         * @param page The page number to retrieve.
         * @return A sublist of [list].
         * @throws IllegalArgumentException if [page] is less than 1.
         */
        @Throws(IllegalArgumentException::class)
        private fun <R> paginatedRankingInfo(list: List<R>, page: Int): List<R> {
            require(page > 0) { "Page must be greater than 0." }
            val start = (page - 1) * PAGE_SIZE
            val end = start + PAGE_SIZE
            val actualEnd = end.coerceAtMost(list.size)
            return list.subList(start.coerceAtMost(actualEnd), actualEnd)
        }

        /**
         * Returns the first element matching the given [predicate], or throws [FetchUserException]
         * if no such element was found.
         * @param predicate The predicate to be matched.
         */
        @Throws(FetchUserException::class)
        private inline fun <T> Iterable<T>.findOrThrow(predicate: (T) -> Boolean): T {
            for (element in this) if (predicate(element)) return element
            throw FetchUserException("User not found")
        }
    }
}