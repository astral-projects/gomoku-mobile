package gomoku.ui.game

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.game.match.Game
import gomoku.domain.game.moves.Move
import gomoku.domain.idle
import gomoku.domain.loaded
import gomoku.domain.loading
import gomoku.domain.service.game.GameService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val service: GameService,
    preferences: PreferencesRepository,
    private val gamesServiceHttp: GameService
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            service: GameService,
            preferences: PreferencesRepository,
            gamesServiceHttp: GameService,
        ) = viewModelFactory {
            initializer { GameViewModel(service, preferences, gamesServiceHttp) }
        }
    }

    val game: Flow<IOState<Game>>
        get() = _gameFlow

    private val _gameFlow: MutableStateFlow<IOState<Game>> = MutableStateFlow(idle())

    fun fetchGameById(gameId: String) {
        check(_gameFlow.value is Idle) { "The view model is not in the idle state." }
        Log.v(TAG, "goes to LoadingState")
        _gameFlow.value = loading()
        viewModelScope.launch {
            Log.v(TAG, "fetch game")
            Log.v(TAG, preferences.getUserInfo().toString())
            val result = runCatching { gamesServiceHttp.fetchGameById("54") }
            Log.v(TAG, "exit fetch game")
            _gameFlow.value = loaded(result)
        }
    }

    fun makeMove(gameId: String, move: Move) {
        check(_gameFlow.value is Idle) { "The view model is not in the idle state." }
        _gameFlow.value = loading()
        viewModelScope.launch {
            val result = runCatching { service.makeMove(gameId, move) }
            _gameFlow.value = loaded(result)
        }
    }
}