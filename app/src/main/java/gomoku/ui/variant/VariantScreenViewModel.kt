package gomoku.ui.variant

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.Lobby
import gomoku.domain.service.game.GameService
import gomoku.domain.service.variant.VariantService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VariantScreenViewModel(
    private val service: VariantService,
    private val gameService: GameService,
    preferences: PreferencesRepository,
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            serviceVariant: VariantService,
            gameService: GameService,
            preferences: PreferencesRepository,
        ) = viewModelFactory {
            initializer {
                VariantScreenViewModel(
                    serviceVariant, gameService, preferences
                )
            }
        }
    }

    val stateFlow: Flow<VariantScreenState>
        get() = _stateFlow.asStateFlow()

    private val _stateFlow: MutableStateFlow<VariantScreenState> =
        MutableStateFlow(VariantScreenState.Idle)

    fun fetchVariants() {
        check(_stateFlow.value is VariantScreenState.Idle) { "The view model is not in the idle state." }
        _stateFlow.value = VariantScreenState.FetchVariants()
        viewModelScope.launch {
            val storedVariants = preferences.getVariants()
            if (storedVariants != null) {
                _stateFlow.value = VariantScreenState.FetchVariants(storedVariants, true)
            } else {
                val result = runCatching { service.fetchVariants() }
                preferences.setVariants(result.getOrThrow())
                _stateFlow.value = VariantScreenState.FetchVariants(result.getOrThrow(), true)
            }
        }
    }

    fun findGame(variantId: Int) {
        check(_stateFlow.value is VariantScreenState.FetchVariants) { "The view model is did not fetch the variants yet." }
        _stateFlow.value = VariantScreenState.FindGame(variantId)
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${VariantScreenViewModel::class.java.simpleName} is null." }
            val result = runCatching { gameService.findGame(variantId, userInfo) }
            if (result.isSuccess) {
                when (val res = result.getOrThrow()) {
                    is Lobby -> {
                        _stateFlow.value = VariantScreenState.WaitingInLobby(res.id)
                    }

                    is Game -> {
                        _stateFlow.value = VariantScreenState.JoinGame(res.id)
                    }
                }
            } else {
                _stateFlow.value = VariantScreenState.Error(
                    result.exceptionOrNull() ?: Exception("Unknown error.")
                )
            }
        }
    }

    /**
     * Starts polling the lobby with the specified id.
     * @param lobbyId The id of the lobby to poll.
     */
    fun startPollingLobby(lobbyId: Int) {
        check(_stateFlow.value is VariantScreenState.WaitingInLobby) { "The view model is not in a waiting in lobby state." }
        _stateFlow.value = VariantScreenState.WaitingInLobby(lobbyId, true)
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${VariantScreenViewModel::class.java.simpleName} is null." }
            val result = runCatching { gameService.waitingInLobby(lobbyId, userInfo.token) }
            delay(5000)
            if (result.isSuccess) {
                // poll again
                if (result.getOrThrow().first == true) {
                    _stateFlow.value = VariantScreenState.WaitingInLobby(lobbyId)
                } else {
                    // navigate to the game screen
                    _stateFlow.value = VariantScreenState.JoinGame(result.getOrThrow().second)
                }
            } else {
                _stateFlow.value = VariantScreenState.Error(
                    result.exceptionOrNull() ?: Exception("Unknown error.")
                )
            }
        }
    }

    /**
     * Attempts to exit the lobby with the specified id.
     * @param lobbyId The id of the lobby to exit.
     */
    fun exitLobby(lobbyId: Int) {
        check(_stateFlow.value is VariantScreenState.WaitingInLobby) { "The view model is not in a waiting in lobby state." }
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${VariantScreenViewModel::class.java.simpleName} is null." }
            val result = runCatching { gameService.exitLobby(lobbyId, userInfo.token) }
            if (result.isSuccess) {
                _stateFlow.value = VariantScreenState.ExitLobby
            } else {
                _stateFlow.value = VariantScreenState.Error(
                    result.exceptionOrNull() ?: Exception("Unknown error.")
                )
            }
        }
    }

    /**
     * Resets the view model to the idle state.
     */
    fun resetToIdle() {
        check(_stateFlow.value is VariantScreenState.JoinGame || _stateFlow.value is VariantScreenState.ExitLobby) { "The view model is not in a valid state." }
        _stateFlow.value = VariantScreenState.Idle
    }
}
