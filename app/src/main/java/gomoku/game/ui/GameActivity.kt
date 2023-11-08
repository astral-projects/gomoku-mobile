package gomoku.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalConfiguration
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
    //TODO(THE GameScreenViewModel is not working But we need to pass the game form viewModel to the GameScreen)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                GameScreen(
                    backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                    localPlayer = Player.W,
                    onLeaveGameRequest = { HomeActivity.navigateTo(this) },
                    onCellClick = { /*TODO*/ },
                    viewModel.game !!
                    /*whitePlayer = PlayerInfo("Player W", R.drawable.man5),
                            blackPlayer = PlayerInfo("Player B", R.drawable.woman2),
                            board = Board(
                                moves = mapOf(
                                    Move(Square(1, 'a'), Piece(Player.W)),
                                    Move(Square(4, 'b'), Piece(Player.B)),
                                    Move(Square(3, 'c'), Piece(Player.W)),
                                    Move(Square(7, 'd'), Piece(Player.B)),
                                    Move(Square(5, 'e'), Piece(Player.W)),
                                    Move(Square(3, 'f'), Piece(Player.B)),
                                ),
                                turn = BoardTurn(
                                    player = Player.W,
                                    timer = Timer(0, 0)
                                ),
                                size = BoardSize.FIFTEEN,
                            )*/
                )
        }
    }
}