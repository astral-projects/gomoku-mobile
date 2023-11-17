package gomoku.leaderboard.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gomoku.LoadState
import gomoku.idle
import gomoku.leaderboard.user.UserService
import gomoku.leaderboard.user.domain.UserStats
import gomoku.loaded
import gomoku.loading
import kotlinx.coroutines.launch

class LeaderboardScreenViewModel(
    private val userService: UserService
) : ViewModel() {

    var usersStats by mutableStateOf<LoadState<UserStats>>(idle())
        private set

    fun fetchUser(userId: Int) {
        viewModelScope.launch {
            Log.v("LeaderboardViewModel", "fetching user ...")
            usersStats = loading()
            val result = kotlin.runCatching { userService.fetchUser(userId) }
            Log.v("LeaderboardViewModel", "user fetched")
            usersStats = loaded(result)
        }
    }
}
