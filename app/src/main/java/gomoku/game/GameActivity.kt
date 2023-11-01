package gomoku.game

import GameScreen
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import gomoku.domain.Timer
import gomoku.domain.board.Board
import gomoku.domain.board.BoardSize
import gomoku.domain.board.BoardTurn
import gomoku.domain.moves.Move
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
        val moves = mapOf(
            Move(Square(1, 'a'), Piece(Player.W)),
            Move(Square(4, 'b'), Piece(Player.B)),
            Move(Square(3, 'c'), Piece(Player.W)),
            Move(Square(7, 'd'), Piece(Player.B)),
            Move(Square(5, 'e'), Piece(Player.W)),
            Move(Square(3, 'f'), Piece(Player.B)),
        )
        setContent {
            GameScreen(
                backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                localPlayer = Player.W,
                onBurgerMenuClick = { /*TODO*/ },
                onLeaveGameRequest = { /*TODO*/ },
                onCellClick = { /*TODO*/ },
                whitePlayer = PlayerInfo("Test", 1),
                blackPlayer = PlayerInfo("Test",2),
                board = Board(
                    moves = moves,
                    turn = BoardTurn(
                        player = Player.W,
                        timer = Timer(0, 0,)
                    ),
                    size = BoardSize.FIFTEEN,
                )
            )
        }
    }
}