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
        HomeViewModel.factory(dependencies.preferencesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDarkTheme.collect {
                        viewModel.isDarkTheme()
                }
            }

        }
        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            HomeScreen(
                inDarkTheme = isDarkTheme,
                username = username ?: "",
                onFindMatch = { VariantActivity.navigateTo(this@HomeActivity, username) },
                onLeaderBoard = { LeaderboardActivity.navigateTo(this) },
                onAbout = { AboutActivity.navigateTo(this) },
                onLogout = { LoginActivity.navigateTo(this) }
            )
        }
    }

    val username: String by lazy {
        getUsernameExtra() ?: throw IllegalArgumentException("Username must be provided")
    }

    private fun getUsernameExtra(): String? =
        intent?.getStringExtra(USERNAME_EXTRA)


}

