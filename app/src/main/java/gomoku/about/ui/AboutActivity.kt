package gomoku.about.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation
import gomoku.about.domain.About
import gomoku.leaderboard.ui.LeaderboardActivity
import gomoku.login.ui.LoginActivity
import gomoku.variant.ui.VariantActivity

class AboutActivity : ComponentActivity() {
    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, AboutActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AboutScreen(
                sections = About.sections,
                toLeaderboardScreen = { LeaderboardActivity.navigateTo(this) },
                toFindGameScreen = { VariantActivity.navigateTo(this) },
                onLogoutRequest = { LoginActivity.navigateTo(this) }
            )
        }
    }
}