package gomoku.ui

import gomoku.domain.Fail
import gomoku.domain.Idle
import gomoku.domain.getOrThrow
import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.UserService
import gomoku.domain.service.user.errors.FetchUserException
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.login.LoginViewModel
import gomoku.utils.MockMainDispatcherRule
import gomoku.utils.flows.collectWithTimeout
import gomoku.utils.flows.subscribeBeforeCallingOperation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

private data class LoginCredentials(
    val username: String,
    val password: String,
)

class LoginViewModelTests : AbstractViewModelTests() {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    companion object {
        private val validLoginCredentials = LoginCredentials(
            username = "admin",
            password = "password",
        )

        private val invalidLoginCredentials = LoginCredentials(
            username = "not-admin",
            password = "not-password",
        )

        private val userInfo = UserInfo(
            id = 1,
            username = validLoginCredentials.username,
            email = "email",
            token = "token",
            iconId = 1,
        )
    }

    private val mockUserService = mockk<UserService> {
        coEvery {
            login(validLoginCredentials.username, validLoginCredentials.password)
        } coAnswers { userInfo }
        coEvery {
            login(invalidLoginCredentials.username, invalidLoginCredentials.password)
        } throws FetchUserException()
    }

    private val mockPreferencesRepository = mockk<PreferencesRepository> {
        coEvery { setUserInfo(userInfo) } coAnswers { }
    }

    @Test
    fun `initial state is idle`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // when: the user info is collected without calling any method
        val collectedState = viewModel.userInfo.collectWithTimeout()

        // then: the state is idle
        assertTrue(collectedState is Idle)
    }

    @Test
    fun `login success sets loaded state`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // and: valid login credentials are used
        val username = validLoginCredentials.username
        val password = validLoginCredentials.password

        // when: subscriber is collecting the user info for a timeout period
        viewModel.userInfo.subscribeBeforeCallingOperation {
            // and: login method is called
            viewModel.login(username, password)
        }.also { collectedStates ->
            // then: the states are the default IO states
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the user info
            assertTrue(collectedStates.last().getOrThrow() == userInfo)
        }

        // and: the user info is stored and called only once
        coVerify(exactly = 1) {
            mockPreferencesRepository.setUserInfo(userInfo)
        }

    }

    @Test
    fun `login failure sets fail state`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // and: invalid login credentials
        val username = invalidLoginCredentials.username
        val password = invalidLoginCredentials.password

        // when: the login method is called
        viewModel.login(username, password)

        // and: the user info is collected without calling the login method
        val collectedState = viewModel.userInfo.collectWithTimeout()

        // then: the state is fail
        assertTrue(collectedState is Fail)

        // and: the user info is changed
        coVerify(exactly = 0) {
            mockPreferencesRepository.setUserInfo(userInfo)
        }

        // and: the service is only called once
        coVerify(exactly = 1) {
            mockUserService.login(username, password)
        }
    }

    @Test
    fun `in fail state login can be called again`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // and: invalid login credentials
        val username = invalidLoginCredentials.username
        val password = invalidLoginCredentials.password

        // when: login method is called
        viewModel.login(username, password)

        // and: the user info is collected without calling the method again
        val collectedState = viewModel.userInfo.collectWithTimeout()

        // then: the state is fail
        assertTrue(collectedState is Fail)

        // when: login is called again while in the fail state
        // then: no exception is thrown
        viewModel.login(username, password)
    }

    @Test
    fun `resetToIdle sets view model to idle state or throws if not in Loaded state`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // when: the resetToIdle method is called while in the idle state
        // then: an exception is thrown
        assertThrows(IllegalStateException::class.java) {
            viewModel.resetToIdle()
        }

        // when: the resetToIdle method is called while in the fail state
        val username = invalidLoginCredentials.username
        val password = invalidLoginCredentials.password
        viewModel.login(username, password)
        val collectedState = viewModel.userInfo.collectWithTimeout()
        assertTrue(collectedState is Fail)

        // then: no exception is thrown
        viewModel.resetToIdle()

        // and: the state is idle
        val collectedStateAfterReset = viewModel.userInfo.collectWithTimeout()
        assertTrue(collectedStateAfterReset is Idle)
    }

    @Test(expected = IllegalStateException::class)
    fun `login throws exception when not in idle or fail state`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // and: valid login credentials
        val username = validLoginCredentials.username
        val password = validLoginCredentials.password

        // when: login is called
        viewModel.login(username, password)

        // when: login is called again while in the loaded state
        // then: an exception is thrown
        viewModel.login(username, password)
    }

}
