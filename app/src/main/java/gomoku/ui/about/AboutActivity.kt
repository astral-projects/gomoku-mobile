package gomoku.ui.about

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
        AboutViewModel.factory(dependencies.preferencesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.isDarkTheme.collect {
                if (it == null) {
                    viewModel.isDarkTheme()
                }
            }
        }

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            AboutScreen(
                isDarkTheme ?: false,
                sections = About.sections,
                setDarkTheme = { viewModel.setDarkTheme(it) },
                toLeaderboardScreen = { LeaderboardActivity.navigateTo(this) },
                toFindGameScreen = { VariantActivity.navigateTo(this) },
                onLogoutRequest = { LoginActivity.navigateTo(this) }
            )
        }
    }
}