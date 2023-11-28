package gomoku.variant.ui

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
import gomoku.idle
import gomoku.loaded
import gomoku.loading
import gomoku.saved
import gomoku.variant.VariantService
import gomoku.variant.VariantsInfoRepositroy
import gomoku.variant.domain.VariantConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VariantScreenViewModel(
    private val service: VariantService,
    private val gameService: GameService,
    private val userRepo: UserInfoRepository,
    private val variantsInfoRepo: VariantsInfoRepositroy,
    private val themeRepo: ThemeRepository
) : ViewModel() {

    companion object {
        fun factory(
            serviceVariant: VariantService,
            gameService: GameService,
            userRepo: UserInfoRepository,
            variantsRepo: VariantsInfoRepositroy,
            themeRepo: ThemeRepository
        ) = viewModelFactory {
            initializer {
                VariantScreenViewModel(
                    serviceVariant,
                    gameService,
                    userRepo,
                    variantsRepo,
                    themeRepo
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
            if (variantsInfoRepo.getVariantsInfo() != null) {
                _variantsFlow.value = loaded(Result.success(variantsInfoRepo.getVariantsInfo()!!))
            } else {
                val result: Result<List<VariantConfig>> = runCatching { service.fetchVariants() }
                Log.v("ViewModelVariants", "fetched variants in view model")
                variantsInfoRepo.updateVariantsInfo(result.getOrNull()!!)
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
                runCatching { gameService.findGame(variantConfig, userRepo.getUserInfo()!!) }
            Log.v("ViewModelVariants", "found game in view model")
            _gameFlow.value = loaded(result)
        }
    }

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = themeRepo.getIsDarkTheme()
        }
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            themeRepo.setDarkTheme(isDarkTheme)
            _isDarkThemeFlow.value = isDarkTheme
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

