package gomoku.about.ui

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
import gomoku.Navigation
import gomoku.about.domain.About
import gomoku.leaderboard.ui.LeaderboardActivity
import gomoku.login.ui.LoginActivity
import gomoku.variant.ui.VariantActivity
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
        AboutViewModel.factory(dependencies.themeRepository)
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