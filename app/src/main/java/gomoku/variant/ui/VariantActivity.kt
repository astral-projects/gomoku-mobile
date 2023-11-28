package gomoku.variant.ui

import android.content.Context
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
import gomoku.Loaded
import gomoku.USER_EXTRA
import gomoku.UserExtra
import gomoku.about.ui.AboutActivity
import gomoku.game.domain.Game
import gomoku.game.ui.GameActivity
import gomoku.idle
import gomoku.leaderboard.ui.LeaderboardActivity
import gomoku.login.UserInfo
import gomoku.login.ui.LoginActivity
import gomoku.toUserInfo
import kotlinx.coroutines.launch

class VariantActivity : ComponentActivity() {

    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<VariantScreenViewModel> {
        VariantScreenViewModel.factory(
            dependencies.variantService,
            dependencies.gameService,
            dependencies.userInfoRepository,
            dependencies.variantsInfoRepository,
            dependencies.themeRepository
        )
    }

    companion object {
        fun navigateTo(ctx: Context, userInfo: UserInfo? = null) {
            ctx.startActivity(createIntent(ctx, userInfo))
        }

        /**
         * Builds the intent that navigates to the [GameActivity] activity.
         * @param ctx the context to be used.
         */
        fun createIntent(ctx: Context, userInfo: UserInfo? = null): Intent {
            val intent = Intent(ctx, VariantActivity::class.java)
            userInfo?.let { intent.putExtra(USER_EXTRA, UserExtra(it)) }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            viewModel.game.collect {
                if (it is Loaded && it.value.isSuccess) {
                    doNavigation(it.value.getOrNull()!!)
                    viewModel.resetToIdle()
                } else if (it is Loaded && it.value.getOrNull() == null) {
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
            viewModel.isDarkTheme.collect {
                if (it == null) {
                    viewModel.isDarkTheme()
                }
            }
        }

        setContent {
            val stateVariants by viewModel.variants.collectAsState(idle())
            val stateFindGame by viewModel.game.collectAsState(initial = idle())
            val stateIsDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            VariantScreen(
                onSubmit = { variantConfig ->
                    viewModel.findGame(variantConfig)
                },
                gameMatchState = stateFindGame,
                toLeaderboardScreen = { LeaderboardActivity.navigateTo(this) },
                toAboutScreen = { AboutActivity.navigateTo(this) },
                onLogoutRequest = { LoginActivity.navigateTo(this) },
                variantsState = stateVariants,
                isDarkTheme = stateIsDarkTheme,
                setDarkTheme = { isDarkTheme ->
                    viewModel.setDarkTheme(isDarkTheme)
                }
            )
        }
    }

    /**
     * Helper method to get the user extra from the intent.
     */
    val userInfo: UserInfo? by lazy { getUserInfoExtra()?.toUserInfo() }

    @Suppress("DEPRECATION")
    private fun getUserInfoExtra(): UserExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent?.getParcelableExtra(USER_EXTRA, UserExtra::class.java)
        else
            intent?.getParcelableExtra(USER_EXTRA)


    /**
     * Navigates to the appropriate activity, depending on whether the
     * user information has already been provided or not.
     * @param game the user information.
     */
    private fun doNavigation(game: Game) {
        GameActivity.navigateTo(this@VariantActivity, game.id, userInfo!!)
    }

}