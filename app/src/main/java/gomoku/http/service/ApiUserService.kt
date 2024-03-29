package gomoku.http.service

import android.util.Log
import gomoku.domain.leaderboard.PaginatedResult
import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.UserService
import gomoku.http.media.siren.SirenModel
import gomoku.http.models.users.LoginInputModel
import gomoku.http.models.users.LoginOutputModel
import gomoku.http.models.users.LoginUserOutputModel
import gomoku.http.models.users.RegisterInputModel
import gomoku.http.models.users.RegisterOutputModel
import gomoku.http.models.users.UserStatsOutputModel
import gomoku.http.models.users.UsersStatsOutputModel
import gomoku.http.utils.Method
import gomoku.http.utils.callApi
import gomoku.http.utils.convertTemplateToUrl
import gomoku.http.utils.recipes.findRecipeUri
import gomoku.infrastructure.PreferencesDataStore
import pdm.gomoku.R

/**
 * Implements the behavior of interface [UserService].
 */
class ApiUserService(
    val preferences: PreferencesDataStore,
) : UserService {

    companion object {
        /**
         * A list of available icons.
         */
        val availableIcons = listOf(
            R.drawable.man,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.woman,
            R.drawable.woman2,
            R.drawable.woman3,
            R.drawable.woman4,
            R.drawable.woman5
        )

        const val FIRST_PAGE = 1
        const val ITEMS_PER_PAGE = 20
    }

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
            iconId = availableIcons.random()
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

    override suspend fun searchUsers(term: Term): PaginatedResult<UserStats> {
        val recipe = findRecipeUri(preferences, "users/search")
        Log.v("searchUsers", "recipe in ApiUserService: $recipe")
        val uri = convertTemplateToUrl(
            recipe, mapOf(
                "term" to term.value,
                "page" to "$FIRST_PAGE",
                "itemsPerPage" to "50"
            )
        )
        Log.v("searchUsers", "uri: $uri")
        val res: SirenModel<UsersStatsOutputModel, Unit> =
            callApi<Unit, SirenModel<UsersStatsOutputModel, Unit>>(url = uri)
        Log.v("searchUsers", res.properties.items.toString())
        return toPaginatedResult(res)
    }

    override suspend fun fetchUsersStats(url: String?): PaginatedResult<UserStats> {
        val recipe = findRecipeUri(preferences, "users/stats")
        Log.v("fetchUsersStats", "recipe in ApiUserService: $recipe")
        if (url == null) {
            // fetch first page
            val uri = convertTemplateToUrl(
                recipe, mapOf(
                    "page" to "$FIRST_PAGE",
                    "itemsPerPage" to "$ITEMS_PER_PAGE"
                )
            )
            Log.v("fetchUsersStats", uri)
            val res: SirenModel<UsersStatsOutputModel, Unit> =
                callApi<Unit, SirenModel<UsersStatsOutputModel, Unit>>(url = uri)
            Log.v("fetchUsersStats", res.properties.items.toString())
            val value = toPaginatedResult(res)
            Log.v("fetchUsersStats", value.toString())
            return value
        } else {
            // fetch page by relation link
            val res: SirenModel<UsersStatsOutputModel, Unit> =
                callApi<Unit, SirenModel<UsersStatsOutputModel, Unit>>(url = url)
            Log.v("fetchUsersStats", res.properties.items.toString())
            return toPaginatedResult(res)
        }
    }

    override suspend fun fetchUsersStats(page: Int): PaginatedResult<UserStats> {
        TODO("Not yet implemented")
    }

    private fun toPaginatedResult(res: SirenModel<UsersStatsOutputModel, Unit>): PaginatedResult<UserStats> {
        val items = res.properties.items.map { user ->
            UserStats(
                id = user.id.value,
                username = user.username.value,
                points = user.points.value,
                rank = user.rank.value,
                gamesPlayed = user.gamesPlayed.value,
                wins = user.wins.value,
                draws = user.draws.value,
                losses = user.losses.value,
                iconId = availableIcons.random()
            )
        }
        return PaginatedResult(
            items = items,
            firstPageUrl = res.links.firstOrNull { it.rel.firstOrNull() == "first" }?.href,
            nextPageUrl = res.links.firstOrNull { it.rel.firstOrNull() == "next" }?.href,
            previousPageUrl = res.links.firstOrNull { it.rel.firstOrNull() == "prev" }?.href,
            lastPageUrl = res.links.firstOrNull { it.rel.firstOrNull() == "last" }?.href,
        )
    }

    override suspend fun fetchUserStats(userId: Int): UserStats {
        val template = findRecipeUri(preferences, "user/stats")
        val uri = convertTemplateToUrl(
            template, mapOf(
                "user_id" to "$userId"
            )
        )
        Log.v("fetchUserStatsUnique", uri)
        val res: SirenModel<UserStatsOutputModel, Unit> =
            callApi<Unit, SirenModel<UserStatsOutputModel, Unit>>(url = uri)
        Log.v("fetchUserStatsUnique", res.properties.toString())
        val user = res.properties
        return UserStats(
            id = user.id.value,
            username = user.username.value,
            points = user.points.value,
            rank = user.rank.value,
            gamesPlayed = user.gamesPlayed.value,
            wins = user.wins.value,
            draws = user.draws.value,
            losses = user.losses.value,
            iconId = availableIcons.random()
        )
    }

    override suspend fun logout(token: String) {
        val uri = findRecipeUri(preferences, "logout")
        return callApi<Unit, Unit>(
            method = Method.POST, url = uri, token = token
        )
    }

}
