package gomoku.ui

import gomoku.domain.Idle
import gomoku.domain.Loaded
import gomoku.domain.Loading
import gomoku.domain.getOrNull
import gomoku.domain.getOrThrow
import gomoku.domain.leaderboard.Term
import gomoku.domain.leaderboard.UserStats
import gomoku.domain.login.UserInfo
import gomoku.domain.service.user.UserService
import gomoku.domain.service.user.errors.FetchUserException
import gomoku.domain.storage.PreferencesRepository
import gomoku.ui.leaderboard.LeaderboardViewModel
import gomoku.utils.MockMainDispatcherRule
import gomoku.utils.TestDataGenerator.generateUsersStats
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

class LeaderboardViewModelTests : AbstractViewModelTests() {

    @get:Rule
    val rule = MockMainDispatcherRule(testDispatcher = StandardTestDispatcher())

    companion object {
        private const val NON_EXISTING_PAGE = -1
        private const val NON_EXISTING_ID = -1
        private const val FIRST_PAGE = 1
        private const val SECOND_PAGE = 2
        private const val USER_ID = 1
        private val userInfo = UserInfo(
            id = USER_ID,
            username = "username",
            token = "token",
            email = "email",
            iconId = 1
        )
        private val validSearchTerm = Term(userInfo.username)
        private val invalidSearchTerm = Term("term")
        private val userStats = UserStats(userInfo)
        private val otherUsers: List<UserStats> = generateUsersStats(10)
        private val firstPageUsers = listOf(userStats)
        private val secondPageUsers = firstPageUsers + otherUsers.first()
    }

    private val mockUserService = mockk<UserService> {
        coEvery { fetchUserStats(USER_ID) } coAnswers { userStats }
        coEvery { fetchUserStats(NON_EXISTING_ID) } throws FetchUserException()
        coEvery { fetchUsersStats(FIRST_PAGE) } coAnswers { firstPageUsers }
        coEvery { fetchUsersStats(SECOND_PAGE) } coAnswers { secondPageUsers }
        coEvery { fetchUsersStats(NON_EXISTING_PAGE) } coAnswers { emptyList() }
        coEvery { searchUsers(validSearchTerm) } coAnswers { listOf(userStats) }
        coEvery { searchUsers(invalidSearchTerm) } coAnswers { emptyList() }
    }

    private val mockPreferencesRepository = mockk<PreferencesRepository>()

    @Test
    fun `initial state is idle`() = runTest {
        // given: a leaderboard view model
        val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

        // when: the user stats are collected without calling any method
        val collectedState = viewModel.usersStats.collectWithTimeout()

        // then: the state is idle
        assertTrue(collectedState is Idle)
    }

    @Test
    fun `fetchUserStats success sets loaded state with the user stats as a list`() = runTest {
        // given: and a leaderboard view model
        val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

        // and: a user ID
        val userId = USER_ID

        // when: subscriber is collecting the user stats for a timeout period
        viewModel.usersStats.subscribeBeforeCallingOperation {
            // and: fetchUserStats method is called
            viewModel.fetchUserStats(userId)
        }.also { collectedStates ->
            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the user stats
            assertTrue(collectedStates.last() is Loaded)

            // and: the value is the expected user stats
            val userStats = collectedStates.last().getOrThrow()
            assertEquals(userStats, userStats)
        }

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockUserService.fetchUserStats(userId) }
    }

    @Test
    fun `fetchUserStats failure sets state as loaded with an empty list`() = runTest {
        // given: a leaderboard view model
        val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

        // when: the method is called
        viewModel.fetchUserStats(NON_EXISTING_ID)

        // and: the user stats are collected without calling the method again
        val collectedState = viewModel.usersStats.collectWithTimeout()

        // then: the state is loaded with an empty list
        assertTrue(collectedState is Loaded)

        // and: the value is an empty list
        val userStats = collectedState.getOrThrow()
        assertEquals(userStats, emptyList<UserStats>())
    }

    @Test
    fun `fetchUsersStats success sets loaded state with the users stats as a list`() = runTest {
        // given: a leaderboard view model
        val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

        // when: subscriber is collecting the user stats for a timeout period
        viewModel.usersStats.subscribeBeforeCallingOperation {
            // and: fetchUsersStats method is called
            viewModel.fetchUsersStats(FIRST_PAGE)
        }.also { collectedStates ->
            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the user stats
            assertTrue(collectedStates.last() is Loaded)

            // and: the value is the expected user stats
            val userStats = collectedStates.last().getOrThrow()
            assertEquals(userStats, firstPageUsers)
        }

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockUserService.fetchUsersStats(FIRST_PAGE) }
    }

    @Test
    fun `fetchUserStats retains the previous loaded values and merges them with the new ones`() =
        runTest {
            // given: a leaderboard view model
            val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

            // when: subscriber is collecting the user stats for a timeout period, for the first page
            viewModel.usersStats.subscribeBeforeCallingOperation {
                // and: fetchUsersStats method is called
                viewModel.fetchUsersStats(FIRST_PAGE)
            }.also { collectedStates ->
                // then: the state sequence is correct
                verifyDefaultIOStateSequence(collectedStates)

                // and: the last state is loaded with the user stats
                assertTrue(collectedStates.last() is Loaded)

                // and: the value is the expected user stats
                val userStats = collectedStates.last().getOrThrow()
                assertEquals(userStats, firstPageUsers)
            }

            // when: subscriber is collecting the user stats for a timeout period, for the second page
            viewModel.usersStats.subscribeBeforeCallingOperation {
                // and: fetchUsersStats method is called
                viewModel.fetchUsersStats(SECOND_PAGE)
            }.also { collectedStates ->
                // then: the first state is Loaded, instead of Idle
                assertTrue(collectedStates.first() is Loaded)

                // and: the middle state is Loading with the previous list
                assertTrue(collectedStates[1] is Loading)
                assertEquals(collectedStates[1].getOrNull(), firstPageUsers)

                // and: the last state is loaded with the user stats
                assertTrue(collectedStates.last() is Loaded)

                // and: the value is the expected user stats from both pages sorted by rank
                val userStats = collectedStates.last().getOrThrow()
                assertEquals(userStats, (firstPageUsers + secondPageUsers).sortedBy { it.rank })
            }
        }

    @Test
    fun `fetchUsersStats failure sets state as loaded with an empty list`() = runTest {
        // given: a leaderboard view model
        val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

        // when: the method is called
        viewModel.fetchUsersStats(NON_EXISTING_PAGE)

        // and: the user stats are collected without calling the method again
        val collectedState = viewModel.usersStats.collectWithTimeout()

        // then: the state is loaded with an empty list
        assertTrue(collectedState is Loaded)

        // and: the value is an empty list
        val userStats = collectedState.getOrThrow()
        assertEquals(userStats, emptyList<UserStats>())
    }

    @Test
    fun `searchUsers success sets loaded state with the users stats as a list`() = runTest {
        // given: a leaderboard view model
        val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

        // when: subscriber is collecting the user stats for a timeout period
        viewModel.usersStats.subscribeBeforeCallingOperation {
            // and: searchUsers method is called
            viewModel.searchUsers(validSearchTerm)
        }.also { collectedStates ->
            // then: the state sequence is correct
            verifyDefaultIOStateSequence(collectedStates)

            // and: the last state is loaded with the user stats
            assertTrue(collectedStates.last() is Loaded)

            // and: the value is the expected user stats
            val userStatsResult = collectedStates.last().getOrThrow()
            assertEquals(userStatsResult, listOf(userStats))
        }

        // and: service function is called exactly once
        coVerify(exactly = 1) { mockUserService.searchUsers(validSearchTerm) }
    }

    @Test
    fun `searchUsers failure sets state as loaded with an empty list`() = runTest {
        // given: a leaderboard view model
        val viewModel = LeaderboardViewModel(mockUserService, mockPreferencesRepository)

        // when: the method is called
        viewModel.searchUsers(invalidSearchTerm)

        // and: the user stats are collected without calling the method again
        val collectedState = viewModel.usersStats.collectWithTimeout()

        // then: the state is loaded with an empty list
        assertTrue(collectedState is Loaded)

        // and: the value is an empty list
        val userStats = collectedState.getOrThrow()
        assertEquals(userStats, emptyList<UserStats>())
    }

}