package gomoku.ui.variant

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.Idle
import gomoku.domain.LoadState
import gomoku.domain.Loaded
import gomoku.domain.game.Game
import gomoku.domain.idle
import gomoku.domain.loaded
import gomoku.domain.loading
import gomoku.domain.saved
import gomoku.domain.service.game.GameService
import gomoku.domain.service.variant.VariantService
import gomoku.domain.storage.PreferencesRepository
import gomoku.domain.variant.VariantConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VariantScreenViewModel(
    private val service: VariantService,
    private val gameService: GameService,
    private val preferences: PreferencesRepository,
) : ViewModel() {

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

    val variants: Flow<LoadState<List<VariantConfig>?>>
        get() = _variantsFlow.asStateFlow()

    private val _variantsFlow: MutableStateFlow<LoadState<List<VariantConfig>?>> =
        MutableStateFlow(idle())

    val game: Flow<LoadState<Game?>>
        get() = _gameFlow.asStateFlow()

    private val _gameFlow: MutableStateFlow<LoadState<Game?>> =
        MutableStateFlow(idle())

    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun fetchVariants() {
        if (_variantsFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _variantsFlow.value = loading()

        viewModelScope.launch {
            Log.v("ViewModelVariants", "fetching variants in view model...")
            if (preferences.getVariants() != null) {
                _variantsFlow.value = loaded(Result.success(preferences.getVariants()))
            } else {
                val result: Result<List<VariantConfig>> = runCatching { service.fetchVariants() }
                Log.v("ViewModelVariants", "fetched variants in view model")
                preferences.setVariants(result.getOrNull()!!)
                _variantsFlow.value = saved(result)
                _variantsFlow.value = loaded(result)
            }
        }
    }

    fun findGame(variantConfig: VariantConfig) {
        if (_gameFlow.value !is Idle) {
            throw IllegalStateException("The view model is not in the idle state.")
        }
        _gameFlow.value = loading()
        viewModelScope.launch {
            Log.v("ViewModelVariants", "finding game in view model...")
            val result: Result<Game?> =
                runCatching { gameService.findGame(variantConfig, preferences.getUserInfo()!!) }
            Log.v("ViewModelVariants", "found game in view model")
            _gameFlow.value = loaded(result)
        }
    }

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = preferences.isInDarkMode()
        }
    }

    fun setDarkTheme(isInDarkTheme: Boolean) {
        viewModelScope.launch {
            preferences.setDarkMode(isInDarkTheme)
            _isDarkThemeFlow.value = isInDarkTheme
        }
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
