package gomoku.http

import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.UserServiceInterface
import gomoku.domain.service.utils.Method
import gomoku.domain.service.utils.callApi
import gomoku.domain.service.utils.recipes.findRecipeUri
import gomoku.infrastructure.PreferencesDataStore

/**
 * Implements the behavior of interface [UserServiceInterface]-
 */
class UserService(
    val preferences: PreferencesDataStore
) : UserServiceInterface {

    override suspend fun login(
        username: String,
        password: String
    ): UserInfo {
        val uri = findRecipeUri(preferences, "login")
        return callApi(
            Method.POST, uri, mapOf(
                "username" to username,
                "password" to password
            )
        )
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Int {
        val uri = findRecipeUri(preferences, "register")
        return callApi(
            Method.POST, uri, mapOf(
                "username" to username,
                "email" to email,
                "password" to password
            )
        )
    }

    override suspend fun searchUsers(term: Term): List<UserStats> {
        TODO()
    }

    override suspend fun fetchUsersStats(page: Int): List<UserStats> {
        TODO()
    }

    override suspend fun fetchUserStats(userId: Int): UserStats {
        TODO()
    }
}