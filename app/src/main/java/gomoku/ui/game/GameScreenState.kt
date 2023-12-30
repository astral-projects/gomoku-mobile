package gomoku.ui.game

import gomoku.domain.game.match.Game

fun GameScreenState.toGameStateScreen(): GameScreenState {
    return when (this) {
        is GameScreenState.Idle -> GameScreenState.Idle
        is GameScreenState.Loading -> GameScreenState.Loading
        is GameScreenState.GameLoadedAndYourTurn -> GameScreenState.GameLoadedAndYourTurn(game)
        is GameScreenState.GameLoadedAndNotYourTurn -> GameScreenState.GameLoadedAndNotYourTurn(game)
        is GameScreenState.FetchFailed -> GameScreenState.FetchFailed(error)
        else -> GameScreenState.Error(IllegalStateException("Invalid state."))
    }
}

fun GameScreenState.isLoading(): Boolean {
    return this is GameScreenState.Loading
}

fun GameScreenState.isIdle(): Boolean {
    return this is GameScreenState.Idle
}

/**
 * Represents the login screen state.
 */
sealed class GameScreenState {
    data object Idle : GameScreenState()
    data object Loading : GameScreenState()
    data class GameLoadedAndYourTurn(val game: Game? = null, val message: String? = null) :
        GameScreenState()

    data class GameLoadedAndNotYourTurn(val game: Game? = null) :
        GameScreenState()

    data class GameFinished(val game: Game? = null) :
        GameScreenState()

    data class FetchFailed(val error: Throwable) : GameScreenState()
    data class Error(val error: Throwable) : GameScreenState()
}