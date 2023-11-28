package gomoku.game.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.lifecycleScope
import gomoku.GomokuDependencyProvider
import gomoku.Idle
import gomoku.USER_EXTRA
import gomoku.UserExtra
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.home.ui.HomeActivity
import gomoku.idle
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.login.UserInfo
import gomoku.shared.background.BackgroundConfig
import gomoku.toUserInfo
import kotlinx.coroutines.launch
import pdm.gomoku.R

private const val GAME_ID_EXTRA = "gameId"
class GameActivity : ComponentActivity() {

    companion object {
        fun navigateTo(ctx: Context, gameId: String, userInfo: UserInfo? = null) {
            ctx.startActivity(createIntent(ctx, userInfo, gameId))
        }

        /**
         * Builds the intent that navigates to the [UserPreferencesActivity] activity.
         * @param ctx the context to be used.
         */
        fun createIntent(
            ctx: Context,
            userInfo: UserInfo? = null,
            gameId: String
        ): Intent {
            val intent = Intent(ctx, GameActivity::class.java)
            userInfo?.let { intent.putExtra(USER_EXTRA, UserExtra(it)) }
            intent.putExtra(GAME_ID_EXTRA, gameId)
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
            dependencies.userInfoRepository,
            dependencies.themeRepository
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
            viewModel.isDarkTheme.collect {
                if (it == null) {
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
                localPlayer = PlayerInfo(userInfo!!.username.value, R.drawable.man),
                onLeaveGameRequest = { HomeActivity.navigateTo(this) },
                onCellClick = { square -> viewModel.makeMove(Move(square, Piece(Player.W))) },
                gameState = gameState,
            )
        }
    }

    /**
     * Helper method to get the user extra from the intent.
     */
    val userInfo: UserInfo? by lazy { getUserInfoExtra()?.toUserInfo() }

    val gameId: String? by lazy { intent?.getStringExtra(GAME_ID_EXTRA) }

    @Suppress("DEPRECATION")
    private fun getUserInfoExtra(): UserExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent?.getParcelableExtra(USER_EXTRA, UserExtra::class.java)
        else
            intent?.getParcelableExtra(USER_EXTRA)


}