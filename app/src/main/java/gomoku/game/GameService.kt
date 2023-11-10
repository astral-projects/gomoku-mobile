package gomoku.game

import android.content.ContentValues.TAG
import android.util.Log
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
import kotlinx.coroutines.delay
import pdm.gomoku.R
import kotlin.random.Random


/**
 * Contract of the service that provides jokes
 */
interface GameService {
    /**
     * Fetches a game from the service
     */
    suspend fun fetchGame(): Game
}

class FetchGameException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Fake implementation of the GameService. It will replaced by a real implementation
 * in a future lecture.
 */
class FakeGameService : GameService {

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

    private val games = listOf(
        Game(
            "I got nothing",
            "https://www.example.com",
            "FREESTYLE",
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Player W", R.drawable.man5),
            PlayerInfo("Player B", R.drawable.woman2),
    ),
        Game(
            "I got nothing46",
            "https://www.example.com",
            "FREESTYLE",
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Player W", R.drawable.man5),
            PlayerInfo("Player B", R.drawable.woman2),
        ),

        Game(
            "EIOAOIEO",
            "https://www.example.com",
            "FREESTYLE",
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Player W", R.drawable.man5),
            PlayerInfo("Player B", R.drawable.woman2),
        ),
    )

    override suspend fun fetchGame(): Game {
        Log.v(TAG, "fetching game...")
        delay(3000)
        val index = Random.nextInt(from = 0, until = games.size)
        Log.v(TAG, "game fetched")
        return games[index]
    }

}


