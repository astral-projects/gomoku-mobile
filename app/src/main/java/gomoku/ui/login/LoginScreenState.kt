package gomoku.ui.login

import gomoku.domain.login.UserInfo
import gomoku.http.utils.recipes.Recipe

/**
 * Represents the login screen state.
 */
sealed class LoginScreenState {
    data object Idle : LoginScreenState()
    data class FetchRecipes(
        val recipes: List<Recipe> = emptyList(),
        val isFetched: Boolean = false
    ) : LoginScreenState()

    data class Login(val userInfo: UserInfo? = null, val isLoggedIn: Boolean = false) :
        LoginScreenState()

    data class LoginFailed(val error: Throwable) : LoginScreenState()
    data class Error(val error: Throwable) : LoginScreenState()
}