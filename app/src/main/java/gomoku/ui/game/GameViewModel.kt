package gomoku.ui.game

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.game.Game
import gomoku.domain.game.moves.Move
import gomoku.domain.idle
import gomoku.domain.loaded
import gomoku.domain.loading
import gomoku.domain.service.game.GameService
import gomoku.domain.storage.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val service: GameService,
    private val preferences: PreferencesRepository,
) : ViewModel() {

    companion object {
        fun factory(
            service: GameService,
            preferences: PreferencesRepository,
        ) = viewModelFactory {
            initializer { GameViewModel(service, preferences) }
        }
    }

    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = preferences.isInDarkMode()
        }
    }

    val game: Flow<IOState<Game?>>
        get() = _gameFlow

    private val _gameFlow: MutableStateFlow<IOState<Game?>> = MutableStateFlow(idle())

    fun fetchGame(gameId: String) {
        if (_gameFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        Log.v(TAG, "goes to LoadingState")
        _gameFlow.value = loading()
        viewModelScope.launch {
            Log.v(TAG, "fetch game")
            Log.v(TAG, preferences.getUserInfo().toString())
            val result = runCatching { service.fetchGame() }
            Log.v(TAG, "exit fetch game")
            _gameFlow.value = loaded(result)
        }
    }

    fun makeMove(move: Move) {
        TODO()
    }
}