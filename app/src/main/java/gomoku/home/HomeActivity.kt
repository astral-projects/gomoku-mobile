package gomoku.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.about.AboutActivity
import gomoku.game.GameActivity
import gomoku.leaderboard.LeaderBoardActivity
import gomoku.login.LoginActivity

class HomeActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: ComponentActivity) {
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