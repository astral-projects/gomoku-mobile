package gomoku.ui.register

import gomoku.domain.Fail
import gomoku.domain.Idle
import gomoku.domain.LoadState
import gomoku.domain.Loading

/**
 * Represents the register screen state.
 */
enum class RegisterScreenState {
    Idle,
    Loading,
    Loaded,
    Fail
}

/**
 * Returns true is the screen state is idle.
 */
fun RegisterScreenState.isIdle() = this == RegisterScreenState.Idle

/**
 * Returns true is the screen state is loading.
 */
fun RegisterScreenState.isLoading() = this == RegisterScreenState.Loading

/**
 * Returns true is the screen state is loaded.
 */
fun RegisterScreenState.isLoaded() = this == RegisterScreenState.Loaded

/**
 * Returns true is the screen state is failed.
 */
fun RegisterScreenState.isFail() = this == RegisterScreenState.Fail

/**
 * Returns the screen state based on the user authentication state.
 */
fun LoadState<Int>.toRegisterScreenState(): RegisterScreenState = when (this) {
    is Idle -> RegisterScreenState.Idle
    is Loading -> RegisterScreenState.Loading
    is Fail -> RegisterScreenState.Fail
    else -> RegisterScreenState.Loaded
}