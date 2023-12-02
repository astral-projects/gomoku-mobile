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
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val service: GameService,
    preferences: PreferencesRepository,
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            service: GameService,
            preferences: PreferencesRepository,
        ) = viewModelFactory {
            initializer { GameViewModel(service, preferences) }
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
            val result = runCatching { service.fetchGameById(gameId) }
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Throws(IllegalStateException::class)
    fun getLocalPlayer(): UserInfo =
        viewModelScope.async {
            preferences.getUserInfo()
                ?: throw IllegalStateException("The user info is not set.")
        }.getCompleted()
}