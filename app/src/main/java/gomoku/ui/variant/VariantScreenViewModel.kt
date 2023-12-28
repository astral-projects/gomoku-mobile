package gomoku.ui.variant

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.game.match.Lobby
import gomoku.domain.game.match.Match
import gomoku.domain.getOrThrow
import gomoku.domain.idle
import gomoku.domain.loaded
import gomoku.domain.loading
import gomoku.domain.service.game.GameService
import gomoku.domain.service.variant.VariantService
import gomoku.domain.storage.PreferencesRepository
import gomoku.domain.variant.VariantConfig
import gomoku.ui.shared.BaseViewModel
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
                    serviceVariant,
                    gameService,
                    preferences
                )
            }
        }
    }

    val variants: Flow<IOState<List<VariantConfig>>>
        get() = _variantsFlow.asStateFlow()

    private val _variantsFlow: MutableStateFlow<IOState<List<VariantConfig>>> =
        MutableStateFlow(idle())

    val match: Flow<IOState<Match>>
        get() = _matchFlow.asStateFlow()

    private val _matchFlow: MutableStateFlow<IOState<Match>> =
        MutableStateFlow(idle())

    fun fetchVariants() {
        check(_variantsFlow.value is Idle) { "The view model is not in the idle state." }
        _variantsFlow.value = loading()
        viewModelScope.launch {
            val storedVariants = preferences.getVariants()
            if (storedVariants != null) {
                _variantsFlow.value = loaded(Result.success(storedVariants))
            } else {
                val result: Result<List<VariantConfig>> = runCatching { service.fetchVariants() }
                preferences.setVariants(result.getOrThrow())
                _variantsFlow.value = loaded(result)
            }
        }
    }

    fun findGame(variantConfig: VariantConfig) {
        check(_matchFlow.value is Idle) { "The view model is not in the idle state." }
        _matchFlow.value = loading()
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${VariantScreenViewModel::class.java.simpleName} is null." }
            val result: Result<Match> =
                runCatching { gameService.findGame(variantConfig, userInfo) }
            _matchFlow.value = loaded(result)
        }
    }

    fun exitLobby() {
        check(_matchFlow.value is Loaded) { "The view model is not in a loaded state." }
        viewModelScope.launch {
            val userInfo = preferences.getUserInfo()
            checkNotNull(userInfo) { "The user info in ${VariantScreenViewModel::class.java.simpleName} is null." }
            val lobbyId = (_matchFlow.value.getOrThrow() as Lobby).id
            runCatching { gameService.exitLobby(lobbyId, userInfo) }
            resetToIdle()
        }
    }

    /**
     * Resets the view model to the idle state.
     */
    fun resetToIdle() {
        check(_matchFlow.value is Loaded) { "The view model is not in a loaded state." }
        _matchFlow.value = idle()
    }
}
