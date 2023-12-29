package gomoku.ui

import gomoku.domain.Loaded
import gomoku.domain.game.Timer
import gomoku.domain.game.board.Board
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.board.BoardTurn
import gomoku.domain.game.match.Game
import gomoku.domain.game.moves.Move
import gomoku.domain.game.moves.move.Piece
import gomoku.domain.game.moves.move.Player
import gomoku.domain.game.moves.move.Square
import gomoku.domain.getOrThrow
import gomoku.domain.leaderboard.PlayerInfo
import gomoku.domain.service.game.GameService
import gomoku.domain.service.game.errors.FetchGameException
import gomoku.domain.storage.PreferencesRepository
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
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

class GameViewModelTests : AbstractViewModelTests() {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    companion object {
        private val gameId = newTestNumber()
        private val invalidGameId = newTestNumber()
        private val validMove: Move = Move(Square(1, 'a'), Piece(Player.W))
        private val invalidMove: Move = Move(Square(1, 'a'), Piece(Player.B))
        private val board = Board(emptyMap(), BoardTurn(Player.W, Timer(0, 0)), BoardSize.NINETEEN)
        private val token = newTestString()
        private val game = Game(
            id = gameId,
            variant = VariantConfig(
                id = newTestNumber(),
                name = VariantName.CARO,
                openingRule = OpeningRule.LONG_PRO,
                boardSize = BoardSize.NINETEEN
            ),
            board = board,
            host = PlayerInfo("Player W", R.drawable.man),
            guest = PlayerInfo("Player B", R.drawable.man2)
        )
    }

    private val mockGameService = mockk<GameService> {
        coEvery { fetchGameById(gameId) } coAnswers { game }
        coEvery { fetchGameById(invalidGameId) } throws FetchGameException("Invalid game id")
        coEvery { makeMove(gameId, validMove, token) } coAnswers { game }
        coEvery { makeMove(gameId, invalidMove, token) } throws FetchGameException("Invalid move")
    }

    private val mockPreferencesRepository = mockk<PreferencesRepository>()

    @Test
    fun `fetchGameById success sets loaded state with the game`() = runTest {
        // given: a game view model
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository,
        )

        // when: subscriber is collecting the game for a timeout period
        viewModel.game.subscribeBeforeCallingOperation {
            // and: fetchGameById method is called
            viewModel.fetchGameById(gameId)
        }.also { collectedStates ->
            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the game
            assertTrue(collectedStates.last() is Loaded)

            // and: the value is the expected game
            val gameResult = collectedStates.last().getOrThrow()
            assertEquals(gameResult, game)
        }

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.fetchGameById(gameId) }
    }

    @Test(expected = FetchGameException::class)
    fun `fetchGameById failure sets loaded state with the error`() = runTest {
        // given: a game view model
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: subscriber is collecting the game for a timeout period
        viewModel.game.subscribeBeforeCallingOperation {
            // and: fetchGameById method is called
            viewModel.fetchGameById(invalidGameId)
        }.also { collectedStates ->

            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the error
            assertTrue(collectedStates.last() is Loaded)

            // and: service function is called exactly once
            coVerify(exactly = 1) { mockGameService.fetchGameById(invalidGameId) }

            // and: the value is the expected error
            collectedStates.last().getOrThrow()
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
        val collectedState = viewModel.game.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is Loaded)

        // when: makeMove method is called, after the game is loaded
        viewModel.makeMove(gameId, validMove)

        // and: the game is collected for a timeout period
        val collectedStateAfterInitialLoad = viewModel.game.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedStateAfterInitialLoad is Loaded)

        // and: the value is the expected updated game
        val updatedGame = collectedStateAfterInitialLoad.getOrThrow()
        assertEquals(updatedGame, game)

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.makeMove(gameId, validMove, token) }
    }

    @Test(expected = FetchGameException::class)
    fun `makeMove failure sets loaded state with the error`() = runTest {
        // given: a game view model with a loaded game state
        val viewModel = GameViewModel(
            mockGameService,
            mockPreferencesRepository
        )

        // when: findGame method is called
        viewModel.fetchGameById(gameId)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.game.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is Loaded)

        // when: makeMove method is called, after the game is loaded
        viewModel.makeMove(gameId, invalidMove)

        // and: the game is collected for a timeout period
        val collectedStateAfterInitialLoad = viewModel.game.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedStateAfterInitialLoad is Loaded)

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.makeMove(gameId, invalidMove, token) }

        // and: the value is the expected error
        collectedStateAfterInitialLoad.getOrThrow()
    }
}
