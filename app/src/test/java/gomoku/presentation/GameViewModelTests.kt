package gomoku.presentation

import gomoku.domain.game.Game
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.http.GomokuGame
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.Test
import pdm.gomoku.R

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTests {

    /*private val sut = GameViewModel(
        GameServiceImplementation(
            listOf(
                GomokuGame(
                    OkHttpClient.Builder()
                        .callTimeout(10, java.util.concurrent.TimeUnit.SECONDS)
                        .build(), Gson()
                )
            )
        )
    )*/

    @get:Rule
    val rule = MockMainDispatcherRule(UnconfinedTestDispatcher())

    //TODO(TO Run this tests you need to have the JAVA_VERSION_11 ON THE build gradle file because of the dependency mock)
    @Test
    fun `initially the joke is null`() {
        // Arrange
        // Act
        // Assert
        //assertNull(sut._gameFlow)
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