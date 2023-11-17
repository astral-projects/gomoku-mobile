package gomoku.variant.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import gomoku.GomokuDependencyProvider
import gomoku.Navigation
import gomoku.about.ui.AboutActivity
import gomoku.game.ui.GameActivity
import gomoku.leaderboard.ui.LeaderboardActivity
import gomoku.login.ui.LoginActivity

class VariantActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<VariantScreenViewModel> {
        VariantScreenViewModel.factory(dependencies.variantService)
    }

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, VariantActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VariantScreen(
                onSubmit = { GameActivity.navigateTo(this) },
                toLeaderboardScreen = { LeaderboardActivity.navigateTo(this) },
                toAboutScreen = { AboutActivity.navigateTo(this) },
                onLogoutRequest = { LoginActivity.navigateTo(this) },
                variantsState = viewModel.variants,
                onFetchVariants = { viewModel.fetchVariants() },
            )
        }
    }
}