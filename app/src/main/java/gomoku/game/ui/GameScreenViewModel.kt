package gomoku.game.ui

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gomoku.game.FakeGameService
import gomoku.game.domain.Game
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square
import gomoku.leaderboard.domain.PlayerInfo
import kotlinx.coroutines.launch
import pdm.gomoku.R

class GameScreenViewModel : ViewModel() {
    val turn = BoardTurn(
        player = Player.W,
        timer = Timer(0, 55)
    )
    val moves = mapOf(
        Move(Square(1, 'a'), Piece(Player.W)),
        Move(Square(4, 'b'), Piece(Player.B)),
        Move(Square(3, 'c'), Piece(Player.W)),
        Move(Square(7, 'd'), Piece(Player.B)),
        Move(Square(5, 'e'), Piece(Player.W)),
        Move(Square(3, 'f'), Piece(Player.B)),
    )
    val board = Board(
        moves = moves,
        turn = turn,
        size = BoardSize.NINETEEN
    )

    var game: Game by mutableStateOf(
        Game(
            "I got nothing23",
            "https://www.example.com",
            "FREESTYLE",
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Player W", R.drawable.man5),
            PlayerInfo("Player B", R.drawable.woman2),
        )
    )
        private set

    fun fetchGame(service: FakeGameService) {
        viewModelScope.launch {
            game = service.fetchGame()
            Log.v(ContentValues.TAG, "Game fetched: $game")
        }
    }
}