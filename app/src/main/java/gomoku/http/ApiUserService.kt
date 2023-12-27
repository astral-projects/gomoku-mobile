package gomoku.http

import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.domain.service.media.siren.SirenModel
import gomoku.domain.service.user.UserServiceInterface
import gomoku.domain.service.utils.Method
import gomoku.domain.service.utils.callApi
import gomoku.domain.service.utils.recipes.findRecipeUri
import gomoku.http.models.users.LoginInputModel
import gomoku.http.models.users.LoginOutputModel
import gomoku.http.models.users.LoginUserOutputModel
import gomoku.http.models.users.RegisterInputModel
import gomoku.http.models.users.RegisterOutputModel
import gomoku.infrastructure.PreferencesDataStore

/**
 * Implements the behavior of interface [UserServiceInterface]-
 */
class ApiUserService(
    val preferences: PreferencesDataStore
) : UserServiceInterface {

    override suspend fun login(
        username: String,
        password: String
    ): UserInfo {
        val uri = findRecipeUri(preferences, "login")
        val sirenRes: SirenModel<LoginOutputModel, LoginUserOutputModel> = callApi(
            method = Method.POST, url = uri, data = LoginInputModel(username, password)
        )

        val token = sirenRes.properties.token
        val userInfo = sirenRes.entities.first().properties
        return UserInfo(
            id = userInfo.id.value,
            username = userInfo.username.value,
            email = userInfo.email.value,
            token = token,
            iconId = 0
        )
    }

    override suspend fun register(
        username: String,
        email: String,
        password: String
    ): Int {
        val uri = findRecipeUri(preferences, "register")
        val res: SirenModel<RegisterOutputModel, Unit> = callApi(
            Method.POST, uri, RegisterInputModel(username, email, password)
        )
        return res.properties.id
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

    override suspend fun logout(token: String) {
        val uri = findRecipeUri(preferences, "logout")
        return callApi<Unit, Unit>(
            method = Method.POST, url = uri, token = token
        )
    }

}
