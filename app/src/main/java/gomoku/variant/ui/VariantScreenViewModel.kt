package gomoku.variant.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.Idle
import gomoku.LoadState
import gomoku.Loaded
import gomoku.game.GameService
import gomoku.game.domain.Game
import gomoku.idle
import gomoku.loaded
import gomoku.loading
import gomoku.login.User
import gomoku.saved
import gomoku.variant.VariantService
import gomoku.variant.domain.VariantConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VariantScreenViewModel(
    private val service: VariantService,
    private val gameService: GameService
) : ViewModel() {

    companion object {
        fun factory(serviceVariant: VariantService, gameService: GameService) = viewModelFactory {
            initializer { VariantScreenViewModel(serviceVariant, gameService) }
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


    fun fetchVariants() {
        if (_variantsFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _variantsFlow.value = loading()
        viewModelScope.launch {
            Log.v("ViewModelVariants", "fetching variants in view model...")
            val result: Result<List<VariantConfig>> = runCatching { service.fetchVariants() }
            Log.v("ViewModelVariants", "fetched variants in view model")
            _variantsFlow.value = saved(result)
            _variantsFlow.value = loaded(result)
        }
    }

    fun findGame(variantConfig: VariantConfig, user: User) {
        if (_gameFlow.value !is Idle) {
            throw IllegalStateException("The view model is not in the idle state.")
        }
        _gameFlow.value = loading()
        viewModelScope.launch {
            Log.v("ViewModelVariants", "finding game in view model...")
            val result: Result<Game?> = runCatching { gameService.findGame(variantConfig, user) }
            Log.v("ViewModelVariants", "found game in view model")
            _gameFlow.value = loaded(result)
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


/*
 val user: Flow<LoadState<User?>>
        get() = _userInfoFlow.asStateFlow()

    private val _userInfoFlow: MutableStateFlow<LoadState<User?>> = MutableStateFlow(idle())

    fun fetchLogin(username: String, password: String) {
        if (_userInfoFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _userInfoFlow.value = loading()
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "fetching for login....")
            val result = runCatching { service.fetchLogin(username, password) }
            Log.v(ContentValues.TAG, "fetched done....")
            _userInfoFlow.value = loaded(result)
        }
    }

    /**
     * Resets the view model to the idle state. From the idle state, the user information
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_userInfoFlow.value !is Loaded)
            throw IllegalStateException("The view model is not in the idle state.")
        _userInfoFlow.value = idle()
    }

 */