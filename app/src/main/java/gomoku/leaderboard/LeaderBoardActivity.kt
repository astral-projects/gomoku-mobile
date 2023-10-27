package gomoku.leaderboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import gomoku.Navigation

class LeaderBoardActivity : ComponentActivity() {
    companion object : Navigation {
        override fun navigateTo(origin: Activity) {
            val intent = Intent(origin, LeaderBoardActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LeaderBoardScreen(
                onSearch = {/*TODO*/},
                onBurgerMenuClick = {/*TODO*/},
                onTargetClick = {/*TODO*/},
                onLeaderBoardClick = {/*TODO*/},
            )
        }
    }
}