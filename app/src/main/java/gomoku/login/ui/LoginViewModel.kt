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
import gomoku.ThemeRepository
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

class LoginViewModel(
    private val service: UserService,
    private val userRepo: UserInfoRepository,
    private val themeRepo: ThemeRepository
) : ViewModel() {

    companion object {
        fun factory(
            service: UserService,
            userRepo: UserInfoRepository,
            themeRepo: ThemeRepository
        ) = viewModelFactory {
            initializer { LoginViewModel(service, userRepo, themeRepo) }
        }
    }

    val userInfo: Flow<LoadState<UserInfo>>
        get() = _userInfoInfoFlow.asStateFlow()

    private val _userInfoInfoFlow: MutableStateFlow<LoadState<UserInfo>> = MutableStateFlow(idle())

    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = themeRepo.getIsDarkTheme()
        }
    }

    fun fetchLogin(username: String, password: String) {
        if (_userInfoInfoFlow.value !is Idle && _userInfoInfoFlow.value !is Fail)
            throw IllegalStateException("The view model is not in the idle state or in fail state.")
        _userInfoInfoFlow.value = loading()
        //if (userRepo.getUserInfo())
        viewModelScope.launch {
            Log.v(ContentValues.TAG, "fetching for login....")
            val result = runCatching { service.fetchLogin(username, password) }
            Log.v(ContentValues.TAG, "fetched done....")
            if (result.isFailure) {
                _userInfoInfoFlow.value = fail()
            } else {
                userRepo.updateUserInfo(result.getOrThrow())
                userRepo.getUserInfo()
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
        _isDarkThemeFlow.value = null
    }

}

