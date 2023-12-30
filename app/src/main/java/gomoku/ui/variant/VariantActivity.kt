package gomoku.ui.variant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import gomoku.ui.game.GameActivity
import gomoku.ui.home.HomeActivity
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
            dependencies.userService,
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
            viewModel.stateFlow.collect {
                if (it is VariantScreenState.Idle) {
                    viewModel.fetchVariants()
                }
                if (it is VariantScreenState.WaitingInLobby && !it.isPolling) {
                    viewModel.startPollingLobby(it.lobbyId)
                }
                if (it is VariantScreenState.JoinGame) {
                    Log.v(
                        "VariantActivity",
                        "Joining game with id ${it.gameId} in string ${it.gameId.toString()}"
                    )
                    doNavigation(it.gameId.toString())
                    viewModel.resetToIdle()
                }
                if (it is VariantScreenState.ExitLobby) {
                    HomeActivity.navigateTo(this@VariantActivity, username)
                    viewModel.resetToIdle()
                }
                if (it is VariantScreenState.Logout) {
                    LoginActivity.navigateTo(this@VariantActivity)
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
            val state = viewModel.stateFlow.collectAsState(initial = VariantScreenState.Idle).value
            val stateIsInDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            VariantScreen(
                state = state,
                userInfo = viewModel.getUserInfo(),
                onPlayRequest = { variantConfig -> viewModel.findGame(variantConfig.id) },
                onLobbyExitRequest = {
                    if (state is VariantScreenState.WaitingInLobby) {
                        viewModel.exitLobby(state.lobbyId)
                    }
                },
                toLeaderboardScreen = { LeaderboardActivity.navigateTo(this) },
                toAboutScreen = { AboutActivity.navigateTo(this) },
                onLogoutRequest = { viewModel.logout() },
                isDarkTheme = stateIsInDarkTheme,
                setDarkTheme = { isDarkTheme -> viewModel.setDarkTheme(isDarkTheme) }
            )
        }
    }


    /**
     * Navigates to the appropriate activity, depending on whether the
     * user information has already been provided or not.
     * @param gameId the id of the game to be displayed.
     */
    private fun doNavigation(gameId: String) {
        GameActivity.navigateTo(this@VariantActivity, gameId, username)
    }

    val username: String by lazy {
        intent?.getStringExtra(USERNAME_EXTRA)
            ?: throw IllegalArgumentException("Username must be provided")
    }

}