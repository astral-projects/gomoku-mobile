package gomoku.ui.variant

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
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.game.match.Game
import gomoku.domain.idle
import gomoku.ui.about.AboutActivity
import gomoku.ui.game.GameActivity
import gomoku.ui.home.USERNAME_EXTRA
import gomoku.ui.leaderboard.LeaderboardActivity
import gomoku.ui.login.LoginActivity
import kotlinx.coroutines.launch

class VariantActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<VariantScreenViewModel> {
        VariantScreenViewModel.factory(
            dependencies.variantService,
            dependencies.gameService,
            dependencies.preferencesRepository
        )
    }

    companion object {
        fun navigateTo(ctx: Context, username: String) {
            ctx.startActivity(createIntent(ctx, username))
        }

        /**
         * Builds the intent that navigates to the [GameActivity] activity.
         * @param ctx the context to be used.
         */
        private fun createIntent(ctx: Context, username: String): Intent {
            val intent = Intent(ctx, VariantActivity::class.java)
            intent.putExtra(USERNAME_EXTRA, username)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.match.collect {
                // only navigate to the game screen if the game is successfully loaded
                if (it is Loaded && it.value.isSuccess && it.value.getOrThrow() is Game) {
                    doNavigation(it.value.getOrThrow() as Game)
                    viewModel.resetToIdle()
                } else if (it is Loaded && it.value.isFailure) {
                    // if an error occurred while trying to find a game, reset the state
                    viewModel.resetToIdle()
                }
            }
        }
        lifecycleScope.launch {
            viewModel.variants.collect {
                if (it is Idle) {
                    viewModel.fetchVariants()
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
            val stateVariants by viewModel.variants.collectAsState(initial = idle())
            val stateFindGame by viewModel.match.collectAsState(initial = idle())
            val stateIsInDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            VariantScreen(
                onPlayRequest = { variantConfig -> viewModel.findGame(variantConfig) },
                gameMatchState = stateFindGame,
                toLeaderboardScreen = { LeaderboardActivity.navigateTo(this) },
                toAboutScreen = { AboutActivity.navigateTo(this) },
                onLogoutRequest = { LoginActivity.navigateTo(this) },
                variantsState = stateVariants,
                isDarkTheme = stateIsInDarkTheme,
                setDarkTheme = { isDarkTheme -> viewModel.setDarkTheme(isDarkTheme) }
            )
        }
    }


    /**
     * Navigates to the appropriate activity, depending on whether the
     * user information has already been provided or not.
     * @param game the user information.
     */
    private fun doNavigation(game: Game) {
        GameActivity.navigateTo(this@VariantActivity, game.id, username)
    }

    val username: String by lazy {
        intent?.getStringExtra(USERNAME_EXTRA)
            ?: throw IllegalArgumentException("Username must be provided")
    }

}