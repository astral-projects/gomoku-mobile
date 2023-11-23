package gomoku.leaderboard.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import gomoku.GomokuDependencyProvider
import gomoku.Idle
import gomoku.Navigation
import gomoku.about.ui.AboutActivity
import gomoku.idle
import gomoku.login.ui.LoginActivity
import gomoku.variant.ui.VariantActivity
import kotlinx.coroutines.launch

class LeaderboardActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependencyProvider }


    private val viewModel by viewModels<LeaderboardViewModel> {
        LeaderboardViewModel.factory(dependencies.userService)
    }

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, LeaderboardActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchUsersStats()
        lifecycleScope.launch {
            viewModel.usersStats.collect {
                if (it is Idle) {
                    viewModel.fetchUsersStats()
                }
            }
        }
        setContent {
            val state by viewModel.usersStats.collectAsState(initial = idle())

            LeaderboardScreen(
                listLeaderboard = state,
                toFindGameScreen = { VariantActivity.navigateTo(this) },
                toAboutScreen = { AboutActivity.navigateTo(this) },
                onLogoutRequest = { LoginActivity.navigateTo(this) },
            )
        }
    }
}