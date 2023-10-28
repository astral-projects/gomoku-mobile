package gomoku.game

import GameScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import gomoku.domain.Timer
import gomoku.domain.Turn
import gomoku.domain.board.Board
import gomoku.domain.moves.move.Piece
import gomoku.domain.moves.move.Player
import gomoku.domain.moves.move.Square
import gomoku.ui.background.BackgroundConfig
import gomoku.ui.containers.PlayerInfo


class GameActivity: ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, GameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    //Todo(The following code is not working, it is just for the navigation propose)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen(
                backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                onBurgerMenuClick = { /*TODO*/ },
                onLeaveGameRequest = { /*TODO*/ },
                whitePlayer = PlayerInfo("Test", 1),
                blackPlayer = PlayerInfo("Test",2),
                board = Board(
                    moves = mapOf<Square,Piece>(),
                    turn = Turn(
                        player = Player.w,
                        timer = Timer(0, 0,)
                    )
                )
            )
        }
    }
}