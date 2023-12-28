package gomoku.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.service.user.UserServiceInterface
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val service: UserServiceInterface,
    preferences: PreferencesRepository
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            service: UserServiceInterface,
            preferences: PreferencesRepository
        ) = viewModelFactory {
            initializer { HomeViewModel(service, preferences) }
        }
    }

    val stateFlow: Flow<HomeScreenState>
        get() = _stateFlow.asStateFlow()

    private val _stateFlow: MutableStateFlow<HomeScreenState> =
        MutableStateFlow(HomeScreenState.Idle)

    fun logout() {
        if (_stateFlow.value !is HomeScreenState.Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _stateFlow.value = HomeScreenState.Logout()
        viewModelScope.launch {
            val user = preferences.getUserInfo()
            if (user != null) {
                Log.v("Home", "logging out....")
                val result = runCatching { service.logout(user.token) }
                if (result.isFailure) {
                    _stateFlow.value =
                        HomeScreenState.Error(
                            result.exceptionOrNull() ?: Exception("Unknown error")
                        )
                } else {
                    Log.v("Home", "logged out done")
                    preferences.clearUserInfo(user)
                    _stateFlow.value = HomeScreenState.Logout(true)
                }
            } else {
                _stateFlow.value = HomeScreenState.Error(Exception("User not logged in"))
            }
        }
    }

    fun resetToIdle() {
        check(_stateFlow.value !is HomeScreenState.Idle) {
            "The view model is not in the idle state."
        }
        _stateFlow.value = HomeScreenState.Idle
    }

}
