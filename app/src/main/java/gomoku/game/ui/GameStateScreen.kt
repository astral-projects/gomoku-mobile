package gomoku.game.ui

import gomoku.Idle
import gomoku.LoadState
import gomoku.Loading
import gomoku.game.domain.Game

/**
 * Represents the game view state.
 */
enum class GameStateScreen {
    LOADING,
    LOADED,
    ERROR,
    IDLE
}

/**
 * Returns true if the game state is idle.
 */
fun GameStateScreen.isIdle() = this == GameStateScreen.IDLE

/**
 * Returns true if the game state is loading.
 */
fun GameStateScreen.isLoading() = this == GameStateScreen.LOADING

/**
 * Returns true if the game state is loaded.
 */
fun GameStateScreen.isLoaded() = this == GameStateScreen.LOADED

/**
 * Returns true if the game state is error.
 */
fun GameStateScreen.isError() = this == GameStateScreen.ERROR

/**
 * Converts a [LoadState] to a [GameStateScreen].
 */
fun LoadState<Game?>.toGameStateScreen(): GameStateScreen {
    return when (this) {
        is Idle -> GameStateScreen.IDLE
        is Loading -> GameStateScreen.LOADING
        is Error -> GameStateScreen.ERROR
        else -> GameStateScreen.LOADED
    }
}