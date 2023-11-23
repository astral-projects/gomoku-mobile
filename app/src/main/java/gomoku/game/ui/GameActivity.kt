package gomoku.game.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import gomoku.VARIANT_EXTRA
import gomoku.VariantExtra
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.home.ui.HomeActivity
import gomoku.idle
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.login.User
import gomoku.shared.background.BackgroundConfig
import gomoku.toUserInfo
import gomoku.toVariantInfo
import gomoku.variant.domain.VariantConfig
import kotlinx.coroutines.launch
import pdm.gomoku.R

class GameActivity : ComponentActivity() {

    companion object {
        fun navigateTo(ctx: Context, user: User? = null, variant: VariantConfig? = null) {
            ctx.startActivity(createIntent(ctx, user, variant))
        }

        /**
         * Builds the intent that navigates to the [UserPreferencesActivity] activity.
         * @param ctx the context to be used.
         */
        fun createIntent(
            ctx: Context,
            userInfo: User? = null,
            variant: VariantConfig? = null
        ): Intent {
            val intent = Intent(ctx, GameActivity::class.java)
            userInfo?.let { intent.putExtra(USER_EXTRA, UserExtra(it)) }
            variant?.let { intent.putExtra(VARIANT_EXTRA, VariantExtra(it)) }
            return intent
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
        lifecycleScope.launch {
            viewModel.game.collect {
                if (it is Idle) {
                    viewModel.fetchGame()
                }
            }
        }
        //todo(improve the makeAMove)
        setContent {
            val gameState by viewModel.game.collectAsState(initial = idle())
            GameScreen(
                backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                localPlayer = PlayerInfo(userInfo!!.username, R.drawable.man),
                onLeaveGameRequest = { HomeActivity.navigateTo(this) },
                onCellClick = { square -> viewModel.makeMove(Move(square, Piece(Player.W))) },
                gameState = gameState,
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


    /**
     * Helper method to get the user extra from the intent.
     */
    val userInfo: User? by lazy { getUserInfoExtra()?.toUserInfo() }

    @Suppress("DEPRECATION")
    private fun getUserInfoExtra(): UserExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent?.getParcelableExtra(USER_EXTRA, UserExtra::class.java)
        else
            intent?.getParcelableExtra(USER_EXTRA)

    val variant: VariantConfig? by lazy { getVariantExtra()?.toVariantInfo() }

    @Suppress("DEPRECATION")
    private fun getVariantExtra(): VariantExtra? =
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
            intent?.getParcelableExtra(VARIANT_EXTRA, VariantExtra::class.java)
        else
            intent?.getParcelableExtra(VARIANT_EXTRA)
}