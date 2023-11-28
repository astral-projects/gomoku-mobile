package gomoku.leaderboard.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.Idle
import gomoku.LoadState
import gomoku.ThemeRepository
import gomoku.idle
import gomoku.leaderboard.user.UserService
import gomoku.leaderboard.user.domain.UserStats
import gomoku.loaded
import gomoku.loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val userService: UserService,
    private val themeRepo: ThemeRepository
) : ViewModel() {

    companion object {
        fun factory(service: UserService, themeRepo: ThemeRepository) = viewModelFactory {
            initializer { LeaderboardViewModel(service, themeRepo) }
        }
    }

    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = themeRepo.getIsDarkTheme()
        }
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            themeRepo.setDarkTheme(isDarkTheme)
            _isDarkThemeFlow.value = isDarkTheme
        }
    }


    val usersStats: Flow<LoadState<List<UserStats>?>>
        get() = _usersStatsFlow.asStateFlow()

    private val _usersStatsFlow: MutableStateFlow<LoadState<List<UserStats>?>> =
        MutableStateFlow(idle())


    fun fetchUsersStats() {
        if (_usersStatsFlow.value !is Idle)
            throw IllegalStateException("The view model is not in the idle state.")
        _usersStatsFlow.value = loading()
        viewModelScope.launch {
            Log.v("LeaderboardViewModel", "fetching user ...")
            val result = kotlin.runCatching { userService.fetchUsersStats() }
            Log.v("LeaderboardViewModel", "user fetched")
            _usersStatsFlow.value = loaded(result)
        }
    }
    /*
        fun fetchUserStats(userId: Int){
            viewModelScope.launch {
                Log.v("LeaderboardViewModel", "fetching user ...")
                usersStats = loading()
                val result = kotlin.runCatching { userService.fetchUsersStats(userId)}
                Log.v("LeaderboardViewModel", "user fetched")
                usersStats = loaded(result)
            }
        }*/
}
