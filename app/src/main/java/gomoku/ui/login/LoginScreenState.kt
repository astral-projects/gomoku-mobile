package gomoku.ui.login

import gomoku.domain.Fail
import gomoku.domain.IOState
import gomoku.domain.Loaded
import gomoku.domain.Loading
import gomoku.domain.login.UserInfo

/**
 * Represents the login screen state.
 */
sealed class LoginScreenState {
    data object Loading : LoginScreenState()
    data class FailedLogin(val message: String) : LoginScreenState()
    data object Loaded : LoginScreenState()
    data object Error : LoginScreenState()
}

/**
 * Returns the screen state based on the user authentication state.
 */
fun IOState<UserInfo>.toLoginScreenState(): LoginScreenState = when (this) {
    is Loading -> LoginScreenState.Loading
    is Loaded -> LoginScreenState.Loaded
    is Fail -> LoginScreenState.FailedLogin(message)
    else -> LoginScreenState.Error
}