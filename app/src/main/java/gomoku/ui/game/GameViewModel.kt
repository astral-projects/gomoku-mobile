package gomoku.ui.game

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loaded
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
    preferences: PreferencesRepository
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

    fun fetchGameById(gameId: Int) {
        check(_gameFlow.value is Idle) { "The view model is not in the idle state." }
        _gameFlow.value = loading()
        viewModelScope.launch {
            val result = runCatching { service.fetchGameById(gameId) }
            _gameFlow.value = loaded(result)
        }
    }

    fun makeMove(gameId: Int, move: Move) {
        check(_gameFlow.value is Loaded) { "The view model is not in the loaded state." }
        _gameFlow.value = loading()
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()!!
            val result = runCatching { service.makeMove(gameId, move, userInfo.token) }
            _gameFlow.value = loaded(result)
        }
    }
}