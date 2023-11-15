package gomoku.leaderboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gomoku.leaderboard.domain.RankingInfo
import gomoku.services.FakeUsersServices
import kotlinx.coroutines.launch

class LeaderboardScreenViewModel : ViewModel() {

    var usersStats: List<RankingInfo> by mutableStateOf(emptyList())
        private set

    fun fetchUsersStats(service: FakeUsersServices) {
        viewModelScope.launch {
            usersStats = service.fetchUsersStats()
        }
    }
}
