package gomoku.game

import android.content.ContentValues.TAG
import android.util.Log
import gomoku.Lobby.Lobby
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
import gomoku.login.UserInfo
import gomoku.variant.domain.VariantConfig
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

    suspend fun fetchGameById(id: String): Game?

    suspend fun createGame(variant: VariantConfig, lobby: Lobby, userInfo: UserInfo): Game
    suspend fun findGame(variant: VariantConfig, userInfo: UserInfo): Game?

    suspend fun makeMove(gameId: Int, moVe: Move): Game


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
    var moves = mapOf(
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

    private var games = mutableListOf<Game>(
        Game(
            "Test Game",
            "https://www.example.com",
            "FREESTYLE",
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Test Player", R.drawable.man5),
            PlayerInfo("Test Fetching", R.drawable.woman2),
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

    private var lobby = mutableListOf<Lobby>(
        Lobby(1, 1, 4, "dfadsfsdf")
    )

    override suspend fun fetchGame(): Game {
        Log.v(TAG, "fetching game...")
        delay(8000)
        val index = Random.nextInt(from = 0, until = games.size)
        Log.v(TAG, "game fetched")
        return games[0]
    }

    override suspend fun createGame(
        variant: VariantConfig,
        lobby: Lobby,
        userInfo: UserInfo
    ): Game {
        Log.v(TAG, "creating game...")
        delay(5000)
        val g = Game(
            "I got nothing gaje ",
            "https://www.example.com",
            variant.name.name,
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Player W", R.drawable.man5),
            PlayerInfo("Player B", R.drawable.woman2)
        )
        games.add(g)
        return g
    }

    override suspend fun fetchGameById(id: String): Game? {
        Log.v(TAG, "fetching game by id...")
        delay(5000)
        return games.firstOrNull { it.id == id }
    }

    override suspend fun findGame(variant: VariantConfig, userInfo: UserInfo): Game? {
        Log.v(TAG, "finding game...")
        delay(5000)
        val foundedGame = lobby.firstOrNull { it.variantId == variant.id }
        if (foundedGame != null) {
            return createGame(variant, foundedGame, userInfo)
        }
        Log.v(TAG, "game found")
        return Game(
            "EIOAOIEO",
            "https://www.example.com",
            "FREESTYLE",
            board,
            "DSSDSDDS",
            "WEQEWQWEWQE",
            PlayerInfo("Player W", R.drawable.man5),
            PlayerInfo("Player B", R.drawable.woman2),
        )
    }

    //TODO(Implementar o make move)
    override suspend fun makeMove(gameId: Int, moVe: Move): Game {
        Log.v(TAG, "making move...")
        delay(5000)
        games[0].board.moves = games[0].board.moves.plus(moVe)

        return games[0]
    }

}


