package gomoku.ui.leaderboard

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.leaderboard.Term
import gomoku.domain.service.user.UserService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.shared.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val service: UserService,
    preferences: PreferencesRepository,
) : BaseViewModel(preferences) {

    companion object {
        fun factory(service: UserService, preferences: PreferencesRepository) =
            viewModelFactory {
                initializer { LeaderboardViewModel(service, preferences) }
            }

        private const val FIRST_PAGE = 1
    }

    val stateFlow: Flow<LeaderBoardScreenState>
        get() = _stateFlow.asStateFlow()

    private val _stateFlow: MutableStateFlow<LeaderBoardScreenState> =
        MutableStateFlow(LeaderBoardScreenState.Idle)

    fun fetchUserStats(userId: Int) {
        _stateFlow.value = LeaderBoardScreenState.Loading()
        viewModelScope.launch {
            val result = runCatching { service.fetchUserStats(userId) }
            _stateFlow.value = if (result.isSuccess) {
                Log.v("fetchUserStatsUnique", "result: ${result.getOrThrow()}")
                LeaderBoardScreenState.UsersStatsLoaded(listOf(result.getOrThrow()))
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    fun fetchUsersStats(page: Int = FIRST_PAGE) {
        val previousList =
            (_stateFlow.value as? LeaderBoardScreenState.UsersStatsLoaded)?.usersStats
                ?: emptyList()
        Log.v("fetchUsersStats", "previousList: $previousList")
        _stateFlow.value = LeaderBoardScreenState.Loading(previousList)
        viewModelScope.launch {
            val result = runCatching { service.fetchUsersStats(page) }
            _stateFlow.value = if (result.isSuccess) {
                val newList = result.getOrThrow()
                if (page == FIRST_PAGE) {
                    Log.v("fetchUsersStats", "newList: $newList")
                    LeaderBoardScreenState.UsersStatsLoaded(newList)
                } else {
                    val mergedList = previousList + newList
                    val sortedList = mergedList.sortedBy { it.rank }
                    Log.v("fetchUsersStats", "sortedList: $sortedList")
                    LeaderBoardScreenState.UsersStatsLoaded(sortedList)
                }
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    fun searchUsers(term: Term) {
        _stateFlow.value = LeaderBoardScreenState.Loading()
        viewModelScope.launch {
            Log.v("searchUsers", "hello: $term")
            val result = runCatching { service.searchUsers(term = term) }
            _stateFlow.value = if (result.isSuccess) {
                LeaderBoardScreenState.UsersStatsLoaded(result.getOrThrow())
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    fun logout() {
        _stateFlow.value = LeaderBoardScreenState.Loading()
        viewModelScope.launch {
            val user = preferences.getUserInfo()
            if (user != null) {
                val result = runCatching { service.logout(user.token) }
                if (result.isFailure) {
                    Log.v("Leaderboard", "failure in logged out")
                    _stateFlow.value = LeaderBoardScreenState.Error(
                        result.exceptionOrNull() ?: Exception("Unknown error")
                    )
                } else {
                    Log.v("Leaderboard", "logged out successfully")
                    preferences.clearUserInfo(user)
                    _stateFlow.value = LeaderBoardScreenState.Logout
                }
            } else {
                _stateFlow.value = LeaderBoardScreenState.Error(Exception("User not logged in"))
            }
        }
    }

    fun resetToIdle() {
        _stateFlow.value = LeaderBoardScreenState.Idle
    }

}
