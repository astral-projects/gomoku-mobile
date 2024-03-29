package gomoku.ui.leaderboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import gomoku.GomokuDependencyProvider
import gomoku.ui.Navigation
import gomoku.ui.about.AboutActivity
import gomoku.ui.login.LoginActivity
import gomoku.ui.variant.VariantActivity
import kotlinx.coroutines.launch

class LeaderboardActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<LeaderboardViewModel> {
        LeaderboardViewModel.factory(
            dependencies.userService,
            dependencies.preferencesRepository
        )
    }

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, LeaderboardActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateFlow.collect {
                if (it is LeaderBoardScreenState.Idle) {
                    Log.v("LeaderboardActivity", "fetching users stats")
                    viewModel.fetchUsersStats()
                } else if (it is LeaderBoardScreenState.Logout) {
                    LoginActivity.navigateTo(this@LeaderboardActivity)
                    viewModel.resetToIdle()
                }
            }
        }

        lifecycleScope.launch {
            viewModel.isDarkTheme.collect {
                if (it == null) {
                    viewModel.isDarkTheme()
                }
            }
        }
        setContent {
            val state by viewModel.stateFlow.collectAsState(initial = LeaderBoardScreenState.Idle)
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            LeaderboardScreen(
                state = state,
                userInfo = viewModel.getUserInfo(),
                isDarkTheme = isDarkTheme ?: false,
                setDarkTheme = { viewModel.setDarkTheme(it) },
                getUserStats = { id -> viewModel.fetchUserStats(id) },
                onSearchRequest = { term -> viewModel.searchUsers(term) },
                getItemsFromPage = { page -> viewModel.fetchUsersStats(page) },
                toFindGameScreen = {
                    VariantActivity.navigateTo(
                        this,
                        viewModel.getUserInfo().username
                    )
                },
                toAboutScreen = { AboutActivity.navigateTo(this) },
                onLogoutRequest = { viewModel.logout() },
            )
        }
    }
}