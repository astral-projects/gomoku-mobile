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
import gomoku.domain.login.UserInfo
import gomoku.ui.about.AboutActivity
import gomoku.ui.leaderboard.LeaderboardActivity
import gomoku.ui.login.LoginActivity
import gomoku.ui.variant.VariantActivity
import kotlinx.coroutines.launch

class HomeActivity : ComponentActivity() {

    companion object {
        fun navigateTo(ctx: Context, userInfo: UserInfo? = null) {
            ctx.startActivity(createIntent(ctx, userInfo))
        }

        private fun createIntent(ctx: Context, userInfo: UserInfo? = null): Intent {
            val intent = Intent(ctx, HomeActivity::class.java)
            userInfo?.let { intent.putExtra(USER_EXTRA, UserExtra(it)) }
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
                    if (it == null) {
                        viewModel.isDarkTheme()
                    }
                }
            }

        }
        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            HomeScreen(
                inDarkTheme = isDarkTheme,
                username = userInfo!!.username,
                onFindMatch = { VariantActivity.navigateTo(this@HomeActivity, userInfo) },
                onLeaderBoard = { LeaderboardActivity.navigateTo(this) },
                onAbout = { AboutActivity.navigateTo(this) },
                onLogout = { LoginActivity.navigateTo(this) }
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
        // provides compatibility with older versions of Android
            intent?.getParcelableExtra(USER_EXTRA)
}

