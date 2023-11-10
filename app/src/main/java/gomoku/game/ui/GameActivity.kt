package gomoku.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalConfiguration
import gomoku.game.FakeGameService
import gomoku.game.domain.moves.move.Player
import gomoku.home.ui.HomeActivity
import gomoku.shared.background.BackgroundConfig

class GameActivity : ComponentActivity() {

    private val viewModel by viewModels<GameScreenViewModel>()

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, GameActivity::class.java)
            origin.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel.fetchGame(FakeGameService())
            GameScreen(
                backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                localPlayer = Player.W,
                onLeaveGameRequest = { HomeActivity.navigateTo(this) },
                onCellClick = { /*TODO*/ },
                viewModel.game
            )
        }
    }
}