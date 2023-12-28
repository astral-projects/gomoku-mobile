package gomoku.ui

import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.game.board.BoardSize
import gomoku.domain.game.match.Lobby
import gomoku.domain.getOrThrow
import gomoku.domain.login.UserInfo
import gomoku.domain.service.game.GameService
import gomoku.domain.service.variant.VariantService
import gomoku.domain.storage.PreferencesRepository
import gomoku.domain.variant.OpeningRule
import gomoku.domain.variant.VariantConfig
import gomoku.domain.variant.VariantName
import gomoku.ui.variant.VariantScreenViewModel
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

class VariantViewModelTests : AbstractViewModelTests() {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    companion object {
        private val variantConfig = VariantConfig(
            id = newTestNumber(),
            name = VariantName.TEST,
            openingRule = OpeningRule.PRO,
            boardSize = BoardSize.FIFTEEN
        )
        private val variants = listOf(variantConfig)
        private val userInfo = UserInfo(
            id = newTestNumber(),
            username = newTestString(),
            token = newTestString(),
            email = newTestString(),
            iconId = newTestNumber()
        )
        private val lobbyId = newTestString()
        private val match = Lobby(
            id = lobbyId,
            host = userInfo,
            variantId = variantConfig.id,
        )
    }

    private val mockVariantService = mockk<VariantService> {
        coEvery { fetchVariants() } coAnswers { variants }
    }

    private val mockGameService = mockk<GameService> {
        coEvery { findGame(variantConfig, userInfo) } coAnswers { match }
        coEvery { exitLobby(lobbyId, userInfo) } coAnswers { /* Do nothing */ }
    }

    private val mockPreferencesRepository = mockk<PreferencesRepository> {
        coEvery { setVariants(variants) } coAnswers { /* Do nothing */ }
        coEvery { getUserInfo() } coAnswers { userInfo }
    }

    @Test
    fun `fetchVariants success sets loaded state with the variants as a list`() = runTest {
        // given: a variant screen view model
        val viewModel = VariantScreenViewModel(
            mockVariantService,
            mockGameService,
            mockPreferencesRepository
        )

        // and: empty variants in the preferences repository
        coEvery { mockPreferencesRepository.getVariants() } coAnswers { null }

        // when: subscriber is collecting the variants for a timeout period
        viewModel.variants.subscribeBeforeCallingOperation {
            // and: fetchVariants method is called
            viewModel.fetchVariants()
        }.also { collectedStates ->
            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the variants
            assertTrue(collectedStates.last() is Loaded)

            // and: the value is the expected variants
            val variantsResult = collectedStates.last().getOrThrow()
            assertEquals(variantsResult, variants)
        }

        // and: the variants were tried to be fetched from the preferences repository
        coVerify(exactly = 1) { mockPreferencesRepository.getVariants() }

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockVariantService.fetchVariants() }

        // and: the variants were set in the preferences repository
        coVerify(exactly = 1) { mockPreferencesRepository.setVariants(variants) }
    }

    @Test
    fun `fetchVariants success sets loaded state with stored variants if available`() = runTest {
        // given: a variant screen view model
        val viewModel = VariantScreenViewModel(
            mockVariantService,
            mockGameService,
            mockPreferencesRepository
        )

        // and: stored variants
        coEvery { mockPreferencesRepository.getVariants() } coAnswers { variants }

        // when: subscriber is collecting the variants for a timeout period
        viewModel.variants.subscribeBeforeCallingOperation {
            // and: fetchVariants method is called
            viewModel.fetchVariants()
        }.also { collectedStates ->
            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the stored variants
            assertTrue(collectedStates.last() is Loaded)

            // and: the value is the expected variants
            val variantsResult = collectedStates.last().getOrThrow()
            assertEquals(variantsResult, variants)
        }

        // and: the variants were tried to be fetched from the preferences repository
        coVerify(exactly = 1) { mockPreferencesRepository.getVariants() }

        // and: service function is not called since stored variants are available
        coVerify(exactly = 0) { mockVariantService.fetchVariants() }
    }

    @Test
    fun `findGame success sets loaded state with the lobby`() = runTest {
        // given: a variant screen view model
        val viewModel = VariantScreenViewModel(
            mockVariantService,
            mockGameService,
            mockPreferencesRepository
        )

        // when: subscriber is collecting the match for a timeout period
        viewModel.match.subscribeBeforeCallingOperation {
            // and: findGame method is called
            viewModel.findGame(variantConfig)
        }.also { collectedStates ->
            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the match
            assertTrue(collectedStates.last() is Loaded)

            // and: the value is the expected match
            val matchResult = collectedStates.last().getOrThrow()
            assertEquals(matchResult, match)
        }

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockGameService.findGame(variantConfig, userInfo) }

        // and: the user info was fetched from the preferences repository once
        coVerify(exactly = 1) { mockPreferencesRepository.getUserInfo() }
    }

    @Test
    fun `exitLobby success resets the view model to the idle state`() = runTest {
        // given: a variant screen view model with a loaded match state
        val viewModel = VariantScreenViewModel(
            mockVariantService,
            mockGameService,
            mockPreferencesRepository
        )

        // when: joining a lobby
        viewModel.findGame(variantConfig)

        // and: the match is collected for a timeout period
        val collectedState = viewModel.match.collectWithTimeout()

        // then: the state is loaded
        assertTrue(collectedState is Loaded)

        // when: exiting the lobby
        viewModel.exitLobby()

        // and: the match is collected for a timeout period
        val collectedStateAfterLobbyExit = viewModel.match.collectWithTimeout()

        // then: the state is idle
        assertTrue(collectedStateAfterLobbyExit is Idle)

        // and: the user info was fetched from the preferences repository, once for each operation
        coVerify(exactly = 2) { mockPreferencesRepository.getUserInfo() }
    }
}
