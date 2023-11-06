package gomoku.game.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalConfiguration
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square
import gomoku.home.ui.HomeActivity
import gomoku.leaderboard.domain.PlayerInfo
import gomoku.ui.background.BackgroundConfig
import pdm.gomoku.R

class GameActivity : ComponentActivity() {
    companion object {
        fun navigateTo(origin: ComponentActivity) {
            val intent = Intent(origin, GameActivity::class.java)
            origin.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameScreen(
                backgroundConfig = BackgroundConfig(LocalConfiguration.current),
                localPlayer = Player.W,
                onBurgerMenuClick = { /*TODO*/ },
                onLeaveGameRequest = { HomeActivity.navigateTo(this) },
                onCellClick = { /*TODO*/ },
                whitePlayer = PlayerInfo("Player W", R.drawable.man5),
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
                )
            )
        }
    }
}