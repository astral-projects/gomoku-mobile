package gomoku.ui.login

import android.content.ContentValues
import android.util.Log
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
import gomoku.domain.service.user.UserService
import gomoku.domain.storage.PreferencesRepository
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
            preferences: PreferencesRepository
        ) = viewModelFactory {
            initializer { LoginViewModel(service, preferences) }
        }
    }

    val userInfo: Flow<IOState<UserInfo>>
        get() = _userInfoInfoFlow.asStateFlow()

    private val _userInfoInfoFlow: MutableStateFlow<IOState<UserInfo>> = MutableStateFlow(idle())



    fun login(username: String, password: String) {
        if (_userInfoInfoFlow.value !is Idle && _userInfoInfoFlow.value !is Fail)
            throw IllegalStateException("The view model is not in the idle state or in fail state.")
        _userInfoInfoFlow.value = loading()
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "fetching for login....")
            val result = runCatching { service.login(username, password) }
            Log.v(ContentValues.TAG, "fetched done....")
            if (result.isFailure) {
                _userInfoInfoFlow.value = fail()
            } else {
                preferences.updateUserInfo(result.getOrThrow())
                _userInfoInfoFlow.value = loaded(result)
            }
        }
    }

    /**
     * Resets the view model to the idle state. From the idle state, the user information
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_userInfoInfoFlow.value !is Loaded)
            throw IllegalStateException("The view model is not in the idle state.")
        _userInfoInfoFlow.value = idle()
    }

}

