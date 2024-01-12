package gomoku.ui

import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.UserService
import gomoku.domain.service.user.errors.FetchUserException
import gomoku.domain.storage.PreferencesRepository
import gomoku.http.utils.recipes.Recipe
import gomoku.ui.login.LoginScreenState
import gomoku.ui.login.LoginViewModel
import gomoku.utils.MockMainDispatcherRule
import gomoku.utils.flows.collectWithTimeout
import gomoku.utils.flows.subscribeBeforeCallingOperation
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

private data class LoginCredentials(
    val username: String,
    val password: String,
)

class LoginViewModelTests {

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

        private val recipes = listOf(
            Recipe(
                rel = "self",
                href = "http://localhost:8080/api/recipes",
            ),
            Recipe(
                rel = "login",
                href = "http://localhost:8080/api/login",
            ),
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
        coEvery { getUserInfo() } coAnswers { null }
        coEvery { getUriTemplates() } coAnswers { recipes }
    }

    @Test
    fun `initial state is idle`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // when: the user info is collected without calling any method
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is idle
        assertTrue(collectedState is LoginScreenState.Idle)
    }

    @Test
    fun `login success sets loaded state after fetching Uri templates`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // when: subscriber is collecting the state for a timeout period
        viewModel.stateFlow.subscribeBeforeCallingOperation {
            // and: fetchUriTemplates method is called
            viewModel.fetchUriTemplates()
        }.also { collectedStates ->
            // then: the expected state sequence is collected
            val expectedState = listOf(
                LoginScreenState.Idle,
                LoginScreenState.FetchRecipes(),
            )
            assertTrue(collectedStates == expectedState)
        }

        // and: valid login credentials are used
        val username = validLoginCredentials.username
        val password = validLoginCredentials.password

        // and: userInfo is present in the repository
        coEvery { mockPreferencesRepository.getUserInfo() } coAnswers { userInfo }

        // when: subscriber is collecting the state for a timeout period
        viewModel.stateFlow.subscribeBeforeCallingOperation {
            // and: login method is called
            viewModel.login(username, password)
        }.also { collectedStates ->
            // then: the expected state sequence is collected
            val expectedStates = listOf(
                LoginScreenState.FetchRecipes(),
                LoginScreenState.Login(),
                LoginScreenState.Login(userInfo, true),
            )
            assertTrue(collectedStates == expectedStates)
        }

        // and: the user info is stored and called only once
        coVerify(exactly = 1) {
            mockPreferencesRepository.setUserInfo(userInfo)
        }
    }

    @Test
    fun `fetching uri templates success in userinfo presence sets loaded state`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // and: userInfo is present in the repository
        coEvery { mockPreferencesRepository.getUserInfo() } coAnswers { userInfo }

        // when: subscriber is collecting the state for a timeout period
        viewModel.stateFlow.subscribeBeforeCallingOperation {
            // and: fetchUriTemplates method is called
            viewModel.fetchUriTemplates()
        }.also { collectedStates ->
            // then: the expected state sequence is collected
            val expectedState = listOf(
                LoginScreenState.Idle,
                LoginScreenState.FetchRecipes(),
                LoginScreenState.Login(userInfo, true),
            )
            assertTrue(collectedStates == expectedState)
        }
    }

    @Test
    fun `resetting to idle state success`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // when: fetchUriTemplates method is called
        viewModel.fetchUriTemplates()

        // and: the login method is called
        viewModel.login(validLoginCredentials.username, validLoginCredentials.password)

        // and: the state is collected for a timeout period
        val lastState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is loaded
        assertTrue(lastState is LoginScreenState.Login)

        // when: reset method is called
        viewModel.resetToIdle()

        // and: the state is collected for a timeout period
        val collectedState = viewModel.stateFlow.collectWithTimeout()

        // then: the state is idle
        assertTrue(collectedState is LoginScreenState.Idle)
    }

    @Test(expected = IllegalStateException::class)
    fun `resetting to idle state failure`() = runTest {
        // given: a login viewmodel
        val viewModel = LoginViewModel(mockUserService, mockPreferencesRepository)

        // when: reset method is called
        viewModel.resetToIdle()

        // then: an exception is thrown
    }
}
