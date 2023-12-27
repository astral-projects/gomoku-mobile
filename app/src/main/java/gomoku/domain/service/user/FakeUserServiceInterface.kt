package gomoku.domain.service.user

import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.domain.service.AbstractFakeService
import gomoku.domain.service.user.errors.FetchUserException
import gomoku.domain.service.user.errors.RegisterUserException
import kotlinx.coroutines.delay

class FakeUserServiceInterface : UserServiceInterface, AbstractFakeService() {

    private var users: MutableList<UserInfo> = (1..NR_PLAYERS)
        .map { playerId ->
            if (playerId == admin.id) return@map admin
            if (playerId == guest.id) return@map guest
            val playerInfo = fakePlayers[(playerId - 1) % fakePlayers.size]
            UserInfo(
                id = playerId,
                username = playerInfo.name,
                token = "token-${playerInfo.name}",
                email = "email@${playerInfo.name}.com",
                iconId = playerInfo.iconId
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
                losses = losses,
                iconId = user.iconId
            )
        }

    override suspend fun login(
        username: String,
        password: String
    ): UserInfo {
        delay(fetchDelay)
        return users.findOrThrow(FetchUserException()) { it.username == username }
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Int {
        delay(fetchDelay)
        if (users.any { it.username == username })
            throw RegisterUserException("Username already taken")
        val newId = userStats.last().id + 1
        val token = password.hashCode().toString()
        users.add(
            UserInfo(
                newId,
                username,
                token,
                email,
                availableIcons[newId % availableIcons.size]
            )
        )
        return newId
    }

    override suspend fun searchUsers(term: Term): List<UserStats> {
        delay(fetchDelay)
        return userStats.filter { it.username.contains(term.value, true) }
    }

    override suspend fun fetchUserStats(userId: Int): UserStats {
        delay(fetchDelay)
        return userStats.findOrThrow(FetchUserException()) { it.id == userId }
    }

    override suspend fun fetchUsersStats(page: Int): List<UserStats> {
        delay(fetchDelay)
        return paginatedRankingInfo(userStats, page)
    }

    override suspend fun logout(token: String) {
        delay(fetchDelay)
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val NR_PLAYERS = 300

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
    }
}