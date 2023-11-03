package gomoku.home.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation
import gomoku.about.ui.AboutActivity
import gomoku.leaderboard.LeaderboardActivity
import gomoku.login.LoginActivity
import gomoku.variant.VariantActivity

class HomeActivity : ComponentActivity() {

    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, HomeActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
                username = "John Doe",
                onFindMatch = { VariantActivity.navigateTo(this)  },
                onLeaderBoard = { LeaderboardActivity.navigateTo(this) },
                onAbout = { AboutActivity.navigateTo(this) },
                onLogout = { LoginActivity.navigateTo(this) }
            )
        }
    }
}