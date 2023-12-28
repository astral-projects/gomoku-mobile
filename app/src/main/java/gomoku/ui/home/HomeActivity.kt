package gomoku.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import gomoku.GomokuDependencyProvider
import gomoku.ui.about.AboutActivity
import gomoku.ui.leaderboard.LeaderboardActivity
import gomoku.ui.login.LoginActivity
import gomoku.ui.variant.VariantActivity
import kotlinx.coroutines.launch

const val USERNAME_EXTRA = "Username"

class HomeActivity : ComponentActivity() {

    companion object {
        fun navigateTo(ctx: Context, username: String) {
            ctx.startActivity(createIntent(ctx, username))
        }

        private fun createIntent(ctx: Context, username: String): Intent {
            val intent = Intent(ctx, HomeActivity::class.java)
            intent.putExtra(USERNAME_EXTRA, username)
            return intent
        }
    }

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.factory(dependencies.userService, dependencies.preferencesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateFlow.collect {
                if (it is HomeScreenState.Logout && it.isDone) {
                    LoginActivity.navigateTo(this@HomeActivity)
                    viewModel.resetToIdle()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDarkTheme.collect {
                    viewModel.isDarkTheme()
                }
            }

        }
        setContent {
            val state = viewModel.stateFlow.collectAsState(initial = HomeScreenState.Idle).value
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            HomeScreen(
                inDarkTheme = isDarkTheme,
                isFetchingLogout = state is HomeScreenState.Logout && !state.isDone,
                username = username,
                onFindMatch = { VariantActivity.navigateTo(this@HomeActivity, username) },
                onLeaderBoard = { LeaderboardActivity.navigateTo(this) },
                onAbout = { AboutActivity.navigateTo(this) },
                onLogout = { viewModel.logout() }
            )
        }
    }

    val username: String by lazy {
        intent?.getStringExtra(USERNAME_EXTRA)
            ?: throw IllegalArgumentException("Username must be provided")
    }
}
