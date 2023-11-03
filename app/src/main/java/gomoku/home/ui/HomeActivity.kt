package gomoku.home.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation
import gomoku.about.ui.AboutActivity
import gomoku.game.ui.GameActivity
import gomoku.leaderboard.ui.LeaderBoardActivity
import gomoku.login.LoginActivity

const val GAME_NAME = "Gomoku Royale"
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
                onFindMatch = { GameActivity.navigateTo(this)  },
                onLeaderBoard = { LeaderBoardActivity.navigateTo(this) },
                onAbout = { AboutActivity.navigateTo(this) },
                onLogout = { LoginActivity.navigateTo(this) }
            )
        }
    }
}