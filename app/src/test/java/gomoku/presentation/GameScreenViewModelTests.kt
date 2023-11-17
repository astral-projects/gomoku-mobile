package gomoku.presentation


import com.google.gson.Gson
import gomoku.game.domain.Game
import gomoku.game.domain.Timer
import gomoku.game.domain.board.Board
import gomoku.game.domain.board.BoardSize
import gomoku.game.domain.board.BoardTurn
import gomoku.game.domain.moves.Move
import gomoku.game.domain.moves.move.Piece
import gomoku.game.domain.moves.move.Player
import gomoku.game.domain.moves.move.Square
import gomoku.game.ui.GameScreenViewModel
import gomoku.http.GomokuGame
import gomoku.http.GomokuServiceImpl
import gomoku.leaderboard.domain.PlayerInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import okhttp3.OkHttpClient
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test
import pdm.gomoku.R

@OptIn(ExperimentalCoroutinesApi::class)
class GameScreenViewModelTests {

    private val sut = GameScreenViewModel(
        GomokuServiceImpl(
            listOf(
                GomokuGame(
                    OkHttpClient.Builder()
                        .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                        .build(), Gson()
                )
            )
        )
    )

    @get:Rule
    val rule = MockMainDispatcherRule(UnconfinedTestDispatcher())

    //TODO(TO Run this tests you need to have the JAVA_VERSION_11 ON THE build gradle file because of the dependency mock)
    @Test
    fun `initially the joke is null`() {
        // Arrange
        // Act
        // Assert
        assertNull(sut.game)
    }

    @Test
    fun `fetch joke calls service`() {
//        // Arrange
//        var called = false
//        // Act
//        sut.fetchJoke(object : JokesService {
//            override suspend fun fetchJoke(): Joke {
//                called = true
//                return Joke("Test", URL("https://www.test.pt"))
//            }
//        })
//        // Assert
//        assertTrue(called)
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
        // Arrange
        val service = mockk<GomokuGame> {
            coEvery { fetchGame() } coAnswers {
                Game(
                    "I got nothing",
                    "https://www.example.com",
                    "FREESTYLE",
                    board,
                    "DSSDSDDS",
                    "WEQEWQWEWQE",
                    PlayerInfo("Player W", R.drawable.man5),
                    PlayerInfo("Player B", R.drawable.woman2),
                )
            }
        }
        // Act
        //sut.fetchGame(service)
        // Assert
        coVerify(exactly = 1) { service.fetchGame() }
    }
}