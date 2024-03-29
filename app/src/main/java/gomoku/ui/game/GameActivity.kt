package gomoku.ui.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import gomoku.GomokuDependencyProvider
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.ui.home.HomeActivity
import gomoku.ui.home.USERNAME_EXTRA
import kotlinx.coroutines.launch

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
         * Builds the intent that navigates to the [GameActivity].
         * @param ctx the context to be used.
         */
        private fun createIntent(
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
            viewModel.stateFlow.collect {
                if (it is GameScreenState.Idle) {
                    viewModel.fetchGameById(gameId.toInt())
                } else if (it is GameScreenState.GameLoadedAndNotYourTurn) {
                    viewModel.startPollingGame(gameId.toInt())
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

        setContent {
            val gameState by viewModel.stateFlow.collectAsState(initial = GameScreenState.Idle)
            val isDarkTheme by viewModel.isDarkTheme.collectAsState(initial = null)
            GameScreen(
                gameState = gameState,
                isDarkTheme = isDarkTheme,
                onCellClick = { square: Square ->
                    if (gameState is GameScreenState.GameLoadedAndYourTurn) {
                        viewModel.makeMove(
                            gameId = gameId.toInt(),
                            move = Move(
                                square,
                                Piece(Player.W)
                            )
                        )
                    }
                },
                onLeaveGameRequest = {
                    viewModel.exitGame(gameId.toInt())
                    HomeActivity.navigateTo(this, username)
                },
                onGameEnd = { HomeActivity.navigateTo(this, username) }
            )
        }
    }

    /**
     * Helper method to get the user extra from the intent.
     */
    private val username: String by lazy {
        intent?.getStringExtra(USERNAME_EXTRA)
            ?: throw IllegalArgumentException("Username must be provided")
    }

    private val gameId: String by lazy {
        intent?.getStringExtra(GAME_ID_EXTRA)
            ?: throw IllegalArgumentException("GameId must be provided")
    }

}