package gomoku.leaderboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class LeaderBoardActivity : ComponentActivity() {
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