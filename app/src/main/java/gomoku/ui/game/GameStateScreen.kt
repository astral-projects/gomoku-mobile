package gomoku.ui.game

import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loading
import gomoku.domain.game.match.Game

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
 * Converts a [IOState] to a [GameStateScreen].
 */
fun IOState<Game?>.toGameStateScreen(): GameStateScreen {
    return when (this) {
        is Idle -> GameStateScreen.IDLE
        is Loading -> GameStateScreen.LOADING
        is Error -> GameStateScreen.ERROR
        else -> GameStateScreen.LOADED
    }
}