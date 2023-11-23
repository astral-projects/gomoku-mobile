package gomoku.home.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.USER_EXTRA
import gomoku.UserExtra
import gomoku.about.ui.AboutActivity
import gomoku.leaderboard.ui.LeaderboardActivity
import gomoku.login.User
import gomoku.login.ui.LoginActivity
import gomoku.toUserInfo
import gomoku.variant.ui.VariantActivity
class HomeActivity : ComponentActivity() {

    companion object {
        fun navigateTo(ctx: Context, user: User? = null) {
            ctx.startActivity(createIntent(ctx, user))
        }

        /**
         * Builds the intent that navigates to the [UserPreferencesActivity] activity.
         * @param ctx the context to be used.
         */
        fun createIntent(ctx: Context, userInfo: User? = null): Intent {
            val intent = Intent(ctx, HomeActivity::class.java)
            userInfo?.let { intent.putExtra(USER_EXTRA, UserExtra(it)) }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
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
    val userInfo: User? by lazy { getUserInfoExtra()?.toUserInfo() }

    @Suppress("DEPRECATION")
    private fun getUserInfoExtra(): UserExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent?.getParcelableExtra(USER_EXTRA, UserExtra::class.java)
        else
            intent?.getParcelableExtra(USER_EXTRA)
}

