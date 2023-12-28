package gomoku.ui.leaderboard

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
        _stateFlow.value = LeaderBoardScreenState.Loading(page = 0)
        viewModelScope.launch {
            val result = runCatching { service.fetchUserStats(userId) }
            _stateFlow.value = if (result.isSuccess) {
                LeaderBoardScreenState.UserStatsLoaded(result.getOrThrow())
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    fun fetchUsersStats(page: Int = FIRST_PAGE) {
        _stateFlow.value = LeaderBoardScreenState.Loading(page = page)
        viewModelScope.launch {
            val result = runCatching { service.fetchUsersStats(page) }
            _stateFlow.value = if (result.isSuccess) {
                LeaderBoardScreenState.UsersStatsLoaded(result.getOrThrow(), isLastPage = false)
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    fun searchUsers(term: String) {
        _stateFlow.value = LeaderBoardScreenState.SearchUsers(term)
        viewModelScope.launch {
            val result = runCatching { service.searchUsers(term = Term(term)) }
            _stateFlow.value = if (result.isSuccess) {
                LeaderBoardScreenState.UsersStatsLoaded(result.getOrThrow(), isLastPage = false)
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

}
