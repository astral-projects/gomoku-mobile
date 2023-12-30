package gomoku.ui.about

import android.app.Activity
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
import gomoku.domain.Loaded
import gomoku.domain.about.About
import gomoku.ui.Navigation
import gomoku.ui.leaderboard.LeaderboardActivity
import gomoku.ui.login.LoginActivity
import gomoku.ui.variant.VariantActivity
import kotlinx.coroutines.launch

class AboutActivity : ComponentActivity() {
    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, AboutActivity::class.java)
            origin.startActivity(intent)
        }
    }

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<AboutViewModel> {
        AboutViewModel.factory(dependencies.userService, dependencies.preferencesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.stateFlow.collect {
                if (it is Loaded) {
                    LoginActivity.navigateTo(this@AboutActivity)
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
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            val username = viewModel.getUserInfo().username
            AboutScreen(
                isDarkTheme ?: false,
                sections = About.sections,
                authors = About.authors,
                setDarkTheme = { viewModel.setDarkTheme(it) },
                toLeaderboardScreen = { LeaderboardActivity.navigateTo(this) },
                toFindGameScreen = { VariantActivity.navigateTo(this, username) },
                onLogoutRequest = { viewModel.logout() }
            )
        }
    }

}