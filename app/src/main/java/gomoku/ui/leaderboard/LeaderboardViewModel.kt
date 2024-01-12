package gomoku.ui.leaderboard

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import gomoku.domain.leaderboard.PaginatedResult
import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
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
                LeaderBoardScreenState.Loaded(PaginatedResult(listOf(result.getOrThrow())))
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    fun fetchUsersStats(page: Int = FIRST_PAGE) {
        val (previousList, nextUrlToFetch, isLastPage) = extractPaginationInfo(page)
        if (isLastPage) {
            return
        }
        _stateFlow.value = LeaderBoardScreenState.Loading(previousList)
        viewModelScope.launch {
            val result = runCatching { service.fetchUsersStats(nextUrlToFetch) }
            _stateFlow.value = if (result.isSuccess) {
                val newPaginatedResult: PaginatedResult<UserStats> = result.getOrThrow()
                if (page == FIRST_PAGE) {
                    LeaderBoardScreenState.Loaded(newPaginatedResult)
                } else {
                    val mergedList = previousList + newPaginatedResult.items
                    val sortedList = mergedList.sortedBy { it.rank }
                    LeaderBoardScreenState.Loaded(
                        newPaginatedResult.copy(items = sortedList)
                    )
                }
            } else {
                LeaderBoardScreenState.Error(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    private fun extractPaginationInfo(page: Int): Triple<List<UserStats>, String?, Boolean> {
        val previousPaginatedResult =
            (_stateFlow.value as? LeaderBoardScreenState.Loaded)?.usersStats
        val previousList = previousPaginatedResult?.items ?: emptyList()
        val isFirstPage = page == FIRST_PAGE
        val nextUrlToFetch = if (isFirstPage) null else previousPaginatedResult?.nextPageUrl
        val isLastPage = !isFirstPage && previousPaginatedResult?.lastPageUrl == null
        return Triple(previousList, nextUrlToFetch, isLastPage)
    }

    fun searchUsers(term: Term) {
        _stateFlow.value = LeaderBoardScreenState.Loading()
        viewModelScope.launch {
            // Log.v("searchUsers", "hello: $term")
            val result = runCatching { service.searchUsers(term = term) }
            _stateFlow.value = if (result.isSuccess) {
                LeaderBoardScreenState.Loaded(result.getOrElse { PaginatedResult(emptyList()) })
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
                    // Log.v("Leaderboard", "failure in logged out")
                    _stateFlow.value = LeaderBoardScreenState.Error(
                        result.exceptionOrNull() ?: Exception("Unknown error")
                    )
                } else {
                    // Log.v("Leaderboard", "logged out successfully")
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
