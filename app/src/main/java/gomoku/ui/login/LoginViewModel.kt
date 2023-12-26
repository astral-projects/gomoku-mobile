package gomoku.ui.login

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.Fail
import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.fail
import gomoku.domain.idle
import gomoku.domain.loaded
import gomoku.domain.loading
import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.UserServiceInterface
import gomoku.domain.service.utils.recipes.Recipe
import gomoku.domain.service.utils.recipes.fetchRecipes
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val service: UserServiceInterface,
    preferences: PreferencesRepository,
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            service: UserServiceInterface,
            preferences: PreferencesRepository
        ) = viewModelFactory {
            initializer { LoginViewModel(service, preferences) }
        }
    }

    val uriTemplates: Flow<IOState<List<Recipe>>>
        get() = _uriTemplatesFlow.asStateFlow()

    private val _uriTemplatesFlow: MutableStateFlow<IOState<List<Recipe>>> =
        MutableStateFlow(idle())

    val userInfo: Flow<IOState<UserInfo>>
        get() = _userInfoFlow.asStateFlow()

    private val _userInfoFlow: MutableStateFlow<IOState<UserInfo>> = MutableStateFlow(idle())

    /**
     * Make the first request to fetch the uri templates for all routes and store them in the
     * data store. This method should be called only once when the app is first launched.
     */
    fun fetchUriTemplates() {
        if (_uriTemplatesFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _uriTemplatesFlow.value = loading()
        viewModelScope.launch {
            Log.v("UriTemplates", "fetching for uri templates....")
            val result = runCatching { fetchRecipes() }
            Log.v("UriTemplates", "fetched done....")
            if (result.isFailure) {
                _uriTemplatesFlow.value = fail(result.toString())
            } else {
                Log.v("UriTemplates", "fetched done and is ${result.getOrNull()}")
                preferences.setUriTemplates(result.getOrThrow())
                _uriTemplatesFlow.value = idle()
            }
        }
    }

    @Throws(IllegalStateException::class)
    fun login(username: String, password: String) {
        if (_userInfoFlow.value !is Idle && _userInfoFlow.value !is Fail)
            throw IllegalStateException("The view model is not in the idle state or in fail state.")
        _userInfoFlow.value = loading()
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "fetching for login....")
            val result = runCatching { service.login(username, password) }
            Log.v(ContentValues.TAG, "fetched done....")
            if (result.isFailure) {
                val message = result.exceptionOrNull()?.message ?: "Unknown error"
                _userInfoFlow.value = fail(message)
            } else {
                Log.v(ContentValues.TAG, "fetched done and is ${result.getOrNull()}")
                preferences.setUserInfo(result.getOrThrow())
                _userInfoFlow.value = loaded(result)
            }
        }
    }

    /**
     * Resets the view model to the idle state. From the idle state, the user information
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_userInfoFlow.value !is Loaded && _userInfoFlow.value !is Fail)
            throw IllegalStateException("The view model is not in the loaded state or in fail state.")
        _userInfoFlow.value = idle()
    }
}
