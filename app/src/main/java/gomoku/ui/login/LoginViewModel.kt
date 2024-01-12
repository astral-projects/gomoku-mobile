package gomoku.ui.login

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.service.user.UserService
import gomoku.domain.storage.PreferencesRepository
import gomoku.http.utils.recipes.fetchRecipes
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val service: UserService,
    preferences: PreferencesRepository,
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            service: UserService,
            preferences: PreferencesRepository,
        ) = viewModelFactory {
            initializer { LoginViewModel(service, preferences) }
        }
    }

    val stateFlow: Flow<LoginScreenState>
        get() = _stateFlow.asStateFlow()

    private val _stateFlow: MutableStateFlow<LoginScreenState> =
        MutableStateFlow(LoginScreenState.Idle)

    /**
     * Make the first request to fetch the uri templates for all routes and store them in the
     * data store. This method should be called only once when the app is first launched.
     */
    fun fetchUriTemplates() {
        if (_stateFlow.value !is LoginScreenState.Idle && _stateFlow.value !is LoginScreenState.ErrorFetchingRecipes)
            throw IllegalStateException("The view model is not in the idle state.")
        _stateFlow.value = LoginScreenState.FetchRecipes()
        viewModelScope.launch {
            val uris = preferences.getUriTemplates()
            val user = preferences.getUserInfo()
            if (uris != null && user != null) {
                _stateFlow.value = LoginScreenState.Login(user, true)
                return@launch
            }
            // Log.v("UriTemplates", "fetching for uri templates....")
            val result = runCatching { fetchRecipes() }
            // Log.v("UriTemplates", "fetched done....")
            if (result.isFailure) {
                _stateFlow.value =
                    LoginScreenState.ErrorFetchingRecipes(
                        result.exceptionOrNull() ?: Exception("Unknown error")
                    )
            } else {
                // Log.v("UriTemplates", "fetched done and is ${result.getOrNull()}")
                preferences.setUriTemplates(result.getOrThrow())
                if (user != null) {
                    _stateFlow.value = LoginScreenState.Login(user, true)
                } else {
                    _stateFlow.value = LoginScreenState.FetchRecipes(result.getOrThrow(), true)
                }
            }
        }
    }

    @Throws(IllegalStateException::class)
    fun login(username: String, password: String) {
        _stateFlow.value.let { state ->
            check(state is LoginScreenState.FetchRecipes || state is LoginScreenState.Error) {
                "The view model is not in the FetchRecipes state or in the Error state."
            }
        }
        _stateFlow.value = LoginScreenState.Login()
        viewModelScope.launch {
            // Log.v("Login", "fetching for login....")
            val result = runCatching { service.login(username, password) }
            // Log.v("Login", "fetched done....")
            if (result.isFailure) {
                _stateFlow.value = LoginScreenState.Error(
                    result.exceptionOrNull() ?: Exception("Unknown error")
                )
            } else {
                // Log.v("Login", "fetched done and is ${result.getOrNull()}")
                preferences.setUserInfo(result.getOrThrow())
                _stateFlow.value = LoginScreenState.Login(result.getOrThrow(), true)
            }
        }
    }

    /**
     * Resets the view model to the idle state.
     * @throws IllegalStateException if the view model is not in the Login state or in the Error state.
     */
    @Throws(IllegalStateException::class)
    fun resetToIdle() {
        check(_stateFlow.value is LoginScreenState.Login || _stateFlow.value is LoginScreenState.Error) {
            "The view model is not in the Login state or in the Error state."
        }
        _stateFlow.value = LoginScreenState.Idle
    }
}
