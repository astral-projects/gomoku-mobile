package gomoku.game.ui

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalConfiguration
import gomoku.GomokuDependencyProvider
import gomoku.game.domain.moves.move.Player
import gomoku.home.ui.HomeActivity
import gomoku.shared.background.BackgroundConfig

class GameActivity : ComponentActivity() {

    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, GameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }


    private val viewModel by viewModels<GameScreenViewModel> {
        GameScreenViewModel.factory(dependencies.gameService)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GameScreen(
                backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                localPlayer = Player.W,
                onLeaveGameRequest = { HomeActivity.navigateTo(this) },
                onCellClick = { /*TODO*/ },
                gameState = viewModel.game,
                onFetchGame = { viewModel.fetchGame() }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        Log.v(TAG, "onStart() called")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG, "onDestroy() called")
    }

}