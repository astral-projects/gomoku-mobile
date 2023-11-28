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
import gomoku.UserInfoRepository
import gomoku.fail
import gomoku.idle
import gomoku.leaderboard.user.UserService
import gomoku.loaded
import gomoku.loading
import gomoku.login.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val service: UserService,
    private val userRepo: UserInfoRepository
) : ViewModel() {

    companion object {
        fun factory(service: UserService, userRepo: UserInfoRepository) = viewModelFactory {
            initializer { RegisterViewModel(service, userRepo) }
        }
    }

    val userInfo: Flow<LoadState<UserInfo>>
        get() = _createUserFlowInfo.asStateFlow()

    private val _createUserFlowInfo: MutableStateFlow<LoadState<UserInfo>> =
        MutableStateFlow(idle())

    fun fetchCreateUser(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        if (_createUserFlowInfo.value !is Idle && _createUserFlowInfo.value !is Fail)
            throw IllegalStateException("The view model is not in the idle state.")
        _createUserFlowInfo.value = loading()
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "fetch for createUser...")
            val result =
                runCatching { service.fetchCreateUser(username, email, password, confirmPassword) }
            Log.v(ContentValues.TAG, "fetch for createUser exit")
            if (result.isFailure) {
                _createUserFlowInfo.value = fail()
            } else {
                userRepo.updateUserInfo(result.getOrThrow())
                _createUserFlowInfo.value = loaded(result)
            }
        }
    }

    /**
     * Resets the view model to the idle state. From the idle state, the create user
     * can be fetched again.
     */
    fun resetToIdle() {
        if (_createUserFlowInfo.value !is Loaded)
            throw IllegalStateException("The view model is not in the idle state.")
        _createUserFlowInfo.value = idle()
    }

}