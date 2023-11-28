package gomoku.game.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.Idle
import gomoku.LoadState
import gomoku.Loaded
import gomoku.ThemeRepository
import gomoku.UserInfoRepository
import gomoku.game.GameService
import gomoku.game.domain.Game
import gomoku.game.domain.moves.Move
import gomoku.idle
import gomoku.loaded
import gomoku.loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val service: GameService,
    private val userRepo: UserInfoRepository,
    private val themeRepository: ThemeRepository
) : ViewModel() {

    companion object {
        fun factory(
            service: GameService,
            userRepo: UserInfoRepository,
            themeRepo: ThemeRepository
        ) = viewModelFactory {
            initializer { GameViewModel(service, userRepo, themeRepo) }
        }
    }


    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = themeRepository.getIsDarkTheme()
        }
    }


    val game: Flow<LoadState<Game?>>
        get() = _gameFlow

    private val _gameFlow: MutableStateFlow<LoadState<Game?>> = MutableStateFlow(idle())

    //TODO(This route is not implement just a simple simulation , waiting for the backend)
    fun fetchGame(gameId: String) {
        if (_gameFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        Log.v(TAG, "goes to LoadingState")
        _gameFlow.value = loading()
        viewModelScope.launch {
            Log.v(TAG, "fetch game")
            Log.v(TAG, userRepo.getUserInfo().toString())
            val result = runCatching { service.fetchGame() }
            Log.v(TAG, "exit fetch game")
            _gameFlow.value = loaded(result)
        }
    }


    fun makeMove(move: Move) {

    }

    /**
     * Resets the view model to the idle state. From the idle state, the user information
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_gameFlow.value !is Loaded)
            throw IllegalStateException("The view model is not in the idle state.")
        _gameFlow.value = idle()
    }
}