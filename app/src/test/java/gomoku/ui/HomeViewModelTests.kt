package gomoku.ui

import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.UserService
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.home.HomeScreenState
import gomoku.ui.home.HomeViewModel
import gomoku.utils.MockMainDispatcherRule
import gomoku.utils.TestDataGenerator.newTestNumber
import gomoku.utils.TestDataGenerator.newTestString
import gomoku.utils.flows.collectWithTimeout
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class HomeViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    companion object {
        private val token = newTestString()
        private val userInfo = UserInfo(
            id = newTestNumber(),
            username = newTestString(),
            token = token,
            email = newTestString(),
            iconId = newTestNumber(),
        )
    }

    private val mockUserService = mockk<UserService> {
        coEvery { logout(token) } coAnswers { }
    }

    private val mockPreferencesRepository = mockk<PreferencesRepository>() {
        coEvery { getUserInfo() } coAnswers { userInfo }
        coEvery { clearUserInfo(userInfo) } coAnswers { }
    }

    @Test
    fun `logout success sets logout state success`() = runTest {
        // given: a home view model in idle state
        val viewModel = HomeViewModel(
            mockUserService,
            mockPreferencesRepository
        )

        // when: logout method is called
        viewModel.logout()

        // then: the state is Logout(true)
        val currentState = viewModel.stateFlow.collectWithTimeout()
        assertTrue(currentState is HomeScreenState.Logout)

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockUserService.logout(token) }
    }

    @Test
    fun `logout when user not logged in sets error state and resets to idle after reset is called`() =
        runTest {
            // given: a home view model in idle state
            val viewModel = HomeViewModel(
                mockUserService,
                mockPreferencesRepository
            )

            // and: simulate user not logged in scenario
            coEvery { mockPreferencesRepository.getUserInfo() } coAnswers { null }

            // when: logout method is called
            viewModel.logout()

            // then: the state is Error
            val currentState = viewModel.stateFlow.collectWithTimeout()
            assertTrue(currentState is HomeScreenState.Error)

            // and: service function is not called
            coVerify(exactly = 0) { mockUserService.logout(token) }

            // when: resetToIdle method is called
            viewModel.resetToIdle()

            // then: the state is Idle
            val currentStateAfterLogout = viewModel.stateFlow.collectWithTimeout()
            assertTrue(currentStateAfterLogout is HomeScreenState.Idle)
        }
}