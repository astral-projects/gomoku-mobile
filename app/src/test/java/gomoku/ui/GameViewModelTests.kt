package gomoku.ui

import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.match.GameState
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.domain.service.game.errors.FetchGameException
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.game.GameScreenState
import gomoku.ui.game.GameViewModel
import gomoku.utils.MockMainDispatcherRule
import gomoku.utils.TestDataGenerator.newTestNumber
import gomoku.utils.TestDataGenerator.newTestString
import gomoku.utils.flows.collectWithTimeout
import gomoku.utils.flows.subscribeBeforeCallingOperation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import pdm.gomoku.R

class GameViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    companion object {
        private val gameId = newTestNumber()
        private val invalidGameId = newTestNumber()
        private val validMove: Move = Move(Square(1, 'a'), Piece(Player.W))
        private val invalidMove: Move = Move(Square(1, 'a'), Piece(Player.B))
        private val board =
            Board(emptyMap(), BoardTurn(Player.W, Timer(0, 0)), null, BoardSize.NINETEEN)
        private val token = newTestString()
        private val userInfo = UserInfo(
            id = newTestNumber(),
            username = newTestString(),
            token = token,
            email = newTestString(),
            iconId = newTestNumber(),
        )
        private val game = Game(
            id = gameId,
            variantId = newTestNumber(),
            board = board,
            host = PlayerInfo(userInfo.id, userInfo.username, userInfo.iconId),
            guest = PlayerInfo(2, "Player B", R.drawable.man2),
            state = GameState.IN_PROGRESS,
            localPlayer = Player.W,
        )
    }

    private val mockGameService = mockk<GameService> {
        coEvery { fetchGameById(gameId) } coAnswers { game }
        coEvery { fetchGameById(invalidGameId) } throws FetchGameException("Invalid game id")
        coEvery { makeMove(gameId, validMove, token) } coAnswers { game }
        coEvery { makeMove(gameId, invalidMove, token) } throws FetchGameException("Invalid move")
        coEvery { exitGame(gameId, token) } coAnswers { }
        coEvery { exitGame(invalidGameId, token) } throws FetchGameException("Invalid game id")
    }

    private val mockPreferencesRepository = mockk<PreferencesRepository>() {
        coEvery { getUserInfo() } coAnswers { userInfo }
    }

    @Test
    fun `fetchGameById success sets loaded state with the game`() = runTest {
        // given: a game view model
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository,
        )

        // when: subscriber is collecting the game for a timeout period
        viewModel.stateFlow.subscribeBeforeCallingOperation {
            // and: fetchGameById method is called
            viewModel.fetchGameById(gameId)
        }.also { collectedStates ->
            // then: the state sequence is correct
            val expectedStates = listOf(
                GameScreenState.Idle,
                GameScreenState.Loading,
                GameScreenState.GameLoadedAndYourTurn(game),
            )
            assertEquals(expectedStates, collectedStates)
        }

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.fetchGameById(gameId) }
    }

    @Test
    fun `fetchGameById failure sets loaded state with the error`() = runTest {
        // given: a game view model
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: subscriber is collecting the game for a timeout period
        viewModel.stateFlow.subscribeBeforeCallingOperation {
            // and: fetchGameById method is called
            viewModel.fetchGameById(invalidGameId)
        }.also { collectedStates ->
            // then: the state sequence is correct
            val expectedStates = listOf(
                GameScreenState.Idle,
                GameScreenState.Loading
            )
            val allStatesExceptLast = collectedStates.dropLast(1)
            assertEquals(expectedStates, allStatesExceptLast)

            // and: service function is called exactly once
            coVerify(exactly = 1) { mockGameService.fetchGameById(invalidGameId) }

            // and: the last state is an error
            val lastState = collectedStates.last()
            assertTrue(lastState is GameScreenState.FetchFailed)
        }
    }

    @Test
    fun `makeMove success sets loaded state with the updated game`() = runTest {
        // given: a game view model with a loaded game state
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: findGame method is called
        viewModel.fetchGameById(gameId)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is GameScreenState.GameLoadedAndYourTurn)

        // when: makeMove method is called, after the game is loaded
        viewModel.makeMove(gameId, validMove)

        // and: the game is collected for a timeout period
        val collectedStateAfterInitialLoad = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedStateAfterInitialLoad is GameScreenState.GameLoadedAndNotYourTurn)

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.makeMove(gameId, validMove, token) }
    }

    @Test
    fun `makeMove failure sets loaded state with the error`() = runTest {
        // given: a game view model with a loaded game state
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: findGame method is called
        viewModel.fetchGameById(gameId)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is GameScreenState.GameLoadedAndYourTurn)

        // when: makeMove method is called, after the game is loaded
        viewModel.makeMove(gameId, invalidMove)

        // and: the game is collected for a timeout period
        val collectedStateAfterInitialLoad = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedStateAfterInitialLoad is GameScreenState.GameLoadedAndNotYourTurn)

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.makeMove(gameId, invalidMove, token) }
    }

    @Test
    fun `exitGame success sets idle state`() = runTest {
        // given: a game view model with a loaded game state
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: findGame method is called
        viewModel.fetchGameById(gameId)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is GameScreenState.GameLoadedAndYourTurn)

        // when: exitGame method is called, after the game is loaded
        viewModel.exitGame(gameId)

        // and: the game is collected for a timeout period
        val collectedStateAfterExit = viewModel.stateFlow.collectWithTimeout()

        // then: the state is idle
        assertTrue(collectedStateAfterExit is GameScreenState.Idle)

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.exitGame(gameId, token) }
    }

    @Test
    fun `exitGame failure sets error state`() = runTest {
        // given: a game view model with a loaded game state
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: findGame method is called
        viewModel.fetchGameById(gameId)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is GameScreenState.GameLoadedAndYourTurn)

        // when: exitGame method is called, after the game is loaded
        viewModel.exitGame(invalidGameId)

        // and: the game is collected for a timeout period
        val collectedStateAfterExit = viewModel.stateFlow.collectWithTimeout()

        // then: the state is error
        assertTrue(collectedStateAfterExit is GameScreenState.Error)

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.exitGame(invalidGameId, token) }
    }

    @Test
    fun `startPollingGame success sets updated game state`() = runTest {
        // given: a game view model with a loaded game state
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: findGame method is called
        viewModel.fetchGameById(gameId)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is GameScreenState.GameLoadedAndYourTurn)


        // when: makeMove method is called, after the game is loaded
        viewModel.makeMove(gameId, validMove)

        // and: the game is collected for a timeout period
        val collectedStateAfterInitialLoad = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedStateAfterInitialLoad is GameScreenState.GameLoadedAndNotYourTurn)

        // when: startPollingGame method is called, after the game is loaded
        viewModel.startPollingGame(gameId)

        // and: the game is collected for a timeout period
        val collectedStateAfterPolling = viewModel.stateFlow.collectWithTimeout()

        // then: the state is updated based on the polled game
        assertTrue(collectedStateAfterPolling is GameScreenState.GameLoadedAndYourTurn)

        // and: service function is called exactly twice
        coVerify(exactly = 2) { mockGameService.fetchGameById(gameId) }
    }

    @Test(expected = FetchGameException::class)
    fun `startPollingGame failure sets error state`() = runTest {
        // given: a game view model with a loaded game state
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: findGame method is called
        viewModel.fetchGameById(gameId)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is GameScreenState.GameLoadedAndYourTurn)

        // when: makeMove method is called, after the game is loaded
        viewModel.makeMove(gameId, validMove)

        // and: the game is collected for a timeout period
        val collectedStateAfterInitialLoad = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedStateAfterInitialLoad is GameScreenState.GameLoadedAndNotYourTurn)

        // when: startPollingGame method is called, after the game is loaded
        viewModel.startPollingGame(invalidGameId)
    }
}
