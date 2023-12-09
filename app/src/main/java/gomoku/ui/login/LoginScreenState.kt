package gomoku.ui.login

import gomoku.domain.Fail
import gomoku.domain.IOState
import gomoku.domain.Loaded
import gomoku.domain.Loading
import gomoku.domain.login.UserInfo

/**
 * Represents the login screen state.
 */
enum class LoginScreenState {
    Loading,
    FailedLogin,
    Loaded,
    Error
}

fun LoginScreenState.isLoading() = this == LoginScreenState.Loading
fun LoginScreenState.isFailedLogin() = this == LoginScreenState.FailedLogin
fun LoginScreenState.isLoaded() = this == LoginScreenState.Loaded
fun LoginScreenState.isError() = this == LoginScreenState.Error

/**
 * Returns the screen state based on the user authentication state.
 */
fun IOState<UserInfo>.toLoginScreenState(): LoginScreenState = when (this) {
    is Loading -> LoginScreenState.Loading
    is Loaded -> LoginScreenState.Loaded
    is Fail -> LoginScreenState.FailedLogin
    else -> LoginScreenState.Error
}