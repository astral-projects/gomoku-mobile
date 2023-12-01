package gomoku.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import gomoku.GomokuDependencyProvider
import gomoku.domain.Idle
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.idle
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.ui.home.HomeActivity
import gomoku.ui.home.USERNAME_EXTRA
import gomoku.ui.shared.background.BackgroundConfig
import kotlinx.coroutines.launch
import pdm.gomoku.R

private const val GAME_ID_EXTRA = "gameId"

class GameActivity : ComponentActivity() {

    companion object {
        fun navigateTo(ctx: Context, gameId: String, username: String) {
            ctx.startActivity(
                createIntent(
                    ctx,
                    gameId,
                    username
                )
            )
        }

        /**
         * Builds the intent that navigates to the [UserPreferencesActivity] activity.
         * @param ctx the context to be used.
         */
        fun createIntent(
            ctx: Context,
            gameId: String,
            username: String
        ): Intent {
            val intent = Intent(ctx, GameActivity::class.java)
            intent.putExtra(GAME_ID_EXTRA, gameId)
            intent.putExtra(USERNAME_EXTRA, username)
            return intent
        }
    }

    /**
     * The application's dependency provider.
     */
    private val dependencies by lazy { application as GomokuDependencyProvider }

    private val viewModel by viewModels<GameViewModel> {
        GameViewModel.factory(
            dependencies.gameService,
            dependencies.preferencesRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.game.collect {
                if (it is Idle) {
                    viewModel.fetchGame(gameId!!)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isDarkTheme.collect {
                    viewModel.isDarkTheme()
                }
            }

        }
        //todo(improve the makeAMove)
        setContent {
            val gameState by viewModel.game.collectAsState(initial = idle())
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            GameScreen(
                isDarkTheme = isDarkTheme,
                backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                localPlayer = PlayerInfo(username, R.drawable.man),
                onLeaveGameRequest = { HomeActivity.navigateTo(this, username) },
                onCellClick = { square: Square ->
                    viewModel.makeMove(
                        Move(
                            square,
                            Piece(Player.W)
                        )
                    )
                },
                gameState = gameState,
            )
        }
    }

    /**
     * Helper method to get the user extra from the intent.
     */
    val username: String by lazy {
        intent?.getStringExtra(USERNAME_EXTRA)
            ?: throw IllegalArgumentException("Username must be provided")
    }

    val gameId: String by lazy {
        intent?.getStringExtra(GAME_ID_EXTRA)
            ?: throw IllegalArgumentException("GameId must be provided")
    }


}