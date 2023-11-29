package gomoku.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.LoadState
import gomoku.domain.idle
import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.loaded
import gomoku.domain.loading
import gomoku.domain.service.user.UserService
import gomoku.domain.storage.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val userService: UserService,
    private val preferences: PreferencesRepository
) : ViewModel() {

    companion object {
        fun factory(service: UserService, preferences: PreferencesRepository) =
            viewModelFactory {
                initializer { LeaderboardViewModel(service, preferences) }
            }
    }

    val isDarkTheme: Flow<Boolean?>
        get() = _isDarkThemeFlow.asStateFlow()

    private val _isDarkThemeFlow: MutableStateFlow<Boolean?> =
        MutableStateFlow(null)

    fun isDarkTheme() {
        viewModelScope.launch {
            _isDarkThemeFlow.value = preferences.isInDarkMode()
        }
    }

    fun setDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            preferences.setDarkMode(isDarkTheme)
            _isDarkThemeFlow.value = isDarkTheme
        }
    }

    val usersStats: Flow<LoadState<List<UserStats>>>
        get() = _usersStatsFlow.asStateFlow()

    private val _usersStatsFlow: MutableStateFlow<LoadState<List<UserStats>>> =
        MutableStateFlow(idle())

    fun fetchUserStats(userId: Int) {
        _usersStatsFlow.value = loading()
        viewModelScope.launch {
            val result = runCatching { userService.fetchUserStats(userId) }
            _usersStatsFlow.value = loaded(result.map { listOf(it) })
        }
    }

    fun fetchUsersStats(page: Int = 1) {
        _usersStatsFlow.value = loading()
        viewModelScope.launch {
            val result = runCatching { userService.fetchUsersStats(page) }
            _usersStatsFlow.value = loaded(result)
        }
    }

    /**
     * Searches for users with the given term.
     * @param term the term to search for.
     */
    fun searchUsers(term: Term) {
        _usersStatsFlow.value = loading()
        viewModelScope.launch {
            val result = runCatching {
                userService.searchUsers(term)
            }
            _usersStatsFlow.value = loaded(result)
        }
    }
}
