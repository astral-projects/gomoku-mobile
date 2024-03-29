package gomoku.ui.game

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.GameState
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Player
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.delay
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

        private const val FETCH_GAME_DELAY = 1000L
    }

    val stateFlow: Flow<GameScreenState>
        get() = _stateFlow

    private val _stateFlow: MutableStateFlow<GameScreenState> =
        MutableStateFlow(GameScreenState.Idle)

    fun fetchGameById(gameId: Int) {
        check(_stateFlow.value is GameScreenState.Idle) { "The view model is not in the idle state." }
        _stateFlow.value = GameScreenState.Loading
        viewModelScope.launch {
            val result = runCatching { service.fetchGameById(gameId) }
            if (result.isFailure) {
                _stateFlow.value = GameScreenState.FetchFailed(result.exceptionOrNull()!!)
            } else {
                val userInfo = preferences.getUserInfo()
                checkNotNull(userInfo) { "The user info in ${GameViewModel::class.java.simpleName} is null." }
                val game = result.getOrThrow()
                _stateFlow.value = gameState(game, userInfo, result)
            }
        }
    }

    fun makeMove(gameId: Int, move: Move) {
        check(_stateFlow.value is GameScreenState.GameLoadedAndYourTurn) { "The view model is not in the loaded state." }
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${GameViewModel::class.java.simpleName} is null." }
            val result = runCatching { service.makeMove(gameId, move, userInfo.token) }
            if (result.isFailure) {
                _stateFlow.value = GameScreenState.GameLoadedAndYourTurn(
                    (_stateFlow.value as GameScreenState.GameLoadedAndYourTurn).game,
                    result.exceptionOrNull()?.message
                )
            }
            _stateFlow.value = GameScreenState.GameLoadedAndNotYourTurn(result.getOrNull())
        }
    }

    fun startPollingGame(gameId: Int) {
        check(_stateFlow.value is GameScreenState.GameLoadedAndNotYourTurn) { "The view model is not in the loaded state and is your turn." }
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${GameViewModel::class.java.simpleName} is null." }
            val result = runCatching { service.fetchGameById(gameId) }
            delay(FETCH_GAME_DELAY)
            val g = result.getOrThrow()
            _stateFlow.value = gameState(g, userInfo, result)
        }
    }

    fun exitGame(gameId: Int) {
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${GameViewModel::class.java.simpleName} is null." }
            val result = runCatching { service.exitGame(gameId, userInfo.token) }
            if (result.isSuccess) {
                _stateFlow.value = GameScreenState.Idle
            } else {
                _stateFlow.value =
                    GameScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error."))
            }
        }
    }
}

fun gameState(g: Game, userInfo: UserInfo, result: Result<Game>): GameScreenState =
    if (g.state == GameState.FINISHED) {
        GameScreenState.GameFinished(result.getOrNull())
    } else if (g.state == GameState.IN_PROGRESS && g.board.turn != null) {
        if ((userInfo.id == g.host.id && g.board.turn.player == Player.W) || (userInfo.id == g.guest.id && g.board.turn.player == Player.B)) {
            GameScreenState.GameLoadedAndYourTurn(result.getOrNull())
        } else {
            GameScreenState.GameLoadedAndNotYourTurn(result.getOrNull())
        }
    } else {
        GameScreenState.Error(IllegalStateException("Invalid state."))
    }
