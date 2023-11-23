package gomoku.register.ui

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

class RegisterViewModel(private val service: UserService) : ViewModel() {

    companion object {
        fun factory(service: UserService) = viewModelFactory {
            initializer { RegisterViewModel(service) }
        }
    }

    val user: Flow<LoadState<User>>
        get() = _createUserFlow.asStateFlow()

    private val _createUserFlow: MutableStateFlow<LoadState<User>> = MutableStateFlow(idle())

    fun fetchCreateUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (_createUserFlow.value !is Idle && _createUserFlow.value !is Fail)
            throw IllegalStateException("The view model is not in the idle state.")
        _createUserFlow.value = loading()
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "fetch for createUser...")
            val result =
                runCatching { service.fetchCreateUser(username, email, password, confirmPassword) }
            Log.v(ContentValues.TAG, "fetch for createUser exit")
            if (result.isFailure) {
                _createUserFlow.value = fail()
            } else {
                _createUserFlow.value = loaded(result)
            }
        }
    }

    /**
     * Resets the view model to the idle state. From the idle state, the create user
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_createUserFlow.value !is Loaded)
            throw IllegalStateException("The view model is not in the idle state.")
        _createUserFlow.value = idle()
    }

}