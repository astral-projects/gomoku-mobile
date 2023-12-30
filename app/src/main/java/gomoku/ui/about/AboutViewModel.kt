package gomoku.ui.about

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.IOState
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.fail
import gomoku.domain.idle
import gomoku.domain.loading
import gomoku.domain.service.user.UserService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AboutViewModel(
    val service: UserService,
    preferences: PreferencesRepository,
) : BaseViewModel(preferences) {

    companion object {
        fun factory(
            service: UserService,
            preferences: PreferencesRepository,
        ) = viewModelFactory {
            initializer { AboutViewModel(service, preferences) }
        }
    }

    val stateFlow: Flow<IOState<Boolean>>
        get() = _stateFlow.asStateFlow()

    private val _stateFlow: MutableStateFlow<IOState<Boolean>> = MutableStateFlow(idle())

    fun logout() {
        if (_stateFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _stateFlow.value = loading()
        viewModelScope.launch {
            val user = preferences.getUserInfo()
            if (user != null) {
                val result = runCatching { service.logout(user.token) }
                if (result.isFailure) {
                    Log.v("About", "failure in logged out")
                    _stateFlow.value = fail(result.exceptionOrNull() ?: Exception("Unknown error"))
                } else {
                    Log.v("About", "logged out successfully")
                    preferences.clearUserInfo(user)
                    _stateFlow.value = Loaded(Result.success(true))
                }
            } else {
                _stateFlow.value = fail(Exception("User not logged in"))
            }
        }
    }

    fun resetToIdle() {
        _stateFlow.value = idle()
    }
}