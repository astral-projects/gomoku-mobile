package gomoku.ui

import gomoku.domain.Fail
import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.Loading
import gomoku.domain.getOrThrow
import gomoku.domain.service.user.UserService
import gomoku.domain.service.user.errors.RegisterUserException
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.register.RegisterViewModel
import gomoku.utils.MockMainDispatcherRule
import gomoku.utils.TestDataGenerator.newTestNumber
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

private data class RegisterCredentials(
    val username: String,
    val password: String,
    val email: String,
)

class RegisterViewModelTests {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    companion object {
        private val validRegistrationCredentials = RegisterCredentials(
            username = "admin",
            password = "password",
            email = "email",
        )

        private val invalidRegistrationCredentials = RegisterCredentials(
            username = "not-admin",
            password = "not-password",
            email = "not-email",
        )

        private val userId = newTestNumber()

    }

    private val mockUserService = mockk<UserService> {
        coEvery {
            register(
                validRegistrationCredentials.username,
                validRegistrationCredentials.email,
                validRegistrationCredentials.password,

                )
        } coAnswers { userId }
        coEvery {
            register(
                invalidRegistrationCredentials.username,
                invalidRegistrationCredentials.email,
                invalidRegistrationCredentials.password,
            )
        } throws RegisterUserException("User already exists")
    }

    private val mockPreferencesRepository = mockk<PreferencesRepository>()

    @Test
    fun `initial state is idle`() = runTest {
        // given: a register viewmodel
        val viewModel = RegisterViewModel(mockUserService, mockPreferencesRepository)

        // when: the user info is collected without calling any method
        val collectedState = viewModel.userId.collectWithTimeout()

        // then: the state is idle
        assertTrue(collectedState is Idle)
    }

    @Test
    fun `registration success sets loaded state`() = runTest {
        // given: a register viewmodel
        val viewModel = RegisterViewModel(mockUserService, mockPreferencesRepository)

        // and: valid registration credentials
        val credentials = validRegistrationCredentials

        // when: a subscription is made before calling the register method
        viewModel.userId.subscribeBeforeCallingOperation {
            // and: register method is called
            viewModel.register(credentials.username, credentials.email, credentials.password)
        }.also { collectedStates ->
            // then: the default IO state sequence is emitted
            val expectedState = listOf(
                Idle,
                Loading(),
                Loaded(Result.success(userId))
            )
            assertTrue(collectedStates == expectedState)

            // and: the last state is loaded with the user ID
            assertTrue(collectedStates.last().getOrThrow() == userId)
        }

        // and: the service is only called once
        coVerify(exactly = 1) {
            mockUserService.register(
                credentials.username,
                credentials.email,
                credentials.password,
            )
        }
    }

    @Test
    fun `registration failure sets fail state`() = runTest {
        // given: a register viewmodel
        val viewModel = RegisterViewModel(mockUserService, mockPreferencesRepository)

        // and: invalid registration credentials
        val credentials = invalidRegistrationCredentials

        // when: the register method is called
        viewModel.register(credentials.username, credentials.email, credentials.password)

        // and: the user id is collected without calling the register method again
        val collectedState = viewModel.userId.collectWithTimeout()

        // then: the state is fail
        assertTrue(collectedState is Fail)
    }

    @Test
    fun `in fail state registration can be called again`() = runTest {
        // given: a register viewmodel
        val viewModel = RegisterViewModel(mockUserService, mockPreferencesRepository)

        // and: invalid registration credentials
        val credentials = invalidRegistrationCredentials

        // when: register method is called
        viewModel.register(credentials.username, credentials.email, credentials.password)

        // and: the user ID is collected without calling the method again
        val collectedState = viewModel.userId.collectWithTimeout()

        // then: the state is fail
        assertTrue(collectedState is Fail)

        // when: register is called again while in the fail state
        // then: no exception is thrown
        viewModel.register(credentials.username, credentials.email, credentials.password)
    }

    @Test
    fun `resetToIdle sets view model to idle state or throws if not in Loaded state`() = runTest {
        // given: a register viewmodel
        val viewModel = RegisterViewModel(mockUserService, mockPreferencesRepository)

        // when: the resetToIdle method is called while in the idle state
        // then: an exception is thrown
        assertThrows(IllegalStateException::class.java) {
            viewModel.resetToIdle()
        }

        // when: the resetToIdle method is called while in the fail state
        val credentials = invalidRegistrationCredentials
        viewModel.register(credentials.username, credentials.email, credentials.password)
        val collectedState = viewModel.userId.collectWithTimeout()
        assertTrue(collectedState is Fail)

        // then: no exception is thrown
        viewModel.resetToIdle()

        // and: the state is idle
        val collectedStateAfterReset = viewModel.userId.collectWithTimeout()
        assertTrue(collectedStateAfterReset is Idle)
    }

    @Test(expected = IllegalStateException::class)
    fun `register throws exception when not in idle or fail state`() = runTest {
        // given: a register viewmodel
        val viewModel = RegisterViewModel(mockUserService, mockPreferencesRepository)

        // and: valid registration credentials
        val credentials = validRegistrationCredentials

        // when: register is called
        viewModel.register(credentials.username, credentials.email, credentials.password)

        // when: register is called again while in the loaded state
        // then: an exception is thrown
        viewModel.register(credentials.username, credentials.email, credentials.password)
    }

}