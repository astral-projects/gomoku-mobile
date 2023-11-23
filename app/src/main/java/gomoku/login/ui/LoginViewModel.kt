package gomoku.login.ui

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.Fail
import gomoku.Idle
import gomoku.LoadState
import gomoku.Loaded
import gomoku.fail
import gomoku.idle
import gomoku.leaderboard.user.UserService
import gomoku.loaded
import gomoku.loading
import gomoku.login.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val service: UserService) : ViewModel() {

    companion object {
        fun factory(service: UserService) = viewModelFactory {
            initializer { LoginViewModel(service) }
        }
    }

    val user: Flow<LoadState<User>>
        get() = _userInfoFlow.asStateFlow()

    private val _userInfoFlow: MutableStateFlow<LoadState<User>> = MutableStateFlow(idle())

    fun fetchLogin(username: String, password: String) {
        if (_userInfoFlow.value !is Idle && _userInfoFlow.value !is Fail)
            throw IllegalStateException("The view model is not in the idle state or in fail state.")
        _userInfoFlow.value = loading()
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "fetching for login....")
            val result = runCatching { service.fetchLogin(username, password) }
            Log.v(ContentValues.TAG, "fetched done....")
            if (result.isFailure) {
                _userInfoFlow.value = fail()
            } else {
                _userInfoFlow.value = loaded(result)
            }
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

}

